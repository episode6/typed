package com.episode6.hackit.typed.preferences;

import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.mockspresso.mockito.MockitoPlugin;

import org.junit.Rule;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Placeholder
 */
public class PrefKeyTest {

  @Rule public final Mockspresso.Rule mMockspresso = Mockspresso.Builders.simple()
      .plugin(MockitoPlugin.getInstance())
      .buildRule();

  @Test
  public void testSomething() {
    assertThat(10).isEqualTo(9+1);
  }
}
