package com.episode6.hackit.typed.core.util;

import com.episode6.hackit.typed.core.Rules;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoRule;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests {@link Suppliers}
 */
public class SuppliersTest {

  @Rule public final MockitoRule mockito = Rules.mockito();

  @Mock Supplier<String> mStringSupplier;

  @Test
  public void testCallsOnlyOnce() {
    when(mStringSupplier.get()).thenReturn("hi there");

    Supplier<String> memoizedSupplier = Suppliers.memoize(mStringSupplier);
    String value1 = memoizedSupplier.get();
    String value2 = memoizedSupplier.get();
    String value3 = memoizedSupplier.get();

    verify(mStringSupplier, times(1)).get();
    assertThat(value1)
        .isEqualTo(value2)
        .isEqualTo(value3)
        .isEqualTo("hi there");
  }

  @Test
  public void testThrowingSupplier() {
    when(mStringSupplier.get()).thenThrow(NullPointerException.class);
    Supplier<String> memoizedSupplier = Suppliers.memoize(mStringSupplier);

    // ensure that our memoized supplier throws an exception every time its called.
    for (int i = 0; i<2; i++) {
      try {
        memoizedSupplier.get();
        fail("Expected a NullPointerException");
      } catch (NullPointerException e) {
        // this should happen
      }
    }
  }
}
