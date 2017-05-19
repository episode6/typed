package com.episode6.hackit.typed.preferences;

import android.content.SharedPreferences;
import com.episode6.hackit.mockspresso.annotation.RealObject;
import com.episode6.hackit.typed.core.TypedKey;
import com.episode6.hackit.typed.testing.Answers;
import com.google.gson.Gson;
import org.junit.Before;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.spi.PowerMockPolicy;
import org.powermock.mockpolicies.MockPolicyClassLoadingSettings;
import org.powermock.mockpolicies.MockPolicyInterceptionSettings;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.*;

/**
 * Shared test resources for some of our tests
 */
public class SharedTestResources {

  static class MockPolicy implements PowerMockPolicy {

    @Override
    public void applyClassLoadingPolicy(MockPolicyClassLoadingSettings settings) {
      settings.addFullyQualifiedNamesOfClassesToLoadByMockClassloader(Gson.class.getName());
    }

    @Override
    public void applyInterceptionPolicy(MockPolicyInterceptionSettings settings) {

    }
  }

  @Mock SharedPreferences mSharedPreferences;
  /*Mock*/ SharedPreferences.Editor mEditor;
  @Mock Gson mGson;

  @RealObject(implementation = TypedPrefsImpl.class) TypedPrefs mTypedPrefs;

  @Before
  public void init() {
    mEditor = mock(SharedPreferences.Editor.class, Answers.builderAnswer());
    when(mSharedPreferences.edit()).thenReturn(mEditor);
    when(mGson.toJson(any())).thenReturn("someFakeJson");
  }

  void verifyPrefDidntExist(TypedKey key) {
    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).contains(key.getKeyName().toString());
    verifyNoMoreInteractions(mSharedPreferences, mEditor, mGson);
  }

  void verifyPrefWasRemoved(TypedKey key) {
    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mEditor).remove(key.getKeyName().toString());
    inOrder.verify(mEditor).apply();
    verifyNoMoreInteractions(mSharedPreferences, mEditor, mGson);
  }

  <T> void setupPrefExists(TypedKey<T> key, T expectedValue) {
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getString(keyName, null)).thenReturn("someFakeJson");
    when(mGson.fromJson("someFakeJson", key.getObjectType())).thenReturn(expectedValue);
  }

  <T> void verifyPrefExisted(TypedKey<T> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getString(keyName, null);
    inOrder.verify(mGson).fromJson("someFakeJson", key.getObjectType());
    verifyNoMoreInteractions(mSharedPreferences, mEditor, mGson);
  }

  <T> void verifyPrefWasSet(TypedKey<T> key, T expectedValue) {
    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mGson).toJson(expectedValue);
    inOrder.verify(mEditor).putString(key.getKeyName().toString(), "someFakeJson");
    inOrder.verify(mEditor).apply();
    verifyNoMoreInteractions(mSharedPreferences, mEditor, mGson);
  }

  void setupBooleanExists(TypedKey<Boolean> key, boolean expectedValue) {
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getBoolean(eq(keyName), anyBoolean())).thenReturn(expectedValue);
  }

  void verifyBooleanExisted(TypedKey<Boolean> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getBoolean(eq(keyName), anyBoolean());
    verifyNoMoreInteractions(mSharedPreferences, mEditor, mGson);
  }

  void verifyBooleanWasSet(TypedKey<Boolean> key, boolean expectedValue) {
    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mEditor).putBoolean(key.getKeyName().toString(), expectedValue);
    inOrder.verify(mEditor).commit();
    verifyNoMoreInteractions(mSharedPreferences, mEditor, mGson);
  }

  void setupFloatExists(TypedKey<Float> key, float expectedValue) {
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getFloat(eq(keyName), anyFloat())).thenReturn(expectedValue);
  }

  void verifyFloatExisted(TypedKey<Float> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getFloat(eq(keyName), anyFloat());
    verifyNoMoreInteractions(mSharedPreferences, mEditor, mGson);
  }

  void verifyFloatWasSet(TypedKey<Float> key, float expectedValue) {
    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mEditor).putFloat(key.getKeyName().toString(), expectedValue);
    inOrder.verify(mEditor).commit();
    verifyNoMoreInteractions(mSharedPreferences, mEditor, mGson);
  }

  void setupIntegerExists(TypedKey<Integer> key, int expectedValue) {
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getInt(eq(keyName), anyInt())).thenReturn(expectedValue);
  }

  void verifyIntegerExisted(TypedKey<Integer> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getInt(eq(keyName), anyInt());
    verifyNoMoreInteractions(mSharedPreferences, mEditor, mGson);
  }

  void verifyIntegerWasSet(TypedKey<Integer> key, int expectedValue) {
    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mEditor).putInt(key.getKeyName().toString(), expectedValue);
    inOrder.verify(mEditor).commit();
    verifyNoMoreInteractions(mSharedPreferences, mEditor, mGson);
  }

  void setupLongExists(TypedKey<Long> key, long expectedValue) {
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getLong(eq(keyName), anyLong())).thenReturn(expectedValue);
  }

  void verifyLongExisted(TypedKey<Long> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getLong(eq(keyName), anyLong());
    verifyNoMoreInteractions(mSharedPreferences, mEditor, mGson);
  }

  void verifyLongWasSet(TypedKey<Long> key, long expectedValue) {
    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mEditor).putLong(key.getKeyName().toString(), expectedValue);
    inOrder.verify(mEditor).commit();
    verifyNoMoreInteractions(mSharedPreferences, mEditor, mGson);
  }

  void setupStringExists(TypedKey<String> key, String expectedValue) {
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getString(eq(keyName), nullable(String.class))).thenReturn(expectedValue);
  }

  void verifyStringExisted(TypedKey<String> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getString(eq(keyName), nullable(String.class));
    verifyNoMoreInteractions(mSharedPreferences, mEditor, mGson);
  }

  void verifyStringWasSet(TypedKey<String> key, String expectedValue) {
    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mEditor).putString(key.getKeyName().toString(), expectedValue);
    inOrder.verify(mEditor).commit();
    verifyNoMoreInteractions(mSharedPreferences, mEditor, mGson);
  }

  void setupDoubleExists(TypedKey<Double> key, double expectedValue) {
    long doubleBits = Double.doubleToRawLongBits(expectedValue);
    String keyName = key.getKeyName().toString();
    when(mSharedPreferences.contains(keyName)).thenReturn(true);
    when(mSharedPreferences.getLong(eq(keyName), anyLong())).thenReturn(doubleBits);
  }

  void verifyDoubleExisted(TypedKey<Double> key) {
    String keyName = key.getKeyName().toString();
    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor, mGson);
    inOrder.verify(mSharedPreferences).contains(keyName);
    inOrder.verify(mSharedPreferences).getLong(eq(keyName), anyLong());
    verifyNoMoreInteractions(mSharedPreferences, mEditor, mGson);
  }

  void verifyDoubleWasSet(TypedKey<Double> key, double expectedValue) {
    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor, mGson);
    long doubleBits = Double.doubleToRawLongBits(expectedValue);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mEditor).putLong(key.getKeyName().toString(), doubleBits);
    inOrder.verify(mEditor).commit();
    verifyNoMoreInteractions(mSharedPreferences, mEditor, mGson);
  }
}
