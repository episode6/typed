package com.episode6.hackit.typed.preferences;

import android.content.SharedPreferences;
import android.util.LruCache;
import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.mockspresso.annotation.RealObject;
import com.episode6.hackit.typed.core.TypedKey;
import com.episode6.hackit.typed.testing.Answers;
import com.episode6.hackit.typed.testing.Rules;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Tests {@link TypedPrefsImpl} usage with Primitives
 */
public class TypedPrefsPrimitivesTest {

  private static final PrefNamespace PREF_NAMESPACE = PrefNamespace.ROOT.extend("testNamespace").extend("subNamespace");

  private static final PrefKey<Boolean> BOOL_PREF = PREF_NAMESPACE.key(Boolean.class)
      .named("testBool")
      .buildWithDefault(true);
  private static final OptPrefKey<Boolean> BOOL_NULL_PREF = PREF_NAMESPACE.key(Boolean.class)
      .named("testNullBool")
      .buildNullable();
  private static final PrefKey<Float> FLOAT_PREF = PREF_NAMESPACE.key(Float.class)
      .named("testFloat")
      .buildWithDefault(1.2f);
  private static final OptPrefKey<Float> FLOAT_NULL_PREF = PREF_NAMESPACE.key(Float.class)
      .named("testNullFloat")
      .buildNullable();
  private static final PrefKey<Integer> INT_PREF = PREF_NAMESPACE.key(Integer.class)
      .named("testInt")
      .buildWithDefault(3);
  private static final OptPrefKey<Integer> INT_NULL_PREF = PREF_NAMESPACE.key(Integer.class)
      .named("testNullInt")
      .buildNullable();
  private static final PrefKey<Long> LONG_PREF = PREF_NAMESPACE.key(Long.class)
      .named("testLong")
      .buildWithDefault(123L);
  private static final OptPrefKey<Long> LONG_NULL_PREF = PREF_NAMESPACE.key(Long.class)
      .named("testNullLong")
      .buildNullable();
  private static final PrefKey<String> STRING_PREF = PREF_NAMESPACE.key(String.class)
      .named("testString")
      .buildWithDefault("default");
  private static final OptPrefKey<String> STRING_NULL_PREF = PREF_NAMESPACE.key(String.class)
      .named("testNullString")
      .buildNullable();
  private static final PrefKey<Double> DOUBLE_PREF = PREF_NAMESPACE.key(Double.class)
      .named("testDouble")
      .buildWithDefault(1.2d);
  private static final OptPrefKey<Double> DOUBLE_NULL_PREF = PREF_NAMESPACE.key(Double.class)
      .named("testNullDouble")
      .buildNullable();

  @Rule public final Mockspresso.Rule mockspresso = Rules.mockspresso();

  @Mock SharedPreferences mSharedPreferences;
  /*Mock*/ SharedPreferences.Editor mEditor;
  @Mock LruCache<TypedKey, Object> mCache;

  @RealObject(implementation = TypedPrefsImpl.class)
  TypedPrefs mTypedPrefs;

  @Before
  public void setup() {
    mEditor = mock(SharedPreferences.Editor.class, Answers.builderAnswer());
    when(mSharedPreferences.edit()).thenReturn(mEditor);
  }

  @Test
  public void testBooleanDoesntExist() {
    boolean result = mTypedPrefs.get(BOOL_PREF);

    verifyPrefDidntExist(BOOL_PREF);
    assertThat(result).isTrue();
  }

  @Test
  public void testBooleanDoesExist() {
    setupBooleanExists(BOOL_PREF, false);

    boolean result = mTypedPrefs.get(BOOL_PREF);

    verifyBooleanExisted(BOOL_PREF);
    assertThat(result).isFalse();
  }

  @Test
  public void testSetBoolean() {
    mTypedPrefs.edit()
        .put(BOOL_PREF, false)
        .commit();

    verifyBooleanWasSet(BOOL_PREF, false);
  }

  @Test
  public void testRemoveBoolean() {
    mTypedPrefs.edit()
        .remove(BOOL_PREF)
        .commit();

    verifyPrefWasRemoved(BOOL_PREF);
  }

  @Test(expected = NullPointerException.class)
  public void testSetBooleanException() {
    mTypedPrefs.edit()
        .put(BOOL_PREF, null);
  }

  @Test
  public void testNullBooleanDoesntExist() {
    Boolean result = mTypedPrefs.get(BOOL_NULL_PREF);

    verifyPrefDidntExist(BOOL_NULL_PREF);
    assertThat(result).isNull();
  }

