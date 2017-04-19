package com.episode6.hackit.typed.core;

import com.episode6.hackit.typed.testing.Rules;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Spy;
import org.mockito.junit.MockitoRule;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests {@link TypedKeyNamespace}
 */
public class TypedKeyNamespaceTest {

  @Rule public final MockitoRule mockito = Rules.mockito();

  @Spy TypedKeyNamespace mAnonymousNamespace = new TypedKeyNamespace("/");

  @Test(expected = NullPointerException.class)
  public void testNullDelinatorFails() {
    TypedKeyNamespace namespace = new TypedKeyNamespace(null);
  }

  @Test(expected = NullPointerException.class)
  public void testNullParentFails() {
    TypedKeyNamespace namespace = new TypedKeyNamespace(null, "childName");
  }

  @Test(expected = NullPointerException.class)
  public void testNullChildNameFails() {
    TypedKeyNamespace namespace = new TypedKeyNamespace(mAnonymousNamespace, null);
  }

  @Test
  public void testAnonNamespace() {
    TypedKeyNamespace namespace = new TypedKeyNamespace("/");

    String value = namespace.toString();

    assertThat(value).isEqualTo("");
  }

  @Test
  public void testFullNameOnlyBuiltOnce() {
    TypedKeyNamespace parentNamespace = spy(new TypedKeyNamespace(mAnonymousNamespace, "parent"));
    TypedKeyNamespace namespace = new TypedKeyNamespace(parentNamespace, "child");

    String value = namespace.toString();

    verify(mAnonymousNamespace).getNameForChild("parent");
    verify(parentNamespace).getNameForChild("child");
    assertThat(value).isEqualTo("parent/child");
  }

  @Test
  public void assertEquality() {
    TypedKeyNamespace parentNamespace = spy(new TypedKeyNamespace(mAnonymousNamespace, "parent"));
    TypedKeyNamespace namespace1 = new TypedKeyNamespace(parentNamespace, "child");
    TypedKeyNamespace namespace2 = new TypedKeyNamespace(parentNamespace, "child");

    assertThat(namespace1).isEqualTo(namespace2);
    assertThat(namespace1.hashCode()).isEqualTo(namespace2.hashCode());
  }

  @Test
  public void assertNonEquality() {
    TypedKeyNamespace parentNamespace = spy(new TypedKeyNamespace(mAnonymousNamespace, "parent"));
    TypedKeyNamespace namespace1 = new TypedKeyNamespace(parentNamespace, "child");
    TypedKeyNamespace namespace2 = new TypedKeyNamespace(parentNamespace, "child1");

    assertThat(namespace1).isNotEqualTo(namespace2);
    assertThat(namespace1.hashCode()).isNotEqualTo(namespace2.hashCode());
  }
}
