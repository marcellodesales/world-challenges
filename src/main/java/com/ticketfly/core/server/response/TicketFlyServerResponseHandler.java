package com.ticketfly.core.server.response;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.annotation.concurrent.Immutable;

/**
 * The strategy that reverses strings from the requests.
 * 
 * @author Marcello de Sales (marcello.desales@gmail.com).
 * 
 */
@Immutable
public class TicketFlyServerResponseHandler {

  private final ResponseDecorator responseDecorator;
  private final Socket clientSocket;

  public TicketFlyServerResponseHandler(Socket clientSocket, ResponseDecorator decorator) {
    this.clientSocket = clientSocket;
    responseDecorator = decorator;
  }

  /**
   * <p>Apr 26, 2012 8:46:36 PM</p> 
   * @throws IOException if any error occurs while sending the message.
   */
  public void sendResponse() throws IOException {
    String response = responseDecorator.decorate();
    sendResponseToClient(response);
  }

  /**
   * Hands the client connection, handling the Request and Response.
   * 
   * @param clientSocket the socket client connection.
   * @return The received String from the client connection.
   * @throws IOException if any problem occurs while sending the message.
   */
  private void sendResponseToClient(String response) throws IOException {
    OutputStreamWriter outputStreat = new OutputStreamWriter(clientSocket.getOutputStream());
    PrintWriter writer = new PrintWriter(new BufferedWriter(outputStreat), true);
    writer.println(response);
  }

}
