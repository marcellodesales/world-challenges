package com.ticketfly.core.server;

public enum ServerTerminationError {

  CANT_BIND_PORT, CLIENT_CONNECTION_ERROR;

  /**
   * @return the error code;
   */
  public int getCode() {
    return (this.ordinal() + 1) * 3;
  }

  @Override
  public String toString() {
    return name() + "(" + getCode() + ")";
  }
}
