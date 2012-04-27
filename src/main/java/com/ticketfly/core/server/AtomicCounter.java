package com.ticketfly.core.server;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * The AtomicCounter is the shared counter that uses an Atomic Integer.
 *
 * <p>Apr 26, 2012 11:46:16 PM</p>
 * @author Marcello de Sales (marcello.desales@gmail.com)
 *
 */
public class AtomicCounter {

  private AtomicInteger c = new AtomicInteger(0);

  public void increment() {
    c.incrementAndGet();
  }

  public void set(int newValue) {
    c.set(newValue);
  }

  public int value() {
    return c.get();
  }

}
