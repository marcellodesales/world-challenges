package com.ticketfly.core.server.request.interpreter;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ticketfly.core.server.request.RequestInfo;
import com.ticketfly.core.server.request.RequestType;

/**
 * The RequestInterpreterTest is the test cases for the service.
 *
 * <p>Apr 27, 2012 3:55:28 AM</p>
 * @author Marcello de Sales (marcello.desales@gmail.com)
 *
 */
public class RequestInterpreterTest {

  @Test
  public void testInterpretationOfReverseStringAndIncrement() {
    String clientInput = "Marcello";
    try {
      RequestInfo requestInfo = RequestTypeInterpreter.interpret(clientInput);
      assertNotNull(requestInfo);

      assertNotNull(requestInfo.getCommandParameters());
      assertEquals("The parameter should be 1", 1, requestInfo.getCommandParameters().length);
      assertEquals("The parameter should contain the input.", clientInput,
          requestInfo.getCommandParameters()[0]);

      assertNotNull("The request should be given.", requestInfo.getRequestType());
      assertEquals(RequestType.REVERSE_STRING, requestInfo.getRequestType());

    } catch (RequestInterpretationException e) {
      fail("This exception should never occur: " + e.getMessage());
    }
  }

  @Test
  public void testInterpretationOfResetIncrementAndRevertInput() {
    String clientInput = "Marcello 2012";
    try {
      RequestInfo requestInfo = RequestTypeInterpreter.interpret(clientInput);
      assertNotNull(requestInfo);

      assertNotNull(requestInfo.getCommandParameters());
      assertEquals("The parameter should be 2", 2, requestInfo.getCommandParameters().length);
      assertEquals("The parameter should contain the input.", "Marcello",
          requestInfo.getCommandParameters()[0]);
      assertEquals("The parameter should contain the input.", "2012",
          requestInfo.getCommandParameters()[1]);

      assertNotNull("The request should be given.", requestInfo.getRequestType());
      assertEquals(RequestType.REVERSE_AND_RESET_COUNTER, requestInfo.getRequestType());

    } catch (RequestInterpretationException e) {
      fail("This exception should never occur: " + e.getMessage());
    }
  }

  @Test(expected = RequestInterpretationException.class)
  public void testInterpretationWithWrongParameter() throws RequestInterpretationException {
    String clientInput = "Marcello abcd";
    RequestTypeInterpreter.interpret(clientInput);
    fail("This exception should never occur");
  }

  @Test(expected = RequestInterpretationException.class)
  public void testExceptionThrownOnEmptyRequest() throws RequestInterpretationException {
    String clientInput = "";
    RequestTypeInterpreter.interpret(clientInput);
  }

}
