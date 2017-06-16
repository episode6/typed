package com.episode6.hackit.typed.bundles;

import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.typed.testing.Rules;
import org.junit.Rule;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Tests {@link BundleNamespace}
 */
public class BundleNamespaceTest {

  class InnerClassTest {}

  @Test
  public void testNamespaceFromClass() {
    BundleNamespace namespace = BundleNamespace.fromClass(InnerClassTest.class);
    BundleKey<Boolean> key = namespace.key(Boolean.class).named("testKey").buildWithDefault(false);

    assertThat(namespace.toString()).isEqualTo("com.episode6.hackit.typed.bundles.BundleNamespaceTest.InnerClassTest");
    assertThat(key.getKeyName().toString()).isEqualTo("com.episode6.hackit.typed.bundles.BundleNamespaceTest.InnerClassTest.testKey");
  }

  @Test
  public void testAnonymousNamespace() {
    BundleNamespace namespace = BundleNamespace.ANONYMOUS;
    BundleKey<Boolean> key = namespace.key(Boolean.class).named("testKey").buildWithDefault(false);

    assertThat(namespace.toString()).isEqualTo("");
    assertThat(key.getKeyName().toString()).isEqualTo("testKey");
  }
}
