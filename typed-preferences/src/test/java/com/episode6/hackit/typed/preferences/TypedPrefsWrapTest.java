package com.episode6.hackit.typed.preferences;

import android.content.Context;
import android.preference.PreferenceManager;
import com.episode6.hackit.mockspresso.Mockspresso;
import com.episode6.hackit.typed.core.util.Supplier;
import com.episode6.hackit.typed.testing.Rules;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.MockPolicy;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

/**
 * Tests {@link TypedPrefs.wrap}
 */
@MockPolicy(SharedTestResources.MockPolicy.class)
@PrepareForTest(PreferenceManager.class)
public class TypedPrefsWrapTest {

  final SharedTestResources t = new SharedTestResources();
  @Rule public final Mockspresso.Rule mockspresso = Rules.mockspressoBuilder()
      .testResources(t)
      .buildRule();

  private static final PrefKey<Integer> INT_PREF = PrefNamespace.ROOT.key(Integer.class)
      .named("testInt")
      .buildWithDefault(3);
  private static final OptPrefKey<TestClass> CLASS_PREF = PrefNamespace.ROOT
      .key(TestClass.class)
      .named("simpleNullTestPref")
      .buildOptional();

  @Mock Context mContext;
  @Mock Supplier<Gson> mGsonSupplier;

  @Before
  public void setup() {
    mockStatic(PreferenceManager.class);
    when(PreferenceManager.getDefaultSharedPreferences(mContext)).thenReturn(t.mSharedPreferences);
    when(mGsonSupplier.get()).thenReturn(t.mGson);
  }

  @Test
  public void testDefaultWrap() {
    TypedPrefs prefs = TypedPrefs.wrap.defaultSharedPrefs(mContext);
    int value = prefs.get(INT_PREF);


    verifyStatic();
    PreferenceManager.getDefaultSharedPreferences(mContext);

    t.verifyPrefDidntExist(INT_PREF);
    assertThat(value).isEqualTo(3);
  }

  @Test
  public void testSimpleWrap() {
    TypedPrefs prefs = TypedPrefs.wrap.sharedPrefs(t.mSharedPreferences);
    int value = prefs.get(INT_PREF);

    t.verifyPrefDidntExist(INT_PREF);
    assertThat(value).isEqualTo(3);
  }

  @Test
  public void testWrapWithGson() {
    TestClass expected = new TestClass();
    t.setupPrefExists(CLASS_PREF, expected);

    TypedPrefs prefs = TypedPrefs.wrap.sharedPrefs(t.mSharedPreferences, mGsonSupplier);
    TestClass result = prefs.get(CLASS_PREF);

    t.verifyPrefExisted(CLASS_PREF);
    assertThat(result).isEqualTo(expected);
  }

  static class TestClass {}
}