  @Test
  public void testNullBooleanDoesExist() {
    setupBooleanExists(BOOL_NULL_PREF, true);

    Boolean result = mTypedPrefs.get(BOOL_NULL_PREF);

    verifyBooleanExisted(BOOL_NULL_PREF);
    assertThat(result)
        .isNotNull()
        .isTrue();
  }

  @Test
  public void testSetNullBoolean() {
    mTypedPrefs.edit()
        .put(BOOL_NULL_PREF, false)
        .commit();

    verifyBooleanWasSet(BOOL_NULL_PREF, false);
  }

  @Test
  public void testRemoveNullBoolean() {
    mTypedPrefs.edit()
        .remove(BOOL_NULL_PREF)
        .commit();

    verifyPrefWasRemoved(BOOL_NULL_PREF);
  }

  @Test
  public void testSetNullBooleanNull() {
    mTypedPrefs.edit()
        .put(BOOL_NULL_PREF, null)
        .commit();

    verifyPrefWasRemoved(BOOL_NULL_PREF);
  }


  @Test
  public void testFloatDoesntExist() {
    float result = mTypedPrefs.get(FLOAT_PREF);

    verifyPrefDidntExist(FLOAT_PREF);
    assertThat(result).isEqualTo(1.2f);
  }

  @Test
  public void testFloatDoesExist() {
    setupFloatExists(FLOAT_PREF, 3.5f);

    float result = mTypedPrefs.get(FLOAT_PREF);

    verifyFloatExisted(FLOAT_PREF);
    assertThat(result).isEqualTo(3.5f);
  }

  @Test
  public void testSetFloat() {
    mTypedPrefs.edit()
        .put(FLOAT_PREF, 7.6f)
        .commit();

    verifyFloatWasSet(FLOAT_PREF, 7.6f);
  }

  @Test
  public void testRemoveFloat() {
    mTypedPrefs.edit()
        .remove(FLOAT_PREF)
        .commit();

    verifyPrefWasRemoved(FLOAT_PREF);
  }

  @Test(expected = NullPointerException.class)
  public void testSetFloatException() {
    mTypedPrefs.edit()
        .put(FLOAT_PREF, null);
  }

  @Test
  public void testNullFloatDoesntExist() {
    Float result = mTypedPrefs.get(FLOAT_NULL_PREF);

    verifyPrefDidntExist(FLOAT_NULL_PREF);
    assertThat(result).isNull();
  }

  @Test
  public void testNullFloatDoesExist() {
    setupFloatExists(FLOAT_NULL_PREF, 17.3f);

    Float result = mTypedPrefs.get(FLOAT_NULL_PREF);

    verifyFloatExisted(FLOAT_NULL_PREF);
    assertThat(result)
        .isNotNull()
        .isEqualTo(17.3f);
  }

  @Test
  public void testSetNullFloat() {
    mTypedPrefs.edit()
        .put(FLOAT_NULL_PREF, 13.3f)
        .commit();

    verifyFloatWasSet(FLOAT_NULL_PREF, 13.3f);
  }

  @Test
  public void testRemoveNullFloat() {
    mTypedPrefs.edit()
        .remove(FLOAT_NULL_PREF)
        .commit();

    verifyPrefWasRemoved(FLOAT_NULL_PREF);
  }

  @Test
  public void testSetNullFloatNull() {
    mTypedPrefs.edit()
        .put(FLOAT_NULL_PREF, null)
        .commit();

    verifyPrefWasRemoved(FLOAT_NULL_PREF);
  }

  @Test
  public void testIntegerDoesntExist() {
    int result = mTypedPrefs.get(INT_PREF);

    verifyPrefDidntExist(INT_PREF);
    assertThat(result).isEqualTo(3);
  }

  @Test
  public void testIntegerDoesExist() {
    setupIntegerExists(INT_PREF, 7);

    int result = mTypedPrefs.get(INT_PREF);

    verifyIntegerExisted(INT_PREF);
    assertThat(result).isEqualTo(7);
  }

  @Test
  public void testSetInteger() {
    mTypedPrefs.edit()
        .put(INT_PREF, 12)
        .commit();

    verifyIntegerWasSet(INT_PREF, 12);
  }

  @Test
  public void testRemoveInteger() {
    mTypedPrefs.edit()
        .remove(INT_PREF)
        .commit();

    verifyPrefWasRemoved(INT_PREF);
  }

  @Test(expected = NullPointerException.class)
  public void testSetIntegerException() {
    mTypedPrefs.edit()
        .put(INT_PREF, null);
  }

