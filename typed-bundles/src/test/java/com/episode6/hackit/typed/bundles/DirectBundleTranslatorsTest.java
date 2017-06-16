package com.episode6.hackit.typed.bundles;

import android.os.Build;
import android.os.Bundle;
import android.util.Size;
import android.util.SizeF;
import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.typed.testing.Rules;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.powermock.core.classloader.annotations.MockPolicy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Tests {@link DirectBundleTranslators}
 */
@PrepareForTest({Build.VERSION.class})
@MockPolicy(TestResources.MockPolicy.class)
public class DirectBundleTranslatorsTest {

  private static final String TEST_KEY_NAME = "someTestKey";

  final TestResources t = new TestResources();
  @Rule public final Mockspresso.Rule mockspresso = Rules.mockspressoBuilder()
      .testResources(t)
      .buildRule();

  private <T> void testGet(Class<T> clazz) {
    BundleTranslator translator = get(clazz);
    TestResources.Tester<T> tester = t.getGetTester(clazz);
    T expected = tester.setup(TEST_KEY_NAME);

    T result = (T) translator.getFromBundle(t.bundle, TEST_KEY_NAME);

    assertThat(result).isEqualTo(expected);
    InOrder inOrder = inOrder(t.bundle, t.gson);
    tester.verify(TEST_KEY_NAME, inOrder);
    verifyNoMoreInteractions(t.bundle, t.gson);
  }

  private <T> void testPut(Class<T> clazz) {
    BundleTranslator translator = get(clazz);
    TestResources.Tester<T> tester = t.getPutTester(clazz);
    T putVal = tester.setup(TEST_KEY_NAME);

    translator.writeToBundle(t.bundle, TEST_KEY_NAME, putVal);

    InOrder inOrder = inOrder(t.bundle, t.gson);
    tester.verify(TEST_KEY_NAME, inOrder);
    verifyNoMoreInteractions(t.bundle, t.gson);
  }

  @Test
  public void testBooleanGet() {
    testGet(Boolean.class);
  }

  @Test
  public void testBooleanSet() {
    testPut(Boolean.class);
  }

  @Test
  public void testIntGet() {
    testGet(Integer.class);
  }

  @Test
  public void testIntSet() {
    testPut(Integer.class);
  }

  @Test
  public void testStringGet() {
    testGet(String.class);
  }

  @Test
  public void testStringSet() {
    testPut(String.class);
  }

  @Test
  public void testFloatGet() {
    testGet(Float.class);
  }

  @Test
  public void testFloatSet() {
    testPut(Float.class);
  }

  @Test
  public void testLongGet() {
    testGet(Long.class);
  }

  @Test
  public void testLongSet() {
    testPut(Long.class);
  }

  @Test
  public void testDoubleGet() {
    testGet(Double.class);
  }

  @Test
  public void testDoubleSet() {
    testPut(Double.class);
  }

  @Test
  public void testShortGet() {
    testGet(Short.class);
  }

  @Test
  public void testShortSet() {
    testPut(Short.class);
  }

  @Test
  public void testCharGet() {
    testGet(Character.class);
  }

  @Test
  public void testCharSet() {
    testPut(Character.class);
  }

  @Test
  public void testByteGet() {
    testGet(Byte.class);
  }

  @Test
  public void testByteSet() {
    testPut(Byte.class);
  }

  @Test
  public void testBundleGet() {
    testGet(Bundle.class);
  }

  @Test
  public void testBundleSet() {
    testPut(Bundle.class);
  }

  @Test
  public void testCharSeqGet() {
    testGet(CharSequence.class);
  }

  @Test
  public void testCharSeqSet() {
    testPut(CharSequence.class);
  }

  @Test
  public void testSizeGet() {
    setSdkVersion(21);
    testGet(Size.class);
  }

  @Test
  public void testSizeSet() {
    setSdkVersion(21);
    testPut(Size.class);
  }

  @Test
  public void testSizeFGet() {
    setSdkVersion(21);
    testGet(SizeF.class);
  }

  @Test
  public void testSizeFSet() {
    setSdkVersion(21);
    testPut(SizeF.class);
  }

  private static void setSdkVersion(int version) {
    Whitebox.setInternalState(Build.VERSION.class, "SDK_INT", version);
  }

  private static BundleTranslator get(Class<?> clazz) {
    return DirectBundleTranslators.getDirectTranslator(clazz);
  }
}
