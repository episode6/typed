package com.episode6.hackit.typed.preferences;

import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.typed.core.util.Supplier;
import com.episode6.hackit.typed.testing.Rules;
import com.google.gson.reflect.TypeToken;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Tests {@link TypedPrefsImpl} usage with Complex types that are translated via Gson
 */
public class TypedPrefsGenericsTest {

  private static final PrefNamespace PREF_NAMESPACE = PrefNamespace.ROOT.extend("testNamespace").extend("generics");

  private static final PrefKey<SimpleTestClass> SIMPLE_PREF = PREF_NAMESPACE
      .key(SimpleTestClass.class)
      .named("simpleTestPref")
      .buildWithDefault(new Supplier<SimpleTestClass>() {
        @Override
        public SimpleTestClass get() {
          return new SimpleTestClass("defaultValue");
        }
      });
  private static final OptPrefKey<SimpleTestClass> SIMPLE_NULL_PREF = PREF_NAMESPACE
      .key(SimpleTestClass.class)
      .named("simpleNullTestPref")
      .buildOptional();

  private static final PrefKey<HashMap<String, SimpleTestClass>> COMPLEX_MAP_PREF = PREF_NAMESPACE
      .key(new TypeToken<HashMap<String, SimpleTestClass>>() {})
      .named("complexMapPref")
      .buildWithDefault(new Supplier<HashMap<String, SimpleTestClass>>() {
        @Override
        public HashMap<String, SimpleTestClass> get() {
          HashMap<String, SimpleTestClass> map = new HashMap<>();
          map.put("key1", new SimpleTestClass("value1"));
          map.put("key2", new SimpleTestClass("value2"));
          return map;
        }
      });
  private static final OptPrefKey<HashMap<String, SimpleTestClass>> COMPLEX_MAP_NULL_PREF = PREF_NAMESPACE
      .key(new TypeToken<HashMap<String, SimpleTestClass>>() {})
      .named("complexMapNullPref")
      .buildOptional();

  private final SharedTestResources t = new SharedTestResources();
  @Rule public final Mockspresso.Rule mockspresso = Rules.mockspressoBuilder()
      .testResources(t)
      .buildRule();

  @Test
  public void testSimplePrefDoesntExist() {
    SimpleTestClass result = t.mTypedPrefs.get(SIMPLE_PREF);

    t.verifyPrefDidntExist(SIMPLE_PREF);
    assertThat(result.value).isEqualTo("defaultValue");
  }

  @Test
  public void testSimplePrefDoesExist() {
    SimpleTestClass expected = new SimpleTestClass("newValue");
    t.setupPrefExists(SIMPLE_PREF, expected);

    SimpleTestClass result = t.mTypedPrefs.get(SIMPLE_PREF);

    t.verifyPrefExisted(SIMPLE_PREF);
    assertThat(result)
        .isNotNull()
        .isEqualTo(expected);
  }

  @Test
  public void testSetSimplePref() {
    SimpleTestClass newValue = new SimpleTestClass("newSetValue");

    t.mTypedPrefs.edit()
        .put(SIMPLE_PREF, newValue)
        .apply();

    t.verifyPrefWasSet(SIMPLE_PREF, newValue);
  }

  @Test(expected = NullPointerException.class)
  public void testSetSimplePrefException() {
    t.mTypedPrefs.edit()
        .put(SIMPLE_PREF, null);
  }

  @Test
  public void testRemoveSimplePref() {
    t.mTypedPrefs.edit()
        .remove(SIMPLE_PREF)
        .apply();

    t.verifyPrefWasRemoved(SIMPLE_PREF);
  }

  @Test
  public void testNullSimplePrefDoesntExist() {
    SimpleTestClass result = t.mTypedPrefs.get(SIMPLE_NULL_PREF);

    t.verifyPrefDidntExist(SIMPLE_NULL_PREF);
    assertThat(result).isNull();
  }

  @Test
  public void testNullSimplePrefDoesExist() {
    SimpleTestClass expected = new SimpleTestClass("newValue");
    t.setupPrefExists(SIMPLE_NULL_PREF, expected);

    SimpleTestClass result = t.mTypedPrefs.get(SIMPLE_NULL_PREF);

    t.verifyPrefExisted(SIMPLE_NULL_PREF);
    assertThat(result)
        .isNotNull()
        .isEqualTo(expected);
  }

