package com.episode6.hackit.typed.preferences;

import android.content.SharedPreferences;
import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.mockspresso.annotation.RealObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Placeholder
 */
public class TypedPreferencesPrimitivesTest {

  private static final PrefNamespace PREF_NAMESPACE = PrefNamespace.ROOT.extend("testNamespace").extend("subNamespace");

  private static final PrefKey<Boolean> BOOL_PREF = PREF_NAMESPACE.key(Boolean.class)
      .named("testBool")
      .buildWithDefault(true);
  private static final NullablePrefKey<Boolean> BOOL_NULL_PREF = PREF_NAMESPACE.key(Boolean.class)
      .named("testNullBool")
      .buildNullable();
  private static final PrefKey<Float> FLOAT_PREF = PREF_NAMESPACE.key(Float.class)
      .named("testFloat")
      .buildWithDefault(1.2f);
  private static final NullablePrefKey<Float> FLOAT_NULL_PREF = PREF_NAMESPACE.key(Float.class)
      .named("testNullFloat")
      .buildNullable();
  private static final PrefKey<Integer> INT_PREF = PREF_NAMESPACE.key(Integer.class)
      .named("testInt")
      .buildWithDefault(3);
  private static final NullablePrefKey<Integer> INT_NULL_PREF = PREF_NAMESPACE.key(Integer.class)
      .named("testNullInt")
      .buildNullable();
  private static final PrefKey<Long> LONG_PREF = PREF_NAMESPACE.key(Long.class)
      .named("testLong")
      .buildWithDefault(123L);
  private static final NullablePrefKey<Long> LONG_NULL_PREF = PREF_NAMESPACE.key(Long.class)
      .named("testNullLong")
      .buildNullable();
  private static final PrefKey<String> STRING_PREF = PREF_NAMESPACE.key(String.class)
      .named("testString")
      .buildWithDefault("default");
  private static final NullablePrefKey<String> STRING_NULL_PREF = PREF_NAMESPACE.key(String.class)
      .named("testNullString")
      .buildNullable();
  private static final PrefKey<Double> DOUBLE_PREF = PREF_NAMESPACE.key(Double.class)
      .named("testDouble")
      .buildWithDefault(1.2d);
  private static final NullablePrefKey<Double> DOUBLE_NULL_PREF = PREF_NAMESPACE.key(Double.class)
      .named("testNullDouble")
      .buildNullable();

  @Rule public final Mockspresso.Rule mockspresso = Rules.mockspresso();

  @Mock SharedPreferences mSharedPreferences;
  /*Mock*/ SharedPreferences.Editor mEditor;

  @RealObject(implementation = TypedPreferencesImpl.class) TypedPreferences mTypedPreferences;

  @Before
  public void setup() {
    mEditor = mock(SharedPreferences.Editor.class, Answers.builderAnswer());
    when(mSharedPreferences.edit()).thenReturn(mEditor);
  }

  @Test
  public void testBooleanDoesntExist() {
    String keyName = BOOL_PREF.getKeyName().toString();

    boolean result = mTypedPreferences.get(BOOL_PREF);

    verify(mSharedPreferences).contains(keyName);
    assertThat(result).isTrue();
  }

  @Test
  public void testBooleanDoesExist() {
    String keyName = BOOL_PREF.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getBoolean(eq(keyName), anyBoolean())).thenReturn(false);

    boolean result = mTypedPreferences.get(BOOL_PREF);

