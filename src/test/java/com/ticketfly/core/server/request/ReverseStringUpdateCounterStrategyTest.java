package com.ticketfly.core.server.request;

import org.junit.Test;

import com.ticketfly.core.server.AtomicCounter;

import static org.junit.Assert.*;

public class ReverseStringUpdateCounterStrategyTest {

  @Test
  public void testReverseStringAndIncrementCounter() {
    AtomicCounter counter = new AtomicCounter();
    String request = "Marcello";
    RequestInfo info = new RequestInfo(request.split(" "), RequestType.REVERSE_STRING);
    ReverseStringUpdateCounterStrategy reverseIncrement = new ReverseStringUpdateCounterStrategy(
      info, counter);

    assertNotNull(reverseIncrement);
    assertNotNull("The reverse must be built.", reverseIncrement.getReverse());
    assertEquals("ollecraM", reverseIncrement.getReverse());

    assertNotNull("The counter reference must be still not null.", counter);
    assertNotNull("The reverse must be built.", reverseIncrement.getComputationCounter());
    assertSame("The reverse must be built.", counter, reverseIncrement.getComputationCounter());
    assertEquals("The counter must have changed its value.", 1, counter.value());
  }

  @Test
  public void testReverseStringAndSetCounter() {
    AtomicCounter counter = new AtomicCounter();
    String request = "JAVA_LANG 35";
    RequestInfo info = new RequestInfo(request.split(" "), RequestType.REVERSE_AND_RESET_COUNTER);
    ReverseStringUpdateCounterStrategy reverseSet = new ReverseStringUpdateCounterStrategy(
      info, counter);

    assertNotNull(reverseSet);
    assertNotNull("The reverse must be built.", reverseSet.getReverse());
    assertEquals("GNAL_AVAJ", reverseSet.getReverse());

    assertNotNull("The counter reference must be still not null.", counter);
    assertNotNull("The reverse must be built.", reverseSet.getComputationCounter());
    assertSame("The reverse must be built.", counter, reverseSet.getComputationCounter());
    assertEquals("The counter must have changed its value.", 36, counter.value());
  }

  @Test
  public void testReverseStringAndIncrementDontSetCounterWithSmallerValues() {
    AtomicCounter counter = new AtomicCounter();
    counter.set(50);
    String request = "JAVA_LANG 35";
    RequestInfo info = new RequestInfo(request.split(" "), RequestType.REVERSE_AND_RESET_COUNTER);
    ReverseStringUpdateCounterStrategy reverseSet = new ReverseStringUpdateCounterStrategy(
      info, counter);

    assertNotNull(reverseSet);
    assertNotNull("The reverse must be built.", reverseSet.getReverse());
    assertEquals("GNAL_AVAJ", reverseSet.getReverse());

    assertNotNull("The counter reference must be still not null.", counter);
    assertNotNull("The reverse must be built.", reverseSet.getComputationCounter());
    assertSame("The reverse must be built.", counter, reverseSet.getComputationCounter());
    assertEquals("The counter must have changed its value.", 51, counter.value());
  }
}
