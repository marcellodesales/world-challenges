package com.ticketfly.core.server.request;

import com.ticketfly.core.server.AtomicCounter;

/**
 * 
 * The TicketFlyServerRequestHandler defines the type of request handlers for the server.
 *
 * <p>Apr 27, 2012 12:59:47 AM</p>
 * @author Marcello de Sales (marcello.desales@gmail.com)
 *
 */
public interface TicketFlyServerRequestHandler {

  /**
   * <p>Apr 27, 2012 1:58:11 AM</p> 
   * @return the shared computation counter.
   */
  public AtomicCounter getComputationCounter();

  /**
   * <p>Apr 27, 2012 1:58:28 AM</p> 
   * @return the request information.
   */
  public RequestInfo getRequestInfo();
}
