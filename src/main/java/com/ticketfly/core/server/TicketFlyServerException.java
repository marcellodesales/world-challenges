package com.ticketfly.core.server;

public class TicketFlyServerException extends Exception {

  /**
   * The version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * <p>Apr 26, 2012 4:57:36 PM</p>
   * @param message the error message from the server.
   */
  public TicketFlyServerException(String message) {
    super(message);
  }

  /**
   * <p>Apr 26, 2012 4:57:36 PM</p>
   * @param message the error message from the server.
   * @param cause is what caused the error.
   */
  public TicketFlyServerException(String message, Throwable cause) {
    super(message, cause);
  }
}
