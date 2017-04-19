package com.episode6.hackit.typed.bundles;

import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.typed.testing.Rules;
import org.junit.Rule;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Placeholder
 */
public class BundleKeyTest {

  @Rule public final Mockspresso.Rule mMockspresso = Rules.mockspresso();

  @Test
  public void testSomething() {
    assertThat(10).isEqualTo(9+1);
  }
}