    verify(mSharedPreferences).contains(keyName);
    verify(mSharedPreferences).getBoolean(eq(keyName), anyBoolean());
    assertThat(result).isFalse();
  }

  @Test
  public void testSetBoolean() {
    String keyName = BOOL_PREF.getKeyName().toString();

    mTypedPreferences.edit()
        .put(BOOL_PREF, false)
        .commit();

    verify(mSharedPreferences).edit();
    verify(mEditor).putBoolean(keyName, false);
    verify(mEditor).commit();
  }

  @Test(expected = NullPointerException.class)
  public void testSetBooleanException() {
    mTypedPreferences.edit()
        .put(BOOL_PREF, null);
  }

  @Test
  public void testNullBooleanDoesntExist() {
    String keyName = BOOL_NULL_PREF.getKeyName().toString();

    Boolean result = mTypedPreferences.get(BOOL_NULL_PREF);

    verify(mSharedPreferences).contains(keyName);
    assertThat(result).isNull();
  }

  @Test
  public void testNullBooleanDoesExist() {
    String keyName = BOOL_NULL_PREF.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getBoolean(eq(keyName), anyBoolean())).thenReturn(true);

    Boolean result = mTypedPreferences.get(BOOL_NULL_PREF);

    verify(mSharedPreferences).contains(keyName);
    verify(mSharedPreferences).getBoolean(eq(keyName), anyBoolean());
    assertThat(result)
        .isNotNull()
        .isTrue();
  }

  @Test
  public void testSetNullBoolean() {
    String keyName = BOOL_NULL_PREF.getKeyName().toString();

    mTypedPreferences.edit()
        .put(BOOL_NULL_PREF, false)
        .apply();

    verify(mSharedPreferences).edit();
    verify(mEditor).putBoolean(keyName, false);
    verify(mEditor).apply();
  }

  @Test
  public void testSetNullBooleanNull() {
    String keyName = BOOL_NULL_PREF.getKeyName().toString();

    mTypedPreferences.edit()
        .put(BOOL_NULL_PREF, null)
        .commit();

    verify(mSharedPreferences).edit();
    verify(mEditor).remove(keyName);
    verify(mEditor).commit();
  }


  @Test
  public void testFloatDoesntExist() {
    String keyName = FLOAT_PREF.getKeyName().toString();

    float result = mTypedPreferences.get(FLOAT_PREF);

    verify(mSharedPreferences).contains(keyName);
    assertThat(result).isEqualTo(1.2f);
  }

  @Test
  public void testFloatDoesExist() {
    String keyName = FLOAT_PREF.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getFloat(eq(keyName), anyFloat())).thenReturn(3.5f);

    float result = mTypedPreferences.get(FLOAT_PREF);

    verify(mSharedPreferences).contains(keyName);
    verify(mSharedPreferences).getFloat(eq(keyName), anyFloat());
    assertThat(result).isEqualTo(3.5f);
  }

  @Test
  public void testSetFloat() {
    String keyName = FLOAT_PREF.getKeyName().toString();

    mTypedPreferences.edit()
        .put(FLOAT_PREF, 7.6f)
        .commit();

    verify(mSharedPreferences).edit();
    verify(mEditor).putFloat(keyName, 7.6f);
    verify(mEditor).commit();
  }

  @Test(expected = NullPointerException.class)
  public void testSetFloatException() {
    mTypedPreferences.edit()
        .put(FLOAT_PREF, null);
  }

  @Test
  public void testNullFloatDoesntExist() {
    String keyName = FLOAT_NULL_PREF.getKeyName().toString();

    Float result = mTypedPreferences.get(FLOAT_NULL_PREF);

    verify(mSharedPreferences).contains(keyName);
    assertThat(result).isNull();
  }

  @Test
  public void testNullFloatDoesExist() {
    String keyName = FLOAT_NULL_PREF.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getFloat(eq(keyName), anyFloat())).thenReturn(17.3f);

    Float result = mTypedPreferences.get(FLOAT_NULL_PREF);

    verify(mSharedPreferences).contains(keyName);
    verify(mSharedPreferences).getFloat(eq(keyName), anyFloat());
    assertThat(result)
        .isNotNull()
        .isEqualTo(17.3f);
  }

  @Test
  public void testSetNullFloat() {
    String keyName = FLOAT_NULL_PREF.getKeyName().toString();

    mTypedPreferences.edit()
        .put(FLOAT_NULL_PREF, 13.3f)
        .apply();

    verify(mSharedPreferences).edit();
    verify(mEditor).putFloat(keyName, 13.3f);
    verify(mEditor).apply();
  }

  @Test
  public void testSetNullFloatNull() {
    String keyName = FLOAT_NULL_PREF.getKeyName().toString();

    mTypedPreferences.edit()
        .put(FLOAT_NULL_PREF, null)
        .commit();

    verify(mSharedPreferences).edit();
    verify(mEditor).remove(keyName);
    verify(mEditor).commit();
  }

  @Test
  public void testIntegerDoesntExist() {
    String keyName = INT_PREF.getKeyName().toString();

    int result = mTypedPreferences.get(INT_PREF);

    verify(mSharedPreferences).contains(keyName);
    assertThat(result).isEqualTo(3);
  }

  @Test
  public void testIntegerDoesExist() {
    String keyName = INT_PREF.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getInt(eq(keyName), anyInt())).thenReturn(7);

    int result = mTypedPreferences.get(INT_PREF);

    verify(mSharedPreferences).contains(keyName);
    verify(mSharedPreferences).getInt(eq(keyName), anyInt());
    assertThat(result).isEqualTo(7);
  }

  @Test
  public void testSetInteger() {
    String keyName = INT_PREF.getKeyName().toString();

    mTypedPreferences.edit()
        .put(INT_PREF, 12)
        .commit();

    verify(mSharedPreferences).edit();
    verify(mEditor).putInt(keyName, 12);
    verify(mEditor).commit();
  }

  @Test(expected = NullPointerException.class)
  public void testSetIntegerException() {
    mTypedPreferences.edit()
        .put(INT_PREF, null);
  }

  @Test
  public void testNullIntegerDoesntExist() {
    String keyName = INT_NULL_PREF.getKeyName().toString();

    Integer result = mTypedPreferences.get(INT_NULL_PREF);

    verify(mSharedPreferences).contains(keyName);
    assertThat(result).isNull();
  }

  @Test
  public void testNullIntegerDoesExist() {
    String keyName = INT_NULL_PREF.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getInt(eq(keyName), anyInt())).thenReturn(18);

    Integer result = mTypedPreferences.get(INT_NULL_PREF);

    verify(mSharedPreferences).contains(keyName);
    verify(mSharedPreferences).getInt(eq(keyName), anyInt());
    assertThat(result)
        .isNotNull()
        .isEqualTo(18);
  }

  @Test
  public void testSetNullInteger() {
    String keyName = INT_NULL_PREF.getKeyName().toString();

    mTypedPreferences.edit()
        .put(INT_NULL_PREF, 22)
        .apply();

    verify(mSharedPreferences).edit();
    verify(mEditor).putInt(keyName, 22);
    verify(mEditor).apply();
  }

  @Test
  public void testSetNullIntegerNull() {
    String keyName = INT_NULL_PREF.getKeyName().toString();

    mTypedPreferences.edit()
        .put(INT_NULL_PREF, null)
        .commit();

    verify(mSharedPreferences).edit();
    verify(mEditor).remove(keyName);
    verify(mEditor).commit();
  }


  @Test
  public void testLongDoesntExist() {
    String keyName = LONG_PREF.getKeyName().toString();

    long result = mTypedPreferences.get(LONG_PREF);

    verify(mSharedPreferences).contains(keyName);
    assertThat(result).isEqualTo(123L);
  }

  @Test
  public void testLongDoesExist() {
    String keyName = LONG_PREF.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getLong(eq(keyName), anyLong())).thenReturn(15L);

    long result = mTypedPreferences.get(LONG_PREF);

    verify(mSharedPreferences).contains(keyName);
    verify(mSharedPreferences).getLong(eq(keyName), anyLong());
    assertThat(result).isEqualTo(15L);
  }

  @Test
  public void testSetLong() {
    String keyName = LONG_PREF.getKeyName().toString();

    mTypedPreferences.edit()
        .put(LONG_PREF, 145L)
        .commit();

    verify(mSharedPreferences).edit();
    verify(mEditor).putLong(keyName, 145L);
    verify(mEditor).commit();
  }

  @Test(expected = NullPointerException.class)
  public void testSetLongException() {
    mTypedPreferences.edit()
        .put(LONG_PREF, null);
  }

  @Test
  public void testNullLongDoesntExist() {
    String keyName = LONG_NULL_PREF.getKeyName().toString();

    Long result = mTypedPreferences.get(LONG_NULL_PREF);

    verify(mSharedPreferences).contains(keyName);
    assertThat(result).isNull();
  }

  @Test
  public void testNullLongDoesExist() {
    String keyName = LONG_NULL_PREF.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getLong(eq(keyName), anyLong())).thenReturn(173L);

    Long result = mTypedPreferences.get(LONG_NULL_PREF);

    verify(mSharedPreferences).contains(keyName);
    verify(mSharedPreferences).getLong(eq(keyName), anyLong());
    assertThat(result)
        .isNotNull()
        .isEqualTo(173L);
  }

  @Test
  public void testSetNullLong() {
    String keyName = LONG_NULL_PREF.getKeyName().toString();

    mTypedPreferences.edit()
        .put(LONG_NULL_PREF, 133L)
        .apply();

    verify(mSharedPreferences).edit();
    verify(mEditor).putLong(keyName, 133L);
    verify(mEditor).apply();
  }

  @Test
  public void testSetNullLongNull() {
    String keyName = LONG_NULL_PREF.getKeyName().toString();

    mTypedPreferences.edit()
        .put(LONG_NULL_PREF, null)
        .commit();

    verify(mSharedPreferences).edit();
    verify(mEditor).remove(keyName);
    verify(mEditor).commit();
  }


  @Test
  public void testStringDoesntExist() {
    String keyName = STRING_PREF.getKeyName().toString();

    String result = mTypedPreferences.get(STRING_PREF);

    verify(mSharedPreferences).contains(keyName);
    assertThat(result).isEqualTo("default");
  }

  @Test
  public void testStringDoesExist() {
    String keyName = STRING_PREF.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getString(eq(keyName), nullable(String.class))).thenReturn("sup");

    String result = mTypedPreferences.get(STRING_PREF);

    verify(mSharedPreferences).contains(keyName);
    verify(mSharedPreferences).getString(eq(keyName), nullable(String.class));
    assertThat(result).isEqualTo("sup");
  }

  @Test
  public void testSetString() {
    String keyName = STRING_PREF.getKeyName().toString();

    mTypedPreferences.edit()
        .put(STRING_PREF, "howdy")
        .commit();

    verify(mSharedPreferences).edit();
    verify(mEditor).putString(keyName, "howdy");
    verify(mEditor).commit();
  }

  @Test(expected = NullPointerException.class)
  public void testSetStringException() {
    mTypedPreferences.edit()
        .put(STRING_PREF, null);
  }

  @Test
  public void testNullStringDoesntExist() {
    String keyName = STRING_NULL_PREF.getKeyName().toString();

    String result = mTypedPreferences.get(STRING_NULL_PREF);

    verify(mSharedPreferences).contains(keyName);
    assertThat(result).isNull();
  }

  @Test
  public void testNullStringDoesExist() {
    String keyName = STRING_NULL_PREF.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getString(eq(keyName), nullable(String.class))).thenReturn("yooo");

    String result = mTypedPreferences.get(STRING_NULL_PREF);

    verify(mSharedPreferences).contains(keyName);
    verify(mSharedPreferences).getString(eq(keyName), nullable(String.class));
    assertThat(result)
        .isNotNull()
        .isEqualTo("yooo");
  }

  @Test
  public void testSetNullString() {
    String keyName = STRING_NULL_PREF.getKeyName().toString();

    mTypedPreferences.edit()
        .put(STRING_NULL_PREF, "hey now")
        .apply();

    verify(mSharedPreferences).edit();
    verify(mEditor).putString(keyName, "hey now");
    verify(mEditor).apply();
  }

  @Test
  public void testSetNullStringNull() {
    String keyName = STRING_NULL_PREF.getKeyName().toString();

    mTypedPreferences.edit()
        .put(STRING_NULL_PREF, null)
        .commit();

    verify(mSharedPreferences).edit();
    verify(mEditor).remove(keyName);
    verify(mEditor).commit();
  }

  @Test
  public void testDoubleDoesntExist() {
    String keyName = DOUBLE_PREF.getKeyName().toString();

    double result = mTypedPreferences.get(DOUBLE_PREF);

    verify(mSharedPreferences).contains(keyName);
    assertThat(result).isEqualTo(1.2d);
  }

  @Test
  public void testDoubleDoesExist() {
    String keyName = DOUBLE_PREF.getKeyName().toString();
    long doubleBits = Double.doubleToRawLongBits(3.5d);
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getLong(eq(keyName), anyLong())).thenReturn(doubleBits);

    double result = mTypedPreferences.get(DOUBLE_PREF);

    verify(mSharedPreferences).contains(keyName);
    verify(mSharedPreferences).getLong(eq(keyName), anyLong());
    assertThat(result).isEqualTo(3.5d);
  }

  @Test
  public void testSetDouble() {
    long doubleBits = Double.doubleToRawLongBits(7.6d);
    String keyName = DOUBLE_PREF.getKeyName().toString();

    mTypedPreferences.edit()
        .put(DOUBLE_PREF, 7.6d)
        .commit();

    verify(mSharedPreferences).edit();
    verify(mEditor).putLong(keyName, doubleBits);
    verify(mEditor).commit();
  }

  @Test(expected = NullPointerException.class)
  public void testSetDoubleException() {
    mTypedPreferences.edit()
        .put(DOUBLE_PREF, null);
  }

  @Test
  public void testNullDoubleDoesntExist() {
    String keyName = DOUBLE_NULL_PREF.getKeyName().toString();

    Double result = mTypedPreferences.get(DOUBLE_NULL_PREF);

    verify(mSharedPreferences).contains(keyName);
    assertThat(result).isNull();
  }

  @Test
  public void testNullDoubleDoesExist() {
    String keyName = DOUBLE_NULL_PREF.getKeyName().toString();
    long doubleBits = Double.doubleToRawLongBits(17.3d);
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getLong(eq(keyName), anyLong())).thenReturn(doubleBits);

    Double result = mTypedPreferences.get(DOUBLE_NULL_PREF);

    verify(mSharedPreferences).contains(keyName);
    verify(mSharedPreferences).getLong(eq(keyName), anyLong());
    assertThat(result)
        .isNotNull()
        .isEqualTo(17.3d);
  }

  @Test
  public void testSetNullDouble() {
    long doubleBits = Double.doubleToRawLongBits(13.3d);
    String keyName = DOUBLE_NULL_PREF.getKeyName().toString();

    mTypedPreferences.edit()
        .put(DOUBLE_NULL_PREF, 13.3d)
        .apply();

    verify(mSharedPreferences).edit();
    verify(mEditor).putLong(keyName, doubleBits);
    verify(mEditor).apply();
  }

  @Test
  public void testSetNullDoubleNull() {
    String keyName = DOUBLE_NULL_PREF.getKeyName().toString();

    mTypedPreferences.edit()
        .put(DOUBLE_NULL_PREF, null)
        .commit();

    verify(mSharedPreferences).edit();
    verify(mEditor).remove(keyName);
    verify(mEditor).commit();
  }
}
