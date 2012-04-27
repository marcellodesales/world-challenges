package com.ticketfly.core.server.request.interpreter;

import com.ticketfly.core.server.request.RequestInfo;
import com.ticketfly.core.server.request.RequestType;

/**
 * 
 * The RequestTypeInterpreter processes the input and identify its type.
 * 
 * <p>
 * Apr 26, 2012 5:18:56 PM
 * </p>
 * 
 * @author Marcello de Sales (marcello.desales@gmail.com)
 * 
 */
public class RequestTypeInterpreter {

  /**
   * Interprets the given input and returns the type of the request.
   * <p>
   * Apr 26, 2012 10:52:58 PM
   * </p>
   * @param is the complete client input.
   * @return the request information with parameters and type from the request.
   * @throws RequestInterpretationException if a problem with the request happens.
   */
  public static RequestInfo interpret(String clientInput) throws RequestInterpretationException {
    if (clientInput.trim().length() == 0) {
      throw new RequestInterpretationException(clientInput, RequestInterpreterError.EMPTY_REQUEST);
    }
    String[] input = clientInput.split(" ");
    switch (input.length) {
      // Requests with only one single String, the request is to reverse.
      case 1:
        if (clientInput.equalsIgnoreCase("/q") || clientInput.equalsIgnoreCase("/quit")) {
          return new RequestInfo(input, RequestType.QUIT);

        } else {
          return new RequestInfo(input, RequestType.REVERSE_STRING);
        }

      // Requests with two parameters.
      case 2:
        try {
          // verify if the second parameter is an Integer.
          Integer.valueOf(input[1]);
          return new RequestInfo(input, RequestType.REVERSE_AND_RESET_COUNTER);

        } catch (NumberFormatException incorrectValue) {
          RequestInterpreterError error = RequestInterpreterError.INCORRECT_REQUEST_PARAMETER;
          throw new RequestInterpretationException(clientInput, error);
        }
        // Other cases can be plugged here later.

      default:
        RequestInterpreterError error = RequestInterpreterError.UNKNOWN_REQUEST;
        throw new RequestInterpretationException(clientInput, error);
    }
  }
}
