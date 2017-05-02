package com.episode6.hackit.typed.preferences;

import android.content.SharedPreferences;
import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.mockspresso.annotation.RealObject;
import com.episode6.hackit.typed.core.TypedKey;
import com.episode6.hackit.typed.preferences.cache.ObjectCache;
import com.episode6.hackit.typed.testing.Answers;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Shared test resources for some of our tests
 */
public class SharedTestResources {

  @Mock SharedPreferences mSharedPreferences;
  /*Mock*/ SharedPreferences.Editor mEditor;
  @Mock Gson mGson;
  @Mock ObjectCache mCache;

  @RealObject(implementation = TypedPrefsImpl.class) TypedPrefs mTypedPrefs;
  TypedPrefs mTypedPrefsNoCache;

  @Before
  public void init(Mockspresso mockspresso) {
    mEditor = mock(SharedPreferences.Editor.class, Answers.builderAnswer());
    when(mSharedPreferences.edit()).thenReturn(mEditor);
    when(mGson.toJson(any())).thenReturn("someFakeJson");

    mTypedPrefsNoCache = mockspresso.buildUpon()
        .dependency(ObjectCache.class, null)
        .build()
        .create(TypedPrefsImpl.class);
  }

  void verifyPrefDidntExist(TypedKey key) {
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor, mGson);
    inOrder.verify(mCache).get(key);
    inOrder.verify(mSharedPreferences).contains(key.getKeyName().toString());
    verifyNoMoreInteractions(mCache, mSharedPreferences, mEditor, mGson);
  }

  void verifyPrefWasRemoved(TypedKey key) {
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mCache).remove(key);
    inOrder.verify(mEditor).remove(key.getKeyName().toString());
    inOrder.verify(mEditor).apply();
    verifyNoMoreInteractions(mCache, mSharedPreferences, mEditor, mGson);
  }

  <T> void setupPrefExists(TypedKey<T> key, T expectedValue) {
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getString(keyName, null)).thenReturn("someFakeJson");
    when(mGson.fromJson("someFakeJson", key.getObjectType())).thenReturn(expectedValue);
  }

  <T> void verifyPrefExisted(TypedKey<T> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor, mGson);
    inOrder.verify(mCache).get(key);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getString(keyName, null);
    inOrder.verify(mGson).fromJson("someFakeJson", key.getObjectType());
    inOrder.verify(mCache).put(eq(key), any());
    verifyNoMoreInteractions(mCache, mSharedPreferences, mEditor, mGson);
  }

  <T> void verifyPrefWasSet(TypedKey<T> key, T expectedValue) {
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mCache).put(key, expectedValue);
    inOrder.verify(mGson).toJson(expectedValue);
    inOrder.verify(mEditor).putString(key.getKeyName().toString(), "someFakeJson");
    inOrder.verify(mEditor).apply();
    verifyNoMoreInteractions(mCache, mSharedPreferences, mEditor, mGson);
  }

  void setupBooleanExists(TypedKey<Boolean> key, boolean expectedValue) {
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getBoolean(eq(keyName), anyBoolean())).thenReturn(expectedValue);
  }

  void verifyBooleanExisted(TypedKey<Boolean> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor, mGson);
    inOrder.verify(mCache).get(key);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getBoolean(eq(keyName), anyBoolean());
    inOrder.verify(mCache).put(eq(key), any(Boolean.class));
    verifyNoMoreInteractions(mCache, mSharedPreferences, mEditor, mGson);
  }

  void verifyBooleanWasSet(TypedKey<Boolean> key, boolean expectedValue) {
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mCache).put(key, expectedValue);
    inOrder.verify(mEditor).putBoolean(key.getKeyName().toString(), expectedValue);
    inOrder.verify(mEditor).commit();
    verifyNoMoreInteractions(mCache, mSharedPreferences, mEditor, mGson);
  }

  void setupFloatExists(TypedKey<Float> key, float expectedValue) {
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getFloat(eq(keyName), anyFloat())).thenReturn(expectedValue);
  }

  void verifyFloatExisted(TypedKey<Float> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor, mGson);
    inOrder.verify(mCache).get(key);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getFloat(eq(keyName), anyFloat());
    inOrder.verify(mCache).put(eq(key), any(Float.class));
    verifyNoMoreInteractions(mCache, mSharedPreferences, mEditor, mGson);
  }

  void verifyFloatWasSet(TypedKey<Float> key, float expectedValue) {
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mCache).put(key, expectedValue);
    inOrder.verify(mEditor).putFloat(key.getKeyName().toString(), expectedValue);
    inOrder.verify(mEditor).commit();
    verifyNoMoreInteractions(mCache, mSharedPreferences, mEditor, mGson);
  }

  void setupIntegerExists(TypedKey<Integer> key, int expectedValue) {
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getInt(eq(keyName), anyInt())).thenReturn(expectedValue);
  }

  void verifyIntegerExisted(TypedKey<Integer> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor, mGson);
    inOrder.verify(mCache).get(key);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getInt(eq(keyName), anyInt());
    inOrder.verify(mCache).put(eq(key), any(Integer.class));
    verifyNoMoreInteractions(mCache, mSharedPreferences, mEditor, mGson);
  }

  void verifyIntegerWasSet(TypedKey<Integer> key, int expectedValue) {
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mCache).put(key, expectedValue);
    inOrder.verify(mEditor).putInt(key.getKeyName().toString(), expectedValue);
    inOrder.verify(mEditor).commit();
    verifyNoMoreInteractions(mCache, mSharedPreferences, mEditor, mGson);
  }

  void setupLongExists(TypedKey<Long> key, long expectedValue) {
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getLong(eq(keyName), anyLong())).thenReturn(expectedValue);
  }

  void verifyLongExisted(TypedKey<Long> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor, mGson);
    inOrder.verify(mCache).get(key);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getLong(eq(keyName), anyLong());
    inOrder.verify(mCache).put(eq(key), any(Long.class));
    verifyNoMoreInteractions(mCache, mSharedPreferences, mEditor, mGson);
  }

  void verifyLongWasSet(TypedKey<Long> key, long expectedValue) {
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mCache).put(key, expectedValue);
    inOrder.verify(mEditor).putLong(key.getKeyName().toString(), expectedValue);
    inOrder.verify(mEditor).commit();
    verifyNoMoreInteractions(mCache, mSharedPreferences, mEditor, mGson);
  }

  void setupStringExists(TypedKey<String> key, String expectedValue) {
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getString(eq(keyName), nullable(String.class))).thenReturn(expectedValue);
  }

  void verifyStringExisted(TypedKey<String> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor, mGson);
    inOrder.verify(mCache).get(key);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getString(eq(keyName), nullable(String.class));
    inOrder.verify(mCache).put(eq(key), any(String.class));
    verifyNoMoreInteractions(mCache, mSharedPreferences, mEditor, mGson);
  }

  void verifyStringWasSet(TypedKey<String> key, String expectedValue) {
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mCache).put(key, expectedValue);
    inOrder.verify(mEditor).putString(key.getKeyName().toString(), expectedValue);
    inOrder.verify(mEditor).commit();
    verifyNoMoreInteractions(mCache, mSharedPreferences, mEditor, mGson);
  }

  void setupDoubleExists(TypedKey<Double> key, double expectedValue) {
    long doubleBits = Double.doubleToRawLongBits(expectedValue);
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getLong(eq(keyName), anyLong())).thenReturn(doubleBits);
  }

  void verifyDoubleExisted(TypedKey<Double> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor, mGson);
    inOrder.verify(mCache).get(key);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getLong(eq(keyName), anyLong());
    inOrder.verify(mCache).put(eq(key), any(Double.class));
    verifyNoMoreInteractions(mCache, mSharedPreferences, mEditor, mGson);
  }

  void verifyDoubleWasSet(TypedKey<Double> key, double expectedValue) {
    InOrder inOrder = Mockito.inOrder(mCache, mSharedPreferences, mEditor, mGson);
    long doubleBits = Double.doubleToRawLongBits(expectedValue);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mCache).put(key, expectedValue);
    inOrder.verify(mEditor).putLong(key.getKeyName().toString(), doubleBits);
    inOrder.verify(mEditor).commit();
    verifyNoMoreInteractions(mCache, mSharedPreferences, mEditor, mGson);
  }
}
