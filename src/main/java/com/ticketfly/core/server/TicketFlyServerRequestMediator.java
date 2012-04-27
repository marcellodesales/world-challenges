package com.ticketfly.core.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import com.ticketfly.core.server.request.RequestInfo;
import com.ticketfly.core.server.request.RequestType;
import com.ticketfly.core.server.request.ReverseStringUpdateCounterStrategy;
import com.ticketfly.core.server.request.TicketFlyServerRequestHandler;
import com.ticketfly.core.server.request.interpreter.RequestTypeInterpreter;
import com.ticketfly.core.server.response.QuitDecorator;
import com.ticketfly.core.server.response.ResponseDecorator;
import com.ticketfly.core.server.response.ReverseStringDecorator;
import com.ticketfly.core.server.response.TicketFlyServerResponseHandler;
import com.ticketfly.core.server.response.TicketFlyServerUserErrorHandler;

/**
 * The strategy that reverses strings from the requests.
 * 
 * @author Marcello de Sales (marcello.desales@gmail.com).
 * 
 */
public class TicketFlyServerRequestMediator implements Runnable {

  /**
   * The client socket connection.
   */
  private Socket clientSocket;
  /**
   * The blocking increment request queue in the server.
   */
  private AtomicCounter computationCounter;

  public TicketFlyServerRequestMediator(Socket client, AtomicCounter sharedCounter)
      throws TflyServiceException {

    clientSocket = client;
    computationCounter = sharedCounter;
  }

  public String makeThreadName() {
    String clientIp = clientSocket.getRemoteSocketAddress().toString();
    return "Client [" + clientIp + "]";
  }
  
  /**
   * Hands the client connection, handling the Request and Response.
   * 
   * @param clientSocket the socket client connection.
   * @return The received String from the client connection.
   * @throws TflyServiceException if any problem occurs while reading the input from the client.
   */
  private static String receiveClientInput(Socket clientSocket) throws TflyServiceException {
    try {
      InputStream input = clientSocket.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));
      return reader.readLine();

    } catch (IllegalStateException | IOException errorWithStream) {
      throw new TflyServiceException("Error while receiving message", errorWithStream);
    }
  }

  @Override
  public void run() {
    final String clientName = makeThreadName();
    Thread.currentThread().setName(clientName);
    System.out.println("Handling client request " + clientName);

    TicketFlyServerResponseHandler responseHandler = null;
    TicketFlyServerRequestHandler requestHandler = null;
    while (!clientSocket.isClosed()) {
      try {
        // keep the connection alive
        clientSocket.setKeepAlive(true);

        String clientInput = receiveClientInput(clientSocket);

        // interpret the request with an appropriate handler, updating the counter if needed.
        RequestInfo requestInfo = RequestTypeInterpreter.interpret(clientInput);

        // build the request handler, which will make manipulate the input appropriately.
        requestHandler = new ReverseStringUpdateCounterStrategy(requestInfo, computationCounter);

        // prepare the decorator for the response.
        ResponseDecorator decorator = new ReverseStringDecorator(requestHandler);
        if (requestHandler.getRequestInfo().getRequestType() == RequestType.QUIT) {
          decorator = new QuitDecorator();

        } else {
          decorator = new ReverseStringDecorator(requestHandler);
        }

        // prepare response and send.
        responseHandler = new TicketFlyServerResponseHandler(clientSocket, decorator);
        responseHandler.sendResponse();

      } catch (TflyServiceException wrongRequest) {
        String errorMsg = wrongRequest.getMessage();
        ResponseDecorator decorator = TicketFlyServerUserErrorHandler.makeErrorDecorator(errorMsg);
        responseHandler = new TicketFlyServerUserErrorHandler(clientSocket, decorator);

      } catch (IOException e) {
        System.err.format("%s: Can't send response to client %s - %s\n", "Error", clientName,
            e.getMessage());
      }

      // verify if the user has requested to quit.
      if (requestHandler.getRequestInfo().getRequestType() == RequestType.QUIT) {
        try {
          clientSocket.setKeepAlive(false);
          clientSocket.close();

        } catch (IOException closingConnectionError) {
          closingConnectionError.printStackTrace();
        }
      }

      // this case will only happen in case of wrong parameters. Try sending the error message.
      if (responseHandler instanceof TicketFlyServerUserErrorHandler) {
        try {
          responseHandler.sendResponse();

        } catch (IOException e) {
          System.err.format("%s: Can't send response to client %s - %s\n",  "Error", clientName,
              e.getMessage());
        }
      }
    }
  }

  @Override
  protected void finalize() {
    try {
      // clean up the client in case the system stopped.
      if (!clientSocket.isClosed()) {
        clientSocket.close();
      }

    } catch (IOException errorClosing) {
      System.err.format("%s: Couldn't close client socket %s - %s\n", "Error", makeThreadName(),
          errorClosing.getMessage());
    }
  }
}
