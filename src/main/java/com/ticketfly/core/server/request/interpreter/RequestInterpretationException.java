package com.ticketfly.core.server.request.interpreter;

import com.ticketfly.core.server.TflyServiceException;

public class RequestInterpretationException extends TflyServiceException {

  /**
   * The version.
   */
  private static final long serialVersionUID = 1L;
  /**
   * The client input as it was typed.
   */
  private String clientInput;
  /**
   * The request Interpreter error
   */
  private RequestInterpreterError error;

  /**
   * When the interpret could not figure out what the request was.
   * <p>Apr 26, 2012 5:31:48 PM</p>
   * @param message is the error message.
   */
  public RequestInterpretationException(String input, RequestInterpreterError error) {
    super(error + ": " + error.getMessage());
    clientInput = input;
    this.error = error;
  }

  /**
   * <p>Apr 27, 2012 2:00:48 AM</p> 
   * @return The original client input.
   */
  public String getClientInput() {
    return clientInput;
  }

  /**
   * <p>Apr 27, 2012 2:01:05 AM</p> 
   * @return The error code for this exception.
   */
  public RequestInterpreterError getErrorCode() {
    return error;
  }

}