  @Test
  public void testSetSimpleNullPref() {
    SimpleTestClass newValue = new SimpleTestClass("newSetValue");

    t.mTypedPrefs.edit()
        .put(SIMPLE_NULL_PREF, newValue)
        .apply();

    t.verifyPrefWasSet(SIMPLE_NULL_PREF, newValue);
  }

  @Test
  public void testRemoveSimpleNullPref() {
    t.mTypedPrefs.edit()
        .remove(SIMPLE_NULL_PREF)
        .apply();

    t.verifyPrefWasRemoved(SIMPLE_NULL_PREF);
  }

  @Test
  public void testSetSimpleNullPrefNull() {
    t.mTypedPrefs.edit()
        .put(SIMPLE_NULL_PREF, null)
        .apply();

    t.verifyPrefWasRemoved(SIMPLE_NULL_PREF);
  }

  @Test
  public void testComplexPrefDoesntExist() {
    HashMap<String, SimpleTestClass> result = t.mTypedPrefs.get(COMPLEX_MAP_PREF);

    t.verifyPrefDidntExist(COMPLEX_MAP_PREF);
    assertThat(result)
        .isNotNull()
        .containsKey("key1")
        .containsKey("key2");
    assertThat(result.get("key1").value).isEqualTo("value1");
    assertThat(result.get("key2").value).isEqualTo("value2");
  }

  @Test
  public void testComplexPrefDoesExist() {
    HashMap<String, SimpleTestClass> expected = new HashMap<>();
    t.setupPrefExists(COMPLEX_MAP_PREF, expected);

    HashMap<String, SimpleTestClass> result = t.mTypedPrefs.get(COMPLEX_MAP_PREF);

    t.verifyPrefExisted(COMPLEX_MAP_PREF);
    assertThat(result)
        .isNotNull()
        .isEqualTo(expected);
  }

  @Test
  public void testSetComplexPref() {
    HashMap<String, SimpleTestClass> newValue = new HashMap<>();

    t.mTypedPrefs.edit()
        .put(COMPLEX_MAP_PREF, newValue)
        .apply();

    t.verifyPrefWasSet(COMPLEX_MAP_PREF, newValue);
  }

  @Test
  public void testComplexPrefRemoved() {
    t.mTypedPrefs.edit()
        .remove(COMPLEX_MAP_PREF)
        .apply();

    t.verifyPrefWasRemoved(COMPLEX_MAP_PREF);
  }

  @Test(expected = NullPointerException.class)
  public void testSetComplexPrefException() {
    t.mTypedPrefs.edit()
        .put(COMPLEX_MAP_PREF, null);
  }

  @Test
  public void testNullComplexPrefDoesntExist() {
    HashMap<String, SimpleTestClass> result = t.mTypedPrefs.get(COMPLEX_MAP_NULL_PREF);

    t.verifyPrefDidntExist(COMPLEX_MAP_NULL_PREF);
    assertThat(result).isNull();
  }

  @Test
  public void testNullComplexPrefDoesExist() {
    HashMap<String, SimpleTestClass> expected = new HashMap<>();
    t.setupPrefExists(COMPLEX_MAP_NULL_PREF, expected);

    HashMap<String, SimpleTestClass> result = t.mTypedPrefs.get(COMPLEX_MAP_NULL_PREF);

    t.verifyPrefExisted(COMPLEX_MAP_NULL_PREF);
    assertThat(result)
        .isNotNull()
        .isEqualTo(expected);
  }

  @Test
  public void testSetComplexNullPref() {
    HashMap<String, SimpleTestClass> newValue = new HashMap<>();

    t.mTypedPrefs.edit()
        .put(COMPLEX_MAP_NULL_PREF, newValue)
        .apply();

    t.verifyPrefWasSet(COMPLEX_MAP_NULL_PREF, newValue);
  }

  @Test
  public void testRemoveComplexNullPref() {
    t.mTypedPrefs.edit()
        .remove(COMPLEX_MAP_NULL_PREF)
        .apply();

    t.verifyPrefWasRemoved(COMPLEX_MAP_NULL_PREF);
  }

  @Test
  public void testSetComplexNullPrefNull() {
    t.mTypedPrefs.edit()
        .put(COMPLEX_MAP_NULL_PREF, null)
        .apply();

    t.verifyPrefWasRemoved(COMPLEX_MAP_NULL_PREF);
  }

  public static class SimpleTestClass {
    final String value;

    public SimpleTestClass(String value) {
      this.value = value;
    }
  }
}
