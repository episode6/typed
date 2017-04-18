package com.episode6.hackit.typed.preferences;

import android.content.SharedPreferences;
import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.mockspresso.annotation.RealObject;
import com.episode6.hackit.typed.core.util.Supplier;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Type;

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

  public static class SimpleTestClass {
    final String value;

    public SimpleTestClass(String value) {
      this.value = value;
    }
  }
}
