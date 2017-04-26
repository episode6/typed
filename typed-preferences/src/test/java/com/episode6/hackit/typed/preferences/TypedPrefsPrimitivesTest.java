package com.episode6.hackit.typed.preferences;

import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.typed.testing.Rules;
import org.junit.Rule;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

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
      .buildOptional();
  private static final PrefKey<Float> FLOAT_PREF = PREF_NAMESPACE.key(Float.class)
      .named("testFloat")
      .buildWithDefault(1.2f);
  private static final OptPrefKey<Float> FLOAT_NULL_PREF = PREF_NAMESPACE.key(Float.class)
      .named("testNullFloat")
      .buildOptional();
  private static final PrefKey<Integer> INT_PREF = PREF_NAMESPACE.key(Integer.class)
      .named("testInt")
      .buildWithDefault(3);
  private static final OptPrefKey<Integer> INT_NULL_PREF = PREF_NAMESPACE.key(Integer.class)
      .named("testNullInt")
      .buildOptional();
  private static final PrefKey<Long> LONG_PREF = PREF_NAMESPACE.key(Long.class)
      .named("testLong")
      .buildWithDefault(123L);
  private static final OptPrefKey<Long> LONG_NULL_PREF = PREF_NAMESPACE.key(Long.class)
      .named("testNullLong")
      .buildOptional();
  private static final PrefKey<String> STRING_PREF = PREF_NAMESPACE.key(String.class)
      .named("testString")
      .buildWithDefault("default");
  private static final OptPrefKey<String> STRING_NULL_PREF = PREF_NAMESPACE.key(String.class)
      .named("testNullString")
      .buildOptional();
  private static final PrefKey<Double> DOUBLE_PREF = PREF_NAMESPACE.key(Double.class)
      .named("testDouble")
      .buildWithDefault(1.2d);
  private static final OptPrefKey<Double> DOUBLE_NULL_PREF = PREF_NAMESPACE.key(Double.class)
      .named("testNullDouble")
      .buildOptional();

  private final SharedTestResources t = new SharedTestResources();
  @Rule public final Mockspresso.Rule mockspresso = Rules.mockspressoBuilder()
      .testResources(t)
      .buildRule();

  @Test
  public void testBooleanDoesntExist() {
    boolean result = t.mTypedPrefs.get(BOOL_PREF);

    t.verifyPrefDidntExist(BOOL_PREF);
    assertThat(result).isTrue();
  }

  @Test
  public void testBooleanDoesExist() {
    t.setupBooleanExists(BOOL_PREF, false);

    boolean result = t.mTypedPrefs.get(BOOL_PREF);

    t.verifyBooleanExisted(BOOL_PREF);
    assertThat(result).isFalse();
  }

  @Test
  public void testSetBoolean() {
    t.mTypedPrefs.edit()
        .put(BOOL_PREF, false)
        .commit();

    t.verifyBooleanWasSet(BOOL_PREF, false);
  }

  @Test
  public void testRemoveBoolean() {
    t.mTypedPrefs.edit()
        .remove(BOOL_PREF)
        .apply();

    t.verifyPrefWasRemoved(BOOL_PREF);
  }

  @Test(expected = NullPointerException.class)
  public void testSetBooleanException() {
    t.mTypedPrefs.edit()
        .put(BOOL_PREF, null);
  }

  @Test
  public void testNullBooleanDoesntExist() {
    Boolean result = t.mTypedPrefs.get(BOOL_NULL_PREF);

    t.verifyPrefDidntExist(BOOL_NULL_PREF);
    assertThat(result).isNull();
  }

  @Test
  public void testNullBooleanDoesExist() {
    t.setupBooleanExists(BOOL_NULL_PREF, true);

    Boolean result = t.mTypedPrefs.get(BOOL_NULL_PREF);

    t.verifyBooleanExisted(BOOL_NULL_PREF);
    assertThat(result)
        .isNotNull()
        .isTrue();
  }

  @Test
  public void testSetNullBoolean() {
    t.mTypedPrefs.edit()
        .put(BOOL_NULL_PREF, false)
        .commit();

    t.verifyBooleanWasSet(BOOL_NULL_PREF, false);
  }

  @Test
  public void testRemoveNullBoolean() {
    t.mTypedPrefs.edit()
        .remove(BOOL_NULL_PREF)
        .apply();

    t.verifyPrefWasRemoved(BOOL_NULL_PREF);
  }

  @Test
  public void testSetNullBooleanNull() {
    t.mTypedPrefs.edit()
        .put(BOOL_NULL_PREF, null)
        .apply();

    t.verifyPrefWasRemoved(BOOL_NULL_PREF);
  }


  @Test
  public void testFloatDoesntExist() {
    float result = t.mTypedPrefs.get(FLOAT_PREF);

    t.verifyPrefDidntExist(FLOAT_PREF);
    assertThat(result).isEqualTo(1.2f);
  }

  @Test
  public void testFloatDoesExist() {
    t.setupFloatExists(FLOAT_PREF, 3.5f);

    float result = t.mTypedPrefs.get(FLOAT_PREF);

    t.verifyFloatExisted(FLOAT_PREF);
    assertThat(result).isEqualTo(3.5f);
  }

  @Test
  public void testSetFloat() {
    t.mTypedPrefs.edit()
        .put(FLOAT_PREF, 7.6f)
        .commit();

    t.verifyFloatWasSet(FLOAT_PREF, 7.6f);
  }

  @Test
  public void testRemoveFloat() {
    t.mTypedPrefs.edit()
        .remove(FLOAT_PREF)
        .apply();

    t.verifyPrefWasRemoved(FLOAT_PREF);
  }

  @Test(expected = NullPointerException.class)
  public void testSetFloatException() {
    t.mTypedPrefs.edit()
        .put(FLOAT_PREF, null);
  }

  @Test
  public void testNullFloatDoesntExist() {
    Float result = t.mTypedPrefs.get(FLOAT_NULL_PREF);

    t.verifyPrefDidntExist(FLOAT_NULL_PREF);
    assertThat(result).isNull();
  }

  @Test
  public void testNullFloatDoesExist() {
    t.setupFloatExists(FLOAT_NULL_PREF, 17.3f);

    Float result = t.mTypedPrefs.get(FLOAT_NULL_PREF);

    t.verifyFloatExisted(FLOAT_NULL_PREF);
    assertThat(result)
        .isNotNull()
        .isEqualTo(17.3f);
  }

  @Test
  public void testSetNullFloat() {
    t.mTypedPrefs.edit()
        .put(FLOAT_NULL_PREF, 13.3f)
        .commit();

    t.verifyFloatWasSet(FLOAT_NULL_PREF, 13.3f);
  }

  @Test
  public void testRemoveNullFloat() {
    t.mTypedPrefs.edit()
        .remove(FLOAT_NULL_PREF)
        .apply();

    t.verifyPrefWasRemoved(FLOAT_NULL_PREF);
  }

  @Test
  public void testSetNullFloatNull() {
    t.mTypedPrefs.edit()
        .put(FLOAT_NULL_PREF, null)
        .apply();

    t.verifyPrefWasRemoved(FLOAT_NULL_PREF);
  }

  @Test
  public void testIntegerDoesntExist() {
    int result = t.mTypedPrefs.get(INT_PREF);

    t.verifyPrefDidntExist(INT_PREF);
    assertThat(result).isEqualTo(3);
  }

  @Test
  public void testIntegerDoesExist() {
    t.setupIntegerExists(INT_PREF, 7);

    int result = t.mTypedPrefs.get(INT_PREF);

    t.verifyIntegerExisted(INT_PREF);
    assertThat(result).isEqualTo(7);
  }

  @Test
  public void testSetInteger() {
    t.mTypedPrefs.edit()
        .put(INT_PREF, 12)
        .commit();

    t.verifyIntegerWasSet(INT_PREF, 12);
  }

  @Test
  public void testRemoveInteger() {
    t.mTypedPrefs.edit()
        .remove(INT_PREF)
        .apply();

    t.verifyPrefWasRemoved(INT_PREF);
  }

  @Test(expected = NullPointerException.class)
  public void testSetIntegerException() {
    t.mTypedPrefs.edit()
        .put(INT_PREF, null);
  }

  @Test
  public void testNullIntegerDoesntExist() {
    Integer result = t.mTypedPrefs.get(INT_NULL_PREF);

    t.verifyPrefDidntExist(INT_NULL_PREF);
    assertThat(result).isNull();
  }

  @Test
  public void testNullIntegerDoesExist() {
    t.setupIntegerExists(INT_NULL_PREF, 18);

    Integer result = t.mTypedPrefs.get(INT_NULL_PREF);

    t.verifyIntegerExisted(INT_NULL_PREF);
    assertThat(result)
        .isNotNull()
        .isEqualTo(18);
  }

  @Test
  public void testSetNullInteger() {
    t.mTypedPrefs.edit()
        .put(INT_NULL_PREF, 22)
        .commit();

    t.verifyIntegerWasSet(INT_NULL_PREF, 22);
  }

  @Test
  public void testRemoveNullInteger() {
    t.mTypedPrefs.edit()
        .remove(INT_NULL_PREF)
        .apply();

    t.verifyPrefWasRemoved(INT_NULL_PREF);
  }

  @Test
  public void testSetNullIntegerNull() {
    t.mTypedPrefs.edit()
        .put(INT_NULL_PREF, null)
        .apply();

    t.verifyPrefWasRemoved(INT_NULL_PREF);
  }


  @Test
  public void testLongDoesntExist() {
    long result = t.mTypedPrefs.get(LONG_PREF);

    t.verifyPrefDidntExist(LONG_PREF);
    assertThat(result).isEqualTo(123L);
  }

  @Test
  public void testLongDoesExist() {
    t.setupLongExists(LONG_PREF, 15L);

    long result = t.mTypedPrefs.get(LONG_PREF);

    t.verifyLongExisted(LONG_PREF);
    assertThat(result).isEqualTo(15L);
  }

  @Test
  public void testSetLong() {
    t.mTypedPrefs.edit()
        .put(LONG_PREF, 145L)
        .commit();

    t.verifyLongWasSet(LONG_PREF, 145L);
  }

  @Test
  public void testRemoveLong() {
    t.mTypedPrefs.edit()
        .remove(LONG_PREF)
        .apply();

    t.verifyPrefWasRemoved(LONG_PREF);
  }

  @Test(expected = NullPointerException.class)
  public void testSetLongException() {
    t.mTypedPrefs.edit()
        .put(LONG_PREF, null);
  }

  @Test
  public void testNullLongDoesntExist() {
    Long result = t.mTypedPrefs.get(LONG_NULL_PREF);

    t.verifyPrefDidntExist(LONG_NULL_PREF);
    assertThat(result).isNull();
  }

  @Test
  public void testNullLongDoesExist() {
    t.setupLongExists(LONG_NULL_PREF, 173L);

    Long result = t.mTypedPrefs.get(LONG_NULL_PREF);

    t.verifyLongExisted(LONG_NULL_PREF);
    assertThat(result)
        .isNotNull()
        .isEqualTo(173L);
  }

  @Test
  public void testSetNullLong() {
    t.mTypedPrefs.edit()
        .put(LONG_NULL_PREF, 133L)
        .commit();

    t.verifyLongWasSet(LONG_NULL_PREF, 133L);
  }

  @Test
  public void testRemoveNullLong() {
    t.mTypedPrefs.edit()
        .remove(LONG_NULL_PREF)
        .apply();

    t.verifyPrefWasRemoved(LONG_NULL_PREF);
  }

  @Test
  public void testSetNullLongNull() {
    t.mTypedPrefs.edit()
        .put(LONG_NULL_PREF, null)
        .apply();

    t.verifyPrefWasRemoved(LONG_NULL_PREF);
  }

  @Test
  public void testStringDoesntExist() {
    String result = t.mTypedPrefs.get(STRING_PREF);

    t.verifyPrefDidntExist(STRING_PREF);
    assertThat(result).isEqualTo("default");
  }

  @Test
  public void testStringDoesExist() {
    t.setupStringExists(STRING_PREF, "sup");

    String result = t.mTypedPrefs.get(STRING_PREF);

    t.verifyStringExisted(STRING_PREF);
    assertThat(result).isEqualTo("sup");
  }

  @Test
  public void testSetString() {
    t.mTypedPrefs.edit()
        .put(STRING_PREF, "howdy")
        .commit();

    t.verifyStringWasSet(STRING_PREF, "howdy");
  }

  @Test
  public void testRemoveString() {
    t.mTypedPrefs.edit()
        .remove(STRING_PREF)
        .apply();

    t.verifyPrefWasRemoved(STRING_PREF);
  }

  @Test(expected = NullPointerException.class)
  public void testSetStringException() {
    t.mTypedPrefs.edit()
        .put(STRING_PREF, null);
  }

  @Test
  public void testNullStringDoesntExist() {
    String result = t.mTypedPrefs.get(STRING_NULL_PREF);

    t.verifyPrefDidntExist(STRING_NULL_PREF);
    assertThat(result).isNull();
  }

  @Test
  public void testNullStringDoesExist() {
    t.setupStringExists(STRING_NULL_PREF, "yooo");

    String result = t.mTypedPrefs.get(STRING_NULL_PREF);

    t.verifyStringExisted(STRING_NULL_PREF);
    assertThat(result)
        .isNotNull()
        .isEqualTo("yooo");
  }

  @Test
  public void testSetNullString() {
    t.mTypedPrefs.edit()
        .put(STRING_NULL_PREF, "hey now")
        .commit();

    t.verifyStringWasSet(STRING_NULL_PREF, "hey now");
  }

  @Test
  public void testRemoveNullString() {
    t.mTypedPrefs.edit()
        .remove(STRING_NULL_PREF)
        .apply();

    t.verifyPrefWasRemoved(STRING_NULL_PREF);
  }

  @Test
  public void testSetNullStringNull() {
    t.mTypedPrefs.edit()
        .put(STRING_NULL_PREF, null)
        .apply();

    t.verifyPrefWasRemoved(STRING_NULL_PREF);
  }

  @Test
  public void testDoubleDoesntExist() {
    double result = t.mTypedPrefs.get(DOUBLE_PREF);

    t.verifyPrefDidntExist(DOUBLE_PREF);
    assertThat(result).isEqualTo(1.2d);
  }

  @Test
  public void testDoubleDoesExist() {
    t.setupDoubleExists(DOUBLE_PREF, 3.5d);

    double result = t.mTypedPrefs.get(DOUBLE_PREF);

    t.verifyDoubleExisted(DOUBLE_PREF);
    assertThat(result).isEqualTo(3.5d);
  }

  @Test
  public void testSetDouble() {
    t.mTypedPrefs.edit()
        .put(DOUBLE_PREF, 7.6d)
        .commit();

    t.verifyDoubleWasSet(DOUBLE_PREF, 7.6d);
  }

  @Test
  public void testDoubleRemoved() {
    t.mTypedPrefs.edit()
        .remove(DOUBLE_PREF)
        .apply();

    t.verifyPrefWasRemoved(DOUBLE_PREF);
  }

  @Test(expected = NullPointerException.class)
  public void testSetDoubleException() {
    t.mTypedPrefs.edit()
        .put(DOUBLE_PREF, null);
  }

  @Test
  public void testNullDoubleDoesntExist() {
    Double result = t.mTypedPrefs.get(DOUBLE_NULL_PREF);

    t.verifyPrefDidntExist(DOUBLE_NULL_PREF);
    assertThat(result).isNull();
  }

  @Test
  public void testNullDoubleDoesExist() {
    t.setupDoubleExists(DOUBLE_NULL_PREF, 17.3d);

    Double result = t.mTypedPrefs.get(DOUBLE_NULL_PREF);

    t.verifyDoubleExisted(DOUBLE_NULL_PREF);
    assertThat(result)
        .isNotNull()
        .isEqualTo(17.3d);
  }

  @Test
  public void testSetNullDouble() {
    t.mTypedPrefs.edit()
        .put(DOUBLE_NULL_PREF, 13.3d)
        .commit();

    t.verifyDoubleWasSet(DOUBLE_NULL_PREF, 13.3d);
  }

  @Test
  public void testNullDoubleRemoved() {
    t.mTypedPrefs.edit()
        .remove(DOUBLE_NULL_PREF)
        .apply();

    t.verifyPrefWasRemoved(DOUBLE_NULL_PREF);
  }

  @Test
  public void testSetNullDoubleNull() {
    t.mTypedPrefs.edit()
        .put(DOUBLE_NULL_PREF, null)
        .apply();

    t.verifyPrefWasRemoved(DOUBLE_NULL_PREF);
  }
}
