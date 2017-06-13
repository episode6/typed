package com.episode6.hackit.typed.bundles;

import android.os.Build;
import android.os.Bundle;
import android.util.Size;
import android.util.SizeF;
import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.typed.testing.Rules;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoRule;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Tests {@link DirectBundleTranslators}
 */
@PrepareForTest({Build.VERSION.class})
public class DirectBundleTranslatorsTest {

  private static final String TEST_KEY_NAME = "someTestKey";

  @Rule public final Mockspresso.Rule mockspresso = Rules.mockspresso();

  @Mock Bundle mBundle;

  @Test
  public void testBooleanGet() {
    when(mBundle.getBoolean(TEST_KEY_NAME)).thenReturn(true);
    BundleTranslator translator = get(Boolean.class);

    Boolean result = (Boolean) translator.getFromBundle(mBundle, TEST_KEY_NAME);

    assertThat(result).isTrue();
    verify(mBundle).getBoolean(TEST_KEY_NAME);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testBooleanSet() {
    BundleTranslator translator = get(Boolean.class);

    translator.writeToBundle(mBundle, TEST_KEY_NAME, true);

    verify(mBundle).putBoolean(TEST_KEY_NAME, true);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testIntGet() {
    when(mBundle.getInt(TEST_KEY_NAME)).thenReturn(10);
    BundleTranslator translator = get(Integer.class);

    Integer result = (Integer) translator.getFromBundle(mBundle, TEST_KEY_NAME);

    assertThat(result).isEqualTo(10);
    verify(mBundle).getInt(TEST_KEY_NAME);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testIntSet() {
    BundleTranslator translator = get(Integer.class);

    translator.writeToBundle(mBundle, TEST_KEY_NAME, 7);

    verify(mBundle).putInt(TEST_KEY_NAME, 7);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testStringGet() {
    when(mBundle.getString(TEST_KEY_NAME)).thenReturn("hithere");
    BundleTranslator translator = get(String.class);

    String result = (String) translator.getFromBundle(mBundle, TEST_KEY_NAME);

    assertThat(result).isEqualTo("hithere");
    verify(mBundle).getString(TEST_KEY_NAME);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testStringSet() {
    BundleTranslator translator = get(String.class);

    translator.writeToBundle(mBundle, TEST_KEY_NAME, "coolbeans");

    verify(mBundle).putString(TEST_KEY_NAME, "coolbeans");
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testFloatGet() {
    when(mBundle.getFloat(TEST_KEY_NAME)).thenReturn(10.5f);
    BundleTranslator translator = get(Float.class);

    Float result = (Float) translator.getFromBundle(mBundle, TEST_KEY_NAME);

    assertThat(result).isEqualTo(10.5f);
    verify(mBundle).getFloat(TEST_KEY_NAME);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testFloatSet() {
    BundleTranslator translator = get(Float.class);

    translator.writeToBundle(mBundle, TEST_KEY_NAME, 7.2f);

    verify(mBundle).putFloat(TEST_KEY_NAME, 7.2f);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testLongGet() {
    when(mBundle.getLong(TEST_KEY_NAME)).thenReturn(20L);
    BundleTranslator translator = get(Long.class);

    Long result = (Long) translator.getFromBundle(mBundle, TEST_KEY_NAME);

    assertThat(result).isEqualTo(20L);
    verify(mBundle).getLong(TEST_KEY_NAME);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testLongSet() {
    BundleTranslator translator = get(Long.class);

    translator.writeToBundle(mBundle, TEST_KEY_NAME, 78L);

    verify(mBundle).putLong(TEST_KEY_NAME, 78L);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testDoubleGet() {
    when(mBundle.getDouble(TEST_KEY_NAME)).thenReturn(10.6d);
    BundleTranslator translator = get(Double.class);

    Double result = (Double) translator.getFromBundle(mBundle, TEST_KEY_NAME);

    assertThat(result).isEqualTo(10.6d);
    verify(mBundle).getDouble(TEST_KEY_NAME);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testDoubleSet() {
    BundleTranslator translator = get(Double.class);

    translator.writeToBundle(mBundle, TEST_KEY_NAME, 7.3d);

    verify(mBundle).putDouble(TEST_KEY_NAME, 7.3d);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testShortGet() {
    when(mBundle.getShort(TEST_KEY_NAME)).thenReturn((short)20);
    BundleTranslator translator = get(Short.class);

    Short result = (Short) translator.getFromBundle(mBundle, TEST_KEY_NAME);

    assertThat(result).isEqualTo((short)20);
    verify(mBundle).getShort(TEST_KEY_NAME);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testShortSet() {
    BundleTranslator translator = get(Short.class);

    translator.writeToBundle(mBundle, TEST_KEY_NAME, (short)2);

    verify(mBundle).putShort(TEST_KEY_NAME, (short)2);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testCharGet() {
    when(mBundle.getChar(TEST_KEY_NAME)).thenReturn('h');
    BundleTranslator translator = get(Character.class);

    Character result = (Character) translator.getFromBundle(mBundle, TEST_KEY_NAME);

    assertThat(result).isEqualTo('h');
    verify(mBundle).getChar(TEST_KEY_NAME);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testCharSet() {
    BundleTranslator translator = get(Character.class);

    translator.writeToBundle(mBundle, TEST_KEY_NAME, 'c');

    verify(mBundle).putChar(TEST_KEY_NAME, 'c');
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testByteGet() {
    when(mBundle.getByte(TEST_KEY_NAME)).thenReturn((byte)1);
    BundleTranslator translator = get(Byte.class);

    Byte result = (Byte) translator.getFromBundle(mBundle, TEST_KEY_NAME);

    assertThat(result).isEqualTo((byte)1);
    verify(mBundle).getByte(TEST_KEY_NAME);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testByteSet() {
    BundleTranslator translator = get(Byte.class);

    translator.writeToBundle(mBundle, TEST_KEY_NAME, (byte)0);

    verify(mBundle).putByte(TEST_KEY_NAME, (byte)0);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testBundleGet() {
    Bundle bundle = mock(Bundle.class);
    when(mBundle.getBundle(TEST_KEY_NAME)).thenReturn(bundle);
    BundleTranslator translator = get(Bundle.class);

    Bundle result = (Bundle) translator.getFromBundle(mBundle, TEST_KEY_NAME);

    assertThat(result).isEqualTo(bundle);
    verify(mBundle).getBundle(TEST_KEY_NAME);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testBundleSet() {
    Bundle bundle = mock(Bundle.class);
    BundleTranslator translator = get(Bundle.class);

    translator.writeToBundle(mBundle, TEST_KEY_NAME, bundle);

    verify(mBundle).putBundle(TEST_KEY_NAME, bundle);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testTypedBundleGet() {
    Bundle bundle = mock(Bundle.class);
    when(mBundle.getBundle(TEST_KEY_NAME)).thenReturn(bundle);
    BundleTranslator translator = get(TypedBundle.class);

    TypedBundle result = (TypedBundle) translator.getFromBundle(mBundle, TEST_KEY_NAME);

    assertThat(result.asBundle()).isEqualTo(bundle);
    verify(mBundle).getBundle(TEST_KEY_NAME);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testTypedBundleSet() {
    TypedBundle typedBundle = mock(TypedBundle.class);
    Bundle bundle = mock(Bundle.class);
    when(typedBundle.asBundle()).thenReturn(bundle);
    BundleTranslator translator = get(TypedBundle.class);

    translator.writeToBundle(mBundle, TEST_KEY_NAME, typedBundle);

    verify(mBundle).putBundle(TEST_KEY_NAME, bundle);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testSizeGet() {
    setSdkVersion(21);
    Size size = mock(Size.class);
    when(mBundle.getSize(TEST_KEY_NAME)).thenReturn(size);
    BundleTranslator translator = get(Size.class);

    Size result = (Size) translator.getFromBundle(mBundle, TEST_KEY_NAME);

    assertThat(result).isEqualTo(size);
    verify(mBundle).getSize(TEST_KEY_NAME);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testSizeSet() {
    setSdkVersion(21);
    Size size = mock(Size.class);
    BundleTranslator translator = get(Size.class);

    translator.writeToBundle(mBundle, TEST_KEY_NAME, size);

    verify(mBundle).putSize(TEST_KEY_NAME, size);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testSizeFGet() {
    setSdkVersion(21);
    SizeF size = mock(SizeF.class);
    when(mBundle.getSizeF(TEST_KEY_NAME)).thenReturn(size);
    BundleTranslator translator = get(SizeF.class);

    SizeF result = (SizeF) translator.getFromBundle(mBundle, TEST_KEY_NAME);

    assertThat(result).isEqualTo(size);
    verify(mBundle).getSizeF(TEST_KEY_NAME);
    verifyNoMoreInteractions(mBundle);
  }

  @Test
  public void testSizeFSet() {
    setSdkVersion(21);
    SizeF size = mock(SizeF.class);
    BundleTranslator translator = get(SizeF.class);

    translator.writeToBundle(mBundle, TEST_KEY_NAME, size);

    verify(mBundle).putSizeF(TEST_KEY_NAME, size);
    verifyNoMoreInteractions(mBundle);
  }

  private static void setSdkVersion(int version) {
    Whitebox.setInternalState(Build.VERSION.class, "SDK_INT", version);
  }

  private static BundleTranslator get(Class<?> clazz) {
    return DirectBundleTranslators.getDirectTranslator(clazz);
  }
}
