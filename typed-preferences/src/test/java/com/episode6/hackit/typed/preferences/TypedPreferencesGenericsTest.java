package com.episode6.hackit.typed.preferences;

import android.content.SharedPreferences;
import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.mockspresso.annotation.RealObject;
import com.episode6.hackit.typed.core.util.Supplier;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Type;
import java.util.HashMap;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 *
 */
public class TypedPreferencesGenericsTest {

  private static final PrefNamespace PREF_NAMESPACE = PrefNamespace.ROOT.extend("testNamespace").extend("generics");

  private static final PrefKey<SimpleTestClass> SIMPLE_PREF = PREF_NAMESPACE.key(SimpleTestClass.class)
      .named("simpleTestPref")
      .buildWithDefault(new Supplier<SimpleTestClass>() {
        @Override
        public SimpleTestClass get() {
          return new SimpleTestClass("defaultValue");
        }
      });
  private static final NullablePrefKey<SimpleTestClass> SIMPLE_NULL_PREF = PREF_NAMESPACE.key(SimpleTestClass.class)
      .named("simpleNullTestPref")
      .buildNullable();

  private static final PrefKey<HashMap<String, SimpleTestClass>> COMPLEX_MAP_PREF = PREF_NAMESPACE.key(new TypeToken<HashMap<String, SimpleTestClass>>() {})
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
  private static final NullablePrefKey<HashMap<String, SimpleTestClass>> COMPLEX_MAP_NULL_PREF = PREF_NAMESPACE.key(new TypeToken<HashMap<String, SimpleTestClass>>() {})
      .named("complexMapNullPref")
      .buildNullable();

  @Rule public final Mockspresso.Rule mockspresso = Rules.mockspresso();

  @Mock SharedPreferences mSharedPreferences;
  /*Mock*/ SharedPreferences.Editor mEditor;
  @Mock Gson mGson;

  @RealObject(implementation = TypedPreferencesImpl.class) TypedPreferences mTypedPreferences;

  @Before
  public void setup() {
    mEditor = mock(SharedPreferences.Editor.class, Answers.builderAnswer());
    when(mSharedPreferences.edit()).thenReturn(mEditor);
  }

  @Test
  public void testSimplePrefDoesntExist() {
    String keyName = SIMPLE_PREF.getKeyName().toString();

    SimpleTestClass result = mTypedPreferences.get(SIMPLE_PREF);

    verify(mSharedPreferences).contains(keyName);
    assertThat(result).isNotNull();
    assertThat(result.value).isEqualTo("defaultValue");
  }

  @Test
  public void testSimplePrefDoesExist() {
    String keyName = SIMPLE_PREF.getKeyName().toString();
    Type objectType = SIMPLE_PREF.getObjectType();
    SimpleTestClass expected = new SimpleTestClass("newValue");
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getString(keyName, null)).thenReturn("newValueJson");
    when(mGson.fromJson("newValueJson", objectType)).thenReturn(expected);

    SimpleTestClass result = mTypedPreferences.get(SIMPLE_PREF);

