package com.episode6.hackit.typed.core;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Tests {@link Preconditions}
 */
public class PreconditionsTest {

  @Test
  public void testCheckNotNullPass() {
    String testString = "hi";

    String retVal = Preconditions.checkNotNull(testString);

    assertThat(retVal).isEqualTo(testString);
  }

  @Test(expected = NullPointerException.class)
  public void testCheckNotNullFail() {
    String testString = null;

    Preconditions.checkNotNull(testString);
  }
}
