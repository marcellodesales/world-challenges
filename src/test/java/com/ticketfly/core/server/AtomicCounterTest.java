package com.ticketfly.core.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;

public class AtomicCounterTest {

  @Test
  public void testCounterInitialState() {
    final AtomicCounter c = new AtomicCounter();
    assertNotNull("Must be able to create a counter", c);
    assertEquals("Initial value must be zero", 0, c.value());
  }

  @Test
  public void testIncrement() {
    final AtomicCounter c = new AtomicCounter();
    assertNotNull("Must be able to create a counter", c);
    assertEquals("Initial value must be zero", 0, c.value());

    for (int i = 1; i <= 10; i++) {
      c.increment();
      assertEquals("The counter should be incremented after calling increment", i, c.value());
    }
  }

  @Test
  public void testSetIncrement() {
    final AtomicCounter c = new AtomicCounter();
    assertNotNull("Must be able to create a counter", c);
    assertEquals("Initial value must be zero", 0, c.value());

    for (int i = 1; i <= 10; i++) {
      c.set(i * 10);
      assertEquals("The counter should be incremented after calling increment", i * 10, c.value());
    }
  }
  
}