  @Test
  public void testNullIntegerDoesntExist() {
    Integer result = mTypedPrefs.get(INT_NULL_PREF);

    verifyPrefDidntExist(INT_NULL_PREF);
    assertThat(result).isNull();
  }

  @Test
  public void testNullIntegerDoesExist() {
    setupIntegerExists(INT_NULL_PREF, 18);

    Integer result = mTypedPrefs.get(INT_NULL_PREF);

    verifyIntegerExisted(INT_NULL_PREF);
    assertThat(result)
        .isNotNull()
        .isEqualTo(18);
  }

  @Test
  public void testSetNullInteger() {
    mTypedPrefs.edit()
        .put(INT_NULL_PREF, 22)
        .commit();

    verifyIntegerWasSet(INT_NULL_PREF, 22);
  }

  @Test
  public void testRemoveNullInteger() {
    mTypedPrefs.edit()
        .remove(INT_NULL_PREF)
        .commit();

    verifyPrefWasRemoved(INT_NULL_PREF);
  }

  @Test
  public void testSetNullIntegerNull() {
    mTypedPrefs.edit()
        .put(INT_NULL_PREF, null)
        .commit();

    verifyPrefWasRemoved(INT_NULL_PREF);
  }


  @Test
  public void testLongDoesntExist() {
    long result = mTypedPrefs.get(LONG_PREF);

    verifyPrefDidntExist(LONG_PREF);
    assertThat(result).isEqualTo(123L);
  }

  @Test
  public void testLongDoesExist() {
    setupLongExists(LONG_PREF, 15L);

    long result = mTypedPrefs.get(LONG_PREF);

    verifyLongExisted(LONG_PREF);
    assertThat(result).isEqualTo(15L);
  }

  @Test
  public void testSetLong() {
    mTypedPrefs.edit()
        .put(LONG_PREF, 145L)
        .commit();

    verifyLongWasSet(LONG_PREF, 145L);
  }

  @Test
  public void testRemoveLong() {
    mTypedPrefs.edit()
        .remove(LONG_PREF)
        .commit();

    verifyPrefWasRemoved(LONG_PREF);
  }

  @Test(expected = NullPointerException.class)
  public void testSetLongException() {
    mTypedPrefs.edit()
        .put(LONG_PREF, null);
  }

  @Test
  public void testNullLongDoesntExist() {
    Long result = mTypedPrefs.get(LONG_NULL_PREF);

    verifyPrefDidntExist(LONG_NULL_PREF);
    assertThat(result).isNull();
  }

  @Test
  public void testNullLongDoesExist() {
    setupLongExists(LONG_NULL_PREF, 173L);

    Long result = mTypedPrefs.get(LONG_NULL_PREF);

    verifyLongExisted(LONG_NULL_PREF);
    assertThat(result)
        .isNotNull()
        .isEqualTo(173L);
  }

  @Test
  public void testSetNullLong() {
    mTypedPrefs.edit()
        .put(LONG_NULL_PREF, 133L)
        .commit();

    verifyLongWasSet(LONG_NULL_PREF, 133L);
  }

  @Test
  public void testRemoveNullLong() {
    mTypedPrefs.edit()
        .remove(LONG_NULL_PREF)
        .commit();

    verifyPrefWasRemoved(LONG_NULL_PREF);
  }

  @Test
  public void testSetNullLongNull() {
    mTypedPrefs.edit()
        .put(LONG_NULL_PREF, null)
        .commit();

    verifyPrefWasRemoved(LONG_NULL_PREF);
  }

  @Test
  public void testStringDoesntExist() {
    String result = mTypedPrefs.get(STRING_PREF);

    verifyPrefDidntExist(STRING_PREF);
    assertThat(result).isEqualTo("default");
  }

  @Test
  public void testStringDoesExist() {
    setupStringExists(STRING_PREF, "sup");

    String result = mTypedPrefs.get(STRING_PREF);

    verifyStringExisted(STRING_PREF);
    assertThat(result).isEqualTo("sup");
  }

  @Test
  public void testSetString() {
    mTypedPrefs.edit()
        .put(STRING_PREF, "howdy")
        .commit();

    verifyStringWasSet(STRING_PREF, "howdy");
  }

  @Test
  public void testRemoveString() {
    mTypedPrefs.edit()
        .remove(STRING_PREF)
        .commit();

    verifyPrefWasRemoved(STRING_PREF);
  }

  @Test(expected = NullPointerException.class)
  public void testSetStringException() {
    mTypedPrefs.edit()
        .put(STRING_PREF, null);
  }

