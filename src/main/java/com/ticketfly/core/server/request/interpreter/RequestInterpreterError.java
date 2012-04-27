package com.ticketfly.core.server.request.interpreter;

/**
 * 
 * The RequestInterpreterError is the errors that might occur while interpreting the client's
 * request.
 *
 * <p>Apr 27, 2012 2:01:25 AM</p>
 * @author Marcello de Sales (marcello.desales@gmail.com)
 *
 */
public enum RequestInterpreterError {

  /**
   * When a request has not been identified. It may occur if the user types more than 2 parameters.
   */
  UNKNOWN_REQUEST,
  /**
   * When the request parameter is incorrect (converting a String to Number, etc).
   */
  INCORRECT_REQUEST_PARAMETER, 
  /**
   * For requests with one empty line.
   */
  EMPTY_REQUEST;

  /**
   * @return the error code;
   */
  public int getCode() {
    return (this.ordinal() + 1) * 3;
  }

  /**
   * <p>Apr 26, 2012 11:07:44 PM</p> 
   * @return the error message based on the code.
   */
  public String getMessage() {
    switch (this) {
      case INCORRECT_REQUEST_PARAMETER:
        return "The parameter provided is incorrect.";

      case UNKNOWN_REQUEST:
      default:
        return "The given request is incorrect.";
    }
  }

  @Override
  public String toString() {
    return name() + "(" + getCode() + ")";
  }
}
