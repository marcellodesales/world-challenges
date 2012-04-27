package com.ticketfly.core.server.request;

/**
 * The RequestType is the request type interpreted by this interpreter.
 *
 * <p>Apr 26, 2012 4:31:31 PM</p>
 * @author Marcello de Sales (marcello.desales@gmail.com)
 *
 */
public enum RequestType {
  /**
   * For requests with one single String.
   */
  REVERSE_STRING,
  /**
   * For requests with a String and a reset counter.
   */
  REVERSE_AND_RESET_COUNTER,
  /**
   * Disconnects the client from the server.
   */
  QUIT;
}

