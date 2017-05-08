package com.episode6.hackit.typed.preferences;

import android.content.SharedPreferences;
import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.mockspresso.annotation.RealObject;
import com.episode6.hackit.typed.testing.Answers;
import com.episode6.hackit.typed.testing.Rules;
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
      .buildOptional();
  private static final OptPrefKey<Long> LONG_NULL_PREF = PREF_NAMESPACE.key(Long.class)
      .named("testNullLong")
      .buildOptional();

  @Rule public final Mockspresso.Rule mockspresso = Rules.mockspresso();

  @Mock SharedPreferences mSharedPreferences;
  /*Mock*/ SharedPreferences.Editor mEditor;

  @RealObject(implementation = TypedPrefsImpl.class) TypedPrefs mTypedPrefs;

  @Before
  public void setup() {
    mEditor = mock(SharedPreferences.Editor.class, Answers.builderAnswer());
    when(mSharedPreferences.edit()).thenReturn(mEditor);
  }

  @Test
  public void testClearOrderWithCommit() {
    mTypedPrefs.edit()
        .put(BOOL_PREF, false)
        .clear()
        .put(FLOAT_PREF, 10f)
        .commit();


    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mEditor).clear();
    inOrder.verify(mEditor).commit();

    verify(mEditor).putBoolean(BOOL_PREF.getKeyName().toString(), false);
    verify(mEditor).putFloat(FLOAT_PREF.getKeyName().toString(), 10f);
    verifyNoMoreInteractions(mSharedPreferences, mEditor);
  }

  @Test
  public void testClearOrderWithApply() {
    mTypedPrefs.edit()
        .put(INT_NULL_PREF, 3)
        .clear()
        .put(LONG_NULL_PREF, 12L)
        .apply();

    InOrder inOrder = Mockito.inOrder(mSharedPreferences, mEditor);
    inOrder.verify(mSharedPreferences).edit();
    inOrder.verify(mEditor).clear();
    inOrder.verify(mEditor).apply();

    verify(mEditor).putInt(INT_NULL_PREF.getKeyName().toString(), 3);
    verify(mEditor).putLong(LONG_NULL_PREF.getKeyName().toString(), 12L);
    verifyNoMoreInteractions(mSharedPreferences, mEditor);
  }
}