  @Test
  public void testNullStringDoesntExist() {
    String result = mTypedPrefs.get(STRING_NULL_PREF);

    verifyPrefDidntExist(STRING_NULL_PREF);
    assertThat(result).isNull();
  }

  @Test
  public void testNullStringDoesExist() {
    setupStringExists(STRING_NULL_PREF, "yooo");

    String result = mTypedPrefs.get(STRING_NULL_PREF);

    verifyStringExisted(STRING_NULL_PREF);
    assertThat(result)
        .isNotNull()
        .isEqualTo("yooo");
  }

  @Test
  public void testSetNullString() {
    mTypedPrefs.edit()
        .put(STRING_NULL_PREF, "hey now")
        .commit();

    verifyStringWasSet(STRING_NULL_PREF, "hey now");
  }

  @Test
  public void testRemoveNullString() {
    mTypedPrefs.edit()
        .remove(STRING_NULL_PREF)
        .commit();

    verifyPrefWasRemoved(STRING_NULL_PREF);
  }

  @Test
  public void testSetNullStringNull() {
    mTypedPrefs.edit()
        .put(STRING_NULL_PREF, null)
        .commit();

    verifyPrefWasRemoved(STRING_NULL_PREF);
  }

  @Test
  public void testDoubleDoesntExist() {
    double result = mTypedPrefs.get(DOUBLE_PREF);

    verifyPrefDidntExist(DOUBLE_PREF);
    assertThat(result).isEqualTo(1.2d);
  }

  @Test
  public void testDoubleDoesExist() {
    setupDoubleExists(DOUBLE_PREF, 3.5d);

    double result = mTypedPrefs.get(DOUBLE_PREF);

    verifyDoubleExisted(DOUBLE_PREF);
    assertThat(result).isEqualTo(3.5d);
  }

  @Test
  public void testSetDouble() {
    mTypedPrefs.edit()
        .put(DOUBLE_PREF, 7.6d)
        .commit();

    verifyDoubleWasSet(DOUBLE_PREF, 7.6d);
  }

  @Test
  public void testDoubleRemoved() {
    mTypedPrefs.edit()
        .remove(DOUBLE_PREF)
        .commit();

    verifyPrefWasRemoved(DOUBLE_PREF);
  }

  @Test(expected = NullPointerException.class)
  public void testSetDoubleException() {
    mTypedPrefs.edit()
        .put(DOUBLE_PREF, null);
  }

  @Test
  public void testNullDoubleDoesntExist() {
    Double result = mTypedPrefs.get(DOUBLE_NULL_PREF);

    verifyPrefDidntExist(DOUBLE_NULL_PREF);
    assertThat(result).isNull();
  }

  @Test
  public void testNullDoubleDoesExist() {
    setupDoubleExists(DOUBLE_NULL_PREF, 17.3d);

    Double result = mTypedPrefs.get(DOUBLE_NULL_PREF);

    verifyDoubleExisted(DOUBLE_NULL_PREF);
    assertThat(result)
        .isNotNull()
        .isEqualTo(17.3d);
  }

  @Test
  public void testSetNullDouble() {
    mTypedPrefs.edit()
        .put(DOUBLE_NULL_PREF, 13.3d)
        .commit();

    verifyDoubleWasSet(DOUBLE_NULL_PREF, 13.3d);
  }

  @Test
  public void testNullDoubleRemoved() {
    mTypedPrefs.edit()
        .remove(DOUBLE_NULL_PREF)
        .commit();

    verifyPrefWasRemoved(DOUBLE_NULL_PREF);
  }

  @Test
  public void testSetNullDoubleNull() {
    mTypedPrefs.edit()
        .put(DOUBLE_NULL_PREF, null)
        .commit();

    verifyPrefWasRemoved(DOUBLE_NULL_PREF);
  }

