package com.episode6.hackit.typed.preferences;

import android.content.SharedPreferences;
import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.mockspresso.annotation.RealObject;
import com.episode6.hackit.typed.core.TypedKey;
import com.episode6.hackit.typed.preferences.cache.ObjectCache;
import com.episode6.hackit.typed.testing.Answers;
import com.episode6.hackit.typed.testing.Rules;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

/**
 * Tests {@link TypedPrefsImpl} usage of Editor.clear()
 */
public class TypedPrefsClearTest {

  private static final PrefNamespace PREF_NAMESPACE = PrefNamespace.ROOT.extend("testNamespace").extend("clearTest");

  private static final PrefKey<Boolean> BOOL_PREF = PREF_NAMESPACE.key(Boolean.class)
      .named("testBool")
      .buildWithDefault(true);
  private static final PrefKey<Float> FLOAT_PREF = PREF_NAMESPACE.key(Float.class)
      .named("testFloat")
      .buildWithDefault(1.2f);
  private static final OptPrefKey<Integer> INT_NULL_PREF = PREF_NAMESPACE.key(Integer.class)
      .named("testNullInt")
      .buildNullable();
  private static final OptPrefKey<Long> LONG_NULL_PREF = PREF_NAMESPACE.key(Long.class)
      .named("testNullLong")
      .buildNullable();

  @Rule public final Mockspresso.Rule mockspresso = Rules.mockspresso();

  @Mock SharedPreferences mSharedPreferences;
  /*Mock*/ SharedPreferences.Editor mEditor;
  @Mock ObjectCache mCache;

  @RealObject(implementation = TypedPrefsImpl.class) TypedPrefs mTypedPrefs;
  TypedPrefs mTypedPrefsNoCache;

  @Before
  public void setup() {
    mEditor = mock(SharedPreferences.Editor.class, Answers.builderAnswer());
    when(mSharedPreferences.edit()).thenReturn(mEditor);
    mTypedPrefsNoCache = mockspresso.buildUpon()
        .dependency(ObjectCache.class, null)
        .build()
        .create(TypedPrefsImpl.class);
  }

  @Test
  public void testClearOrderWithCommit() {
    mTypedPrefs.edit()
        .put(BOOL_PREF, false)
        .clear()
        .put(FLOAT_PREF, 10f)
        .commit();


    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor, mCache);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mEditor).clear();
    inOrder.verify(mCache).clear();
    inOrder.verify(mCache, times(2)).put(any(TypedKey.class), any());
    inOrder.verify(mEditor).commit();

    verify(mEditor).putBoolean(BOOL_PREF.getKeyName().toString(), false);
    verify(mEditor).putFloat(FLOAT_PREF.getKeyName().toString(), 10f);
    verifyNoMoreInteractions(mSharedPreferences, mEditor, mCache);
  }

  @Test
  public void testClearOrderWithApply() {
    mTypedPrefs.edit()
        .put(INT_NULL_PREF, 3)
        .clear()
        .put(LONG_NULL_PREF, 12L)
        .apply();

    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor, mCache);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mEditor).clear();
    inOrder.verify(mCache).clear();
    inOrder.verify(mCache, times(2)).put(any(TypedKey.class), any());
    inOrder.verify(mEditor).apply();

    verify(mEditor).putInt(INT_NULL_PREF.getKeyName().toString(), 3);
    verify(mEditor).putLong(LONG_NULL_PREF.getKeyName().toString(), 12L);
    verifyNoMoreInteractions(mSharedPreferences, mEditor, mCache);
  }

  @Test
  public void testNoCacheClearOrderWithCommit() {
    mTypedPrefsNoCache.edit()
        .put(BOOL_PREF, false)
        .clear()
        .put(FLOAT_PREF, 10f)
        .commit();

    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor, mCache);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mEditor).clear();
    inOrder.verify(mEditor).commit();

    verify(mEditor).putBoolean(BOOL_PREF.getKeyName().toString(), false);
    verify(mEditor).putFloat(FLOAT_PREF.getKeyName().toString(), 10f);
    verifyNoMoreInteractions(mSharedPreferences, mEditor, mCache);
  }

  @Test
  public void testNoCacheClearOrderWithApply() {
    mTypedPrefsNoCache.edit()
        .put(INT_NULL_PREF, 3)
        .clear()
        .put(LONG_NULL_PREF, 12L)
        .apply();

    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor, mCache);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mEditor).clear();
    inOrder.verify(mEditor).apply();

    verify(mEditor).putInt(INT_NULL_PREF.getKeyName().toString(), 3);
    verify(mEditor).putLong(LONG_NULL_PREF.getKeyName().toString(), 12L);
    verifyNoMoreInteractions(mSharedPreferences, mEditor, mCache);
  }
}
