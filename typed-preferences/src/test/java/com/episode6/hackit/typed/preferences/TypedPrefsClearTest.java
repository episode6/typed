package com.episode6.hackit.typed.preferences;

import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.typed.testing.Rules;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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

  final SharedTestResources t = new SharedTestResources();
  @Rule public final Mockspresso.Rule mockspresso = Rules.mockspressoBuilder()
      .testResources(t)
      .buildRule();

  @Test
  public void testClearOrderWithCommit() {
    t.mTypedPrefs.edit()
        .put(BOOL_PREF, false)
        .clear()
        .put(FLOAT_PREF, 10f)
        .commit();


    InOrder inOrder = Mockito.inOrder(t.mSharedPreferences, t.mEditor);
    inOrder.verify(t.mSharedPreferences).edit();
    inOrder.verify(t.mEditor).clear();
    inOrder.verify(t.mEditor).commit();

    verify(t.mEditor).putBoolean(BOOL_PREF.getKeyName().toString(), false);
    verify(t.mEditor).putFloat(FLOAT_PREF.getKeyName().toString(), 10f);
    verifyNoMoreInteractions(t.mSharedPreferences, t.mEditor);
  }

  @Test
  public void testClearOrderWithApply() {
    t.mTypedPrefs.edit()
        .put(INT_NULL_PREF, 3)
        .clear()
        .put(LONG_NULL_PREF, 12L)
        .apply();

    InOrder inOrder = Mockito.inOrder(t.mSharedPreferences, t.mEditor);
    inOrder.verify(t.mSharedPreferences).edit();
    inOrder.verify(t.mEditor).clear();
    inOrder.verify(t.mEditor).apply();

    verify(t.mEditor).putInt(INT_NULL_PREF.getKeyName().toString(), 3);
    verify(t.mEditor).putLong(LONG_NULL_PREF.getKeyName().toString(), 12L);
    verifyNoMoreInteractions(t.mSharedPreferences, t.mEditor);
  }
}
