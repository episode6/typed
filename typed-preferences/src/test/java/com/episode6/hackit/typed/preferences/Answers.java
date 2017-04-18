package com.episode6.hackit.typed.preferences;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.RETURNS_DEFAULTS;

/**
 *
 */
public class Answers {
  public static Answer<Object> builderAnswer() {
    return new Answer<Object>() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        Object mock = invocation.getMock();
        if( invocation.getMethod().getReturnType().isInstance( mock )){
          return mock;
        }
        return RETURNS_DEFAULTS.answer(invocation);
      }
    };
  }
}