  private void verifyPrefDidntExist(TypedKey key) {
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor);
    inOrder.verify(mCache).get(key);
    inOrder.verify(mSharedPreferences).contains(key.getKeyName().toString());
    inOrder.verifyNoMoreInteractions();
  }

  private void verifyPrefWasRemoved(TypedKey key) {
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mCache).remove(key);
    inOrder.verify(mEditor).remove(key.getKeyName().toString());
    inOrder.verify(mEditor).commit();
    inOrder.verifyNoMoreInteractions();
  }

  private void setupBooleanExists(TypedKey<Boolean> key, boolean expectedValue) {
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getBoolean(eq(keyName), anyBoolean())).thenReturn(expectedValue);
  }

  private void verifyBooleanExisted(TypedKey<Boolean> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor);
    inOrder.verify(mCache).get(key);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getBoolean(eq(keyName), anyBoolean());
    inOrder.verify(mCache).put(eq(key), any(Boolean.class));
    inOrder.verifyNoMoreInteractions();
  }

  private void verifyBooleanWasSet(TypedKey<Boolean> key, boolean expectedValue) {
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mCache).put(key, expectedValue);
    inOrder.verify(mEditor).putBoolean(key.getKeyName().toString(), expectedValue);
    inOrder.verify(mEditor).commit();
    inOrder.verifyNoMoreInteractions();
  }

  private void setupFloatExists(TypedKey<Float> key, float expectedValue) {
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getFloat(eq(keyName), anyFloat())).thenReturn(expectedValue);
  }

  private void verifyFloatExisted(TypedKey<Float> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor);
    inOrder.verify(mCache).get(key);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getFloat(eq(keyName), anyFloat());
    inOrder.verify(mCache).put(eq(key), any(Float.class));
    inOrder.verifyNoMoreInteractions();
  }

  private void verifyFloatWasSet(TypedKey<Float> key, float expectedValue) {
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mCache).put(key, expectedValue);
    inOrder.verify(mEditor).putFloat(key.getKeyName().toString(), expectedValue);
    inOrder.verify(mEditor).commit();
    inOrder.verifyNoMoreInteractions();
  }

  private void setupIntegerExists(TypedKey<Integer> key, int expectedValue) {
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getInt(eq(keyName), anyInt())).thenReturn(expectedValue);
  }

  private void verifyIntegerExisted(TypedKey<Integer> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor);
    inOrder.verify(mCache).get(key);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getInt(eq(keyName), anyInt());
    inOrder.verify(mCache).put(eq(key), any(Integer.class));
    inOrder.verifyNoMoreInteractions();
  }

  private void verifyIntegerWasSet(TypedKey<Integer> key, int expectedValue) {
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mCache).put(key, expectedValue);
    inOrder.verify(mEditor).putInt(key.getKeyName().toString(), expectedValue);
    inOrder.verify(mEditor).commit();
    inOrder.verifyNoMoreInteractions();
  }

  private void setupLongExists(TypedKey<Long> key, long expectedValue) {
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getLong(eq(keyName), anyLong())).thenReturn(expectedValue);
  }

  private void verifyLongExisted(TypedKey<Long> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor);
    inOrder.verify(mCache).get(key);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getLong(eq(keyName), anyLong());
    inOrder.verify(mCache).put(eq(key), any(Long.class));
    inOrder.verifyNoMoreInteractions();
  }

  private void verifyLongWasSet(TypedKey<Long> key, long expectedValue) {
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mCache).put(key, expectedValue);
    inOrder.verify(mEditor).putLong(key.getKeyName().toString(), expectedValue);
    inOrder.verify(mEditor).commit();
    inOrder.verifyNoMoreInteractions();
  }

  private void setupStringExists(TypedKey<String> key, String expectedValue) {
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getString(eq(keyName), nullable(String.class))).thenReturn(expectedValue);
  }

  private void verifyStringExisted(TypedKey<String> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor);
    inOrder.verify(mCache).get(key);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getString(eq(keyName), nullable(String.class));
    inOrder.verify(mCache).put(eq(key), any(String.class));
    inOrder.verifyNoMoreInteractions();
  }

  private void verifyStringWasSet(TypedKey<String> key, String expectedValue) {
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mCache).put(key, expectedValue);
    inOrder.verify(mEditor).putString(key.getKeyName().toString(), expectedValue);
    inOrder.verify(mEditor).commit();
    inOrder.verifyNoMoreInteractions();
  }

  private void setupDoubleExists(TypedKey<Double> key, double expectedValue) {
    long doubleBits = Double.doubleToRawLongBits(expectedValue);
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getLong(eq(keyName), anyLong())).thenReturn(doubleBits);
  }

  private void verifyDoubleExisted(TypedKey<Double> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor);
    inOrder.verify(mCache).get(key);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getLong(eq(keyName), anyLong());
    inOrder.verify(mCache).put(eq(key), any(Double.class));
    inOrder.verifyNoMoreInteractions();
  }

  private void verifyDoubleWasSet(TypedKey<Double> key, double expectedValue) {
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor);
    long doubleBits = Double.doubleToRawLongBits(expectedValue);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mCache).put(key, expectedValue);
    inOrder.verify(mEditor).putLong(key.getKeyName().toString(), doubleBits);
    inOrder.verify(mEditor).commit();
    inOrder.verifyNoMoreInteractions();
  }
}
