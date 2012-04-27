package com.ticketfly.core.server.response;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.Preconditions;
import com.ticketfly.core.server.request.ReverseStringUpdateCounterStrategy;
import com.ticketfly.core.server.request.TicketFlyServerRequestHandler;

@Immutable
public class ReverseStringDecorator implements ResponseDecorator {

  /**
   * It is the reverse string strategy to be decorated.
   */
  private final ReverseStringUpdateCounterStrategy requestHandlerStrategy;

  /**
   * Constructs a new decorator with the reversed input and the computed counter.
   * 
   * <p>Apr 27, 2012 1:32:29 AM</p>
   * @param processedRequest is the processed request by the server.
   */
  public ReverseStringDecorator(TicketFlyServerRequestHandler processedRequest) {
    Preconditions.checkArgument(processedRequest instanceof ReverseStringUpdateCounterStrategy);

    requestHandlerStrategy = (ReverseStringUpdateCounterStrategy)processedRequest; 
  }

  @Override
  public String decorate() {
    StringBuilder response = new StringBuilder();
    response.append(requestHandlerStrategy.getReverse());
    response.append(" ");
    response.append(requestHandlerStrategy.getComputationCounter().value());
    return response.toString();
  }

}
