package com.episode6.hackit.typed.preferences;

import android.content.SharedPreferences;
import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.mockspresso.annotation.RealObject;
import com.episode6.hackit.typed.core.TypedKey;
import com.episode6.hackit.typed.preferences.cache.ObjectCache;
import com.episode6.hackit.typed.core.util.Supplier;
import com.episode6.hackit.typed.testing.Answers;
import com.episode6.hackit.typed.testing.Rules;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
      .buildNullable();

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
      .buildNullable();

  @Rule public final Mockspresso.Rule mockspresso = Rules.mockspresso();

  @Mock SharedPreferences mSharedPreferences;
  /*Mock*/ SharedPreferences.Editor mEditor;
  @Mock Gson mGson;
  @Mock ObjectCache mCache;

  @RealObject(implementation = TypedPrefsImpl.class)
  TypedPrefs mTypedPrefs;

  @Before
  public void setup() {
    mEditor = mock(SharedPreferences.Editor.class, Answers.builderAnswer());
    when(mSharedPreferences.edit()).thenReturn(mEditor);
    when(mGson.toJson(any())).thenReturn("someFakeJson");
  }

  @Test
  public void testSimplePrefDoesntExist() {
    SimpleTestClass result = mTypedPrefs.get(SIMPLE_PREF);

    verifyPrefDidntExist(SIMPLE_PREF);
    assertThat(result.value).isEqualTo("defaultValue");
  }

  @Test
  public void testSimplePrefDoesExist() {
    SimpleTestClass expected = new SimpleTestClass("newValue");
    setupPrefExists(SIMPLE_PREF, expected);

    SimpleTestClass result = mTypedPrefs.get(SIMPLE_PREF);

    verifyPrefExisted(SIMPLE_PREF);
    assertThat(result)
        .isNotNull()
        .isEqualTo(expected);
  }

  @Test
  public void testSetSimplePref() {
    SimpleTestClass newValue = new SimpleTestClass("newSetValue");

    mTypedPrefs.edit()
        .put(SIMPLE_PREF, newValue)
        .commit();

    verifyPrefWasSet(SIMPLE_PREF, newValue);
  }

  @Test(expected = NullPointerException.class)
  public void testSetSimplePrefException() {
    mTypedPrefs.edit()
        .put(SIMPLE_PREF, null);
  }

  @Test
  public void testRemoveSimplePref() {
    mTypedPrefs.edit()
        .remove(SIMPLE_PREF)
        .commit();

    verifyPrefWasRemoved(SIMPLE_PREF);
  }

  @Test
  public void testNullSimplePrefDoesntExist() {
    SimpleTestClass result = mTypedPrefs.get(SIMPLE_NULL_PREF);

    verifyPrefDidntExist(SIMPLE_NULL_PREF);
    assertThat(result).isNull();
  }

  @Test
  public void testNullSimplePrefDoesExist() {
    SimpleTestClass expected = new SimpleTestClass("newValue");
    setupPrefExists(SIMPLE_NULL_PREF, expected);

    SimpleTestClass result = mTypedPrefs.get(SIMPLE_NULL_PREF);

    verifyPrefExisted(SIMPLE_NULL_PREF);
    assertThat(result)
        .isNotNull()
        .isEqualTo(expected);
  }

  @Test
  public void testSetSimpleNullPref() {
    SimpleTestClass newValue = new SimpleTestClass("newSetValue");

    mTypedPrefs.edit()
        .put(SIMPLE_NULL_PREF, newValue)
        .commit();

    verifyPrefWasSet(SIMPLE_NULL_PREF, newValue);
  }

  @Test
  public void testRemoveSimpleNullPref() {
    mTypedPrefs.edit()
        .remove(SIMPLE_NULL_PREF)
        .commit();

    verifyPrefWasRemoved(SIMPLE_NULL_PREF);
  }

  @Test
  public void testSetSimpleNullPrefNull() {
    mTypedPrefs.edit()
        .put(SIMPLE_NULL_PREF, null)
        .commit();

    verifyPrefWasRemoved(SIMPLE_NULL_PREF);
  }

  @Test
  public void testComplexPrefDoesntExist() {
    HashMap<String, SimpleTestClass> result = mTypedPrefs.get(COMPLEX_MAP_PREF);

    verifyPrefDidntExist(COMPLEX_MAP_PREF);
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
    setupPrefExists(COMPLEX_MAP_PREF, expected);

    HashMap<String, SimpleTestClass> result = mTypedPrefs.get(COMPLEX_MAP_PREF);

    verifyPrefExisted(COMPLEX_MAP_PREF);
    assertThat(result)
        .isNotNull()
        .isEqualTo(expected);
  }

  @Test
  public void testSetComplexPref() {
    HashMap<String, SimpleTestClass> newValue = new HashMap<>();

    mTypedPrefs.edit()
        .put(COMPLEX_MAP_PREF, newValue)
        .commit();

    verifyPrefWasSet(COMPLEX_MAP_PREF, newValue);
  }

  @Test
  public void testComplexPrefRemoved() {
    mTypedPrefs.edit()
        .remove(COMPLEX_MAP_PREF)
        .commit();

    verifyPrefWasRemoved(COMPLEX_MAP_PREF);
  }

  @Test(expected = NullPointerException.class)
  public void testSetComplexPrefException() {
    mTypedPrefs.edit()
        .put(COMPLEX_MAP_PREF, null);
  }

  @Test
  public void testNullComplexPrefDoesntExist() {
    HashMap<String, SimpleTestClass> result = mTypedPrefs.get(COMPLEX_MAP_NULL_PREF);

    verifyPrefDidntExist(COMPLEX_MAP_NULL_PREF);
    assertThat(result).isNull();
  }

  @Test
  public void testNullComplexPrefDoesExist() {
    HashMap<String, SimpleTestClass> expected = new HashMap<>();
    setupPrefExists(COMPLEX_MAP_NULL_PREF, expected);

    HashMap<String, SimpleTestClass> result = mTypedPrefs.get(COMPLEX_MAP_NULL_PREF);

    verifyPrefExisted(COMPLEX_MAP_NULL_PREF);
    assertThat(result)
        .isNotNull()
        .isEqualTo(expected);
  }

  @Test
  public void testSetComplexNullPref() {
    HashMap<String, SimpleTestClass> newValue = new HashMap<>();

    mTypedPrefs.edit()
        .put(COMPLEX_MAP_NULL_PREF, newValue)
        .commit();

    verifyPrefWasSet(COMPLEX_MAP_NULL_PREF, newValue);
  }

  @Test
  public void testRemoveComplexNullPref() {
    mTypedPrefs.edit()
        .remove(COMPLEX_MAP_NULL_PREF)
        .commit();

    verifyPrefWasRemoved(COMPLEX_MAP_NULL_PREF);
  }

  @Test
  public void testSetComplexNullPrefNull() {
    mTypedPrefs.edit()
        .put(COMPLEX_MAP_NULL_PREF, null)
        .commit();

    verifyPrefWasRemoved(COMPLEX_MAP_NULL_PREF);
  }

  private void verifyPrefDidntExist(TypedKey key) {
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor, mGson);
    inOrder.verify(mCache).get(key);
    inOrder.verify(mSharedPreferences).contains(key.getKeyName().toString());
    inOrder.verifyNoMoreInteractions();
  }

  private void verifyPrefWasRemoved(TypedKey key) {
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mCache).remove(key);
    inOrder.verify(mEditor).remove(key.getKeyName().toString());
    inOrder.verify(mEditor).commit();
    inOrder.verifyNoMoreInteractions();
  }

  private <T> void setupPrefExists(TypedKey<T> key, T expectedValue) {
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getString(keyName, null)).thenReturn("someFakeJson");
    when(mGson.fromJson("someFakeJson", key.getObjectType())).thenReturn(expectedValue);
  }

  private <T> void verifyPrefExisted(TypedKey<T> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor, mGson);
    inOrder.verify(mCache).get(key);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getString(keyName, null);
    inOrder.verify(mGson).fromJson("someFakeJson", key.getObjectType());
    inOrder.verify(mCache).put(eq(key), any());
    inOrder.verifyNoMoreInteractions();
  }

  private <T> void verifyPrefWasSet(TypedKey<T> key, T expectedValue) {
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mCache).put(key, expectedValue);
    inOrder.verify(mGson).toJson(expectedValue);
    inOrder.verify(mEditor).putString(key.getKeyName().toString(), "someFakeJson");
    inOrder.verify(mEditor).commit();
    inOrder.verifyNoMoreInteractions();
  }

  public static class SimpleTestClass {
    final String value;

    public SimpleTestClass(String value) {
      this.value = value;
    }
  }
}
