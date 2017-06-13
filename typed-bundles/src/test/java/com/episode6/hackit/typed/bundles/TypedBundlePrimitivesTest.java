package com.episode6.hackit.typed.bundles;

import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.typed.testing.Rules;
import org.junit.Rule;
import org.junit.Test;
import org.powermock.core.classloader.annotations.MockPolicy;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Tests TypedBundles with primitives
 */
@MockPolicy({TestResources.MockPolicy.class})
public class TypedBundlePrimitivesTest {

  final TestResources t = new TestResources();
  @Rule public final Mockspresso.Rule mockspresso = Rules.mockspressoBuilder()
      .testResources(t)
      .buildRule();

  static final BundleNamespace NAMESPACE = BundleNamespace.fromClass(TypedBundlePrimitivesTest.class);

  static final BundleKey<Boolean> BOOL_KEY = NAMESPACE.key(Boolean.class).named("boolKey").buildWithDefault(true);
  static final OptBundleKey<Boolean> BOOL_OPT_KEY = NAMESPACE.key(Boolean.class).named("boolOptKey").buildOptional();

  @Test
  public void testPrefDoesntExist() {
    t.testDoesntContain(BOOL_KEY);
    t.testDoesntContain(BOOL_OPT_KEY);
  }
}
