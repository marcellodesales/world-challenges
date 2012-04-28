package com.ticketfly.core.server.request;

import javax.annotation.concurrent.Immutable;

import com.ticketfly.TFlyService;
import com.ticketfly.core.server.AtomicCounter;

/**
 * 
 * The ReverseStringUpdateCounterStrategy is responsible for resolving the constraints of the
 * requirement.
 * 
 * "...If the sequence number is larger than the current sequence number in the server, the server
 * must reset its sequence number to at least one higher than the passed in number."
 * 
 * <p>
 * Apr 27, 2012 1:51:57 AM
 * </p>
 * 
 * @author Marcello de Sales (marcello.desales@gmail.com)
 * 
 */
@Immutable
public class ReverseStringUpdateCounterStrategy
    implements
      TicketFlyServerRequestHandler,
      RevertStringStrategy {
  /**
   * The ticket fly service that is used to revert the string.
   */
  private static final TFlyService tflyService = new TFlyService();
  /**
   * The request information containing the parameters and the type.
   */
  private final RequestInfo requestInfo;
  /**
   * The shared response counter to be used.
   */
  private final AtomicCounter serverSequenceCounter;
  /**
   * The reverse string computed during initialization.
   */
  private String reverse;

  /**
   * 
   * <p>
   * Apr 27, 2012 1:51:46 AM
   * </p>
   * 
   * @param requestInfo
   * @param counter
   */
  public ReverseStringUpdateCounterStrategy(RequestInfo requestInfo, AtomicCounter counter) {
    this.requestInfo = requestInfo;
    this.serverSequenceCounter = counter;

    synchronized (serverSequenceCounter) {
      switch (requestInfo.getRequestType()) {
        case REVERSE_AND_RESET_COUNTER:
          int requestedValue = Integer.valueOf(requestInfo.getCommandParameters()[1]);
          if (requestedValue > serverSequenceCounter.value())
            serverSequenceCounter.set(requestedValue);

          //$FALL-THROUGH$ as the value needs to be be incremented after being reset.
        case REVERSE_STRING:
          serverSequenceCounter.increment();
      }
    }

    int retriesCount = 0;
    while(true){
      try {
          reverse = tflyService.execute(requestInfo.getCommandParameters()[0]);
          break;

      } catch (TFlyService.TFlyServiceException serviceUnavailable){
        retriesCount++;
      }
    }
    if (retriesCount > 0) {
      System.err.format("WARNING: The tfly service was retried %d times for request %s.\n", 
        retriesCount, requestInfo.getCommandParameters()[0]);
    }
  }

  @Override
  public RequestInfo getRequestInfo() {
    return requestInfo;
  }

  @Override
  public AtomicCounter getComputationCounter() {
    return serverSequenceCounter;
  }

  @Override
  public String getReverse() {
    return reverse;
  }
}
