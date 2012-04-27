package com.ticketfly.core.server.request;

import javax.annotation.concurrent.Immutable;

/**
 * 
 * The RequestInformation is the information from the request.
 *
 * <p>Apr 27, 2012 12:22:09 AM</p>
 * @author Marcello de Sales (marcello.desales@gmail.com)
 *
 */
@Immutable
public class RequestInfo {

  /**
   * The request line broken into parameters.
   */
  public final String[] commandParameters;
  /**
   * The identified type of request.
   */
  public final RequestType type;

  /**
   * Creates a new request information with the given input parameters transformed from the 
   * client input.
   * <p>Apr 27, 2012 12:23:00 AM</p>
   * @param inputParameters is the original input broken into parameters.
   * @param type is the type identified by the Interpreter.
   */
  public RequestInfo(String[] inputParameters, RequestType type) {
    this.commandParameters = inputParameters;
    this.type = type;
  }

  /**
   * <p>Apr 27, 2012 1:58:58 AM</p> 
   * @return the command parameters provided by the client.
   */
  public String[] getCommandParameters() {
    return commandParameters;
  }

  /**
   * <p>Apr 27, 2012 1:59:29 AM</p> 
   * @return the request type identified.
   */
  public RequestType getRequestType() {
    return this.type;
  }
}