    verify(mSharedPreferences).contains(keyName);
    verify(mSharedPreferences).getString(keyName, null);
    verify(mGson).fromJson("newValueJson", objectType);
    assertThat(result)
        .isNotNull()
        .isEqualTo(expected);
  }

  @Test
  public void testSetSimplePref() {
    String keyName = SIMPLE_PREF.getKeyName().toString();
    SimpleTestClass newValue = new SimpleTestClass("newSetValue");
    when(mGson.toJson(newValue)).thenReturn("newSetValueJson");

    mTypedPreferences.edit()
        .put(SIMPLE_PREF, newValue)
        .commit();

    verify(mSharedPreferences).edit();
    verify(mGson).toJson(newValue);
    verify(mEditor).putString(keyName, "newSetValueJson");
    verify(mEditor).commit();
  }

  @Test(expected = NullPointerException.class)
  public void testSetSimplePrefException() {
    mTypedPreferences.edit()
        .put(SIMPLE_PREF, null);
  }

  @Test
  public void testNullSimplePrefDoesntExist() {
    String keyName = SIMPLE_NULL_PREF.getKeyName().toString();

    SimpleTestClass result = mTypedPreferences.get(SIMPLE_NULL_PREF);

    verify(mSharedPreferences).contains(keyName);
    assertThat(result).isNull();
  }

  @Test
  public void testNullSimplePrefDoesExist() {
    String keyName = SIMPLE_NULL_PREF.getKeyName().toString();
    Type objectType = SIMPLE_NULL_PREF.getObjectType();
    SimpleTestClass expected = new SimpleTestClass("newValue");
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getString(keyName, null)).thenReturn("newValueJson");
    when(mGson.fromJson("newValueJson", objectType)).thenReturn(expected);

    SimpleTestClass result = mTypedPreferences.get(SIMPLE_NULL_PREF);

    verify(mSharedPreferences).contains(keyName);
    verify(mSharedPreferences).getString(keyName, null);
    verify(mGson).fromJson("newValueJson", objectType);
    assertThat(result)
        .isNotNull()
        .isEqualTo(expected);
  }

  @Test
  public void testSetSimpleNullPref() {
    String keyName = SIMPLE_NULL_PREF.getKeyName().toString();
    SimpleTestClass newValue = new SimpleTestClass("newSetValue");
    when(mGson.toJson(newValue)).thenReturn("newSetValueJson");

    mTypedPreferences.edit()
        .put(SIMPLE_NULL_PREF, newValue)
        .apply();

    verify(mSharedPreferences).edit();
    verify(mGson).toJson(newValue);
    verify(mEditor).putString(keyName, "newSetValueJson");
    verify(mEditor).apply();
  }

  @Test
  public void testSetSimpleNullPrefNull() {
    String keyName = SIMPLE_NULL_PREF.getKeyName().toString();

    mTypedPreferences.edit()
        .put(SIMPLE_NULL_PREF, null)
        .commit();

    verify(mSharedPreferences).edit();
    verify(mEditor).remove(keyName);
    verify(mEditor).commit();
  }

  @Test
  public void testComplexPrefDoesntExist() {
    String keyName = COMPLEX_MAP_PREF.getKeyName().toString();

    HashMap<String, SimpleTestClass> result = mTypedPreferences.get(COMPLEX_MAP_PREF);

    verify(mSharedPreferences).contains(keyName);
    assertThat(result)
        .isNotNull()
        .containsKey("key1")
        .containsKey("key2");
    assertThat(result.get("key1").value).isEqualTo("value1");
    assertThat(result.get("key2").value).isEqualTo("value2");
  }

  @Test
  public void testComplexPrefDoesExist() {
    String keyName = COMPLEX_MAP_PREF.getKeyName().toString();
    Type objectType = COMPLEX_MAP_PREF.getObjectType();
    HashMap<String, SimpleTestClass> expected = new HashMap<>();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getString(keyName, null)).thenReturn("newValueJson");
    when(mGson.fromJson("newValueJson", objectType)).thenReturn(expected);

    HashMap<String, SimpleTestClass> result = mTypedPreferences.get(COMPLEX_MAP_PREF);

    verify(mSharedPreferences).contains(keyName);
    verify(mSharedPreferences).getString(keyName, null);
    verify(mGson).fromJson("newValueJson", objectType);
    assertThat(result)
        .isNotNull()
        .isEqualTo(expected);
  }

  @Test
  public void testSetComplexPref() {
    String keyName = COMPLEX_MAP_PREF.getKeyName().toString();
    HashMap<String, SimpleTestClass> newValue = new HashMap<>();
    when(mGson.toJson(newValue)).thenReturn("newSetValueJson");

    mTypedPreferences.edit()
        .put(COMPLEX_MAP_PREF, newValue)
        .commit();

    verify(mSharedPreferences).edit();
    verify(mGson).toJson(newValue);
    verify(mEditor).putString(keyName, "newSetValueJson");
    verify(mEditor).commit();
  }

  @Test(expected = NullPointerException.class)
  public void testSetComplexPrefException() {
    mTypedPreferences.edit()
        .put(COMPLEX_MAP_PREF, null);
  }

  @Test
  public void testNullComplexPrefDoesntExist() {
    String keyName = COMPLEX_MAP_NULL_PREF.getKeyName().toString();

    HashMap<String, SimpleTestClass> result = mTypedPreferences.get(COMPLEX_MAP_NULL_PREF);

    verify(mSharedPreferences).contains(keyName);
    assertThat(result).isNull();
  }

  @Test
  public void testNullComplexPrefDoesExist() {
    String keyName = COMPLEX_MAP_NULL_PREF.getKeyName().toString();
    Type objectType = COMPLEX_MAP_NULL_PREF.getObjectType();
    HashMap<String, SimpleTestClass> expected = new HashMap<>();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getString(keyName, null)).thenReturn("newValueJson");
    when(mGson.fromJson("newValueJson", objectType)).thenReturn(expected);

    HashMap<String, SimpleTestClass> result = mTypedPreferences.get(COMPLEX_MAP_NULL_PREF);

    verify(mSharedPreferences).contains(keyName);
    verify(mSharedPreferences).getString(keyName, null);
    verify(mGson).fromJson("newValueJson", objectType);
    assertThat(result)
        .isNotNull()
        .isEqualTo(expected);
  }

  @Test
  public void testSetComplexNullPref() {
    String keyName = COMPLEX_MAP_NULL_PREF.getKeyName().toString();
    HashMap<String, SimpleTestClass> newValue = new HashMap<>();
    when(mGson.toJson(newValue)).thenReturn("newSetValueJson");

    mTypedPreferences.edit()
        .put(COMPLEX_MAP_NULL_PREF, newValue)
        .apply();

    verify(mSharedPreferences).edit();
    verify(mGson).toJson(newValue);
    verify(mEditor).putString(keyName, "newSetValueJson");
    verify(mEditor).apply();
  }

  @Test
  public void testSetComplexNullPrefNull() {
    String keyName = COMPLEX_MAP_NULL_PREF.getKeyName().toString();

    mTypedPreferences.edit()
        .put(COMPLEX_MAP_NULL_PREF, null)
        .commit();

    verify(mSharedPreferences).edit();
    verify(mEditor).remove(keyName);
    verify(mEditor).commit();
  }

  public static class SimpleTestClass {
    final String value;

    public SimpleTestClass(String value) {
      this.value = value;
    }
  }
}
