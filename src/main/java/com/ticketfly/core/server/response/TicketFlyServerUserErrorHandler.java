package com.ticketfly.core.server.response;

import java.net.Socket;

/**
 * 
 * The TicketFlyServerUserErrorHandler is the error response handler.
 *
 * <p>Apr 27, 2012 1:44:25 AM</p>
 * @author Marcello de Sales (marcello.desales@gmail.com)
 *
 */
public class TicketFlyServerUserErrorHandler extends TicketFlyServerResponseHandler {

  public TicketFlyServerUserErrorHandler(Socket clientSocket, ResponseDecorator decorator) {
    super(clientSocket, decorator);
  }

  public static ResponseDecorator makeErrorDecorator(final String errorMessage) {
    return new ResponseDecorator() {
      @Override
      public String decorate() {
        return errorMessage;
      }
    };
  }

}
