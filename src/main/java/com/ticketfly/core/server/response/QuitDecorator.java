package com.ticketfly.core.server.response;

/**
 * 
 * The QuitDecorator is a decorator used.
 *
 * <p>Apr 27, 2012 2:33:54 AM</p>
 * @author Marcello de Sales (marcello.desales@gmail.com)
 *
 */
public class QuitDecorator implements ResponseDecorator {

  @Override
  public String decorate() {
    return "Have a nice day!";
  }

}
