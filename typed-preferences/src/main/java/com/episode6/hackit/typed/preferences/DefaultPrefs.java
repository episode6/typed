package com.episode6.hackit.typed.preferences;

import android.content.Context;
import android.preference.PreferenceManager;

import javax.annotation.Nullable;

/**
 *
 */
public class DefaultPrefs {

  private static TypedPrefs sDefaultTypedPrefs = null;

  static void init(Context context) {
     sDefaultTypedPrefs = TypedPrefWrapper.wrapSharedPreferences(PreferenceManager.getDefaultSharedPreferences(context));
  }

  public static TypedPrefs get() {
    if (sDefaultTypedPrefs == null) {
      throw new RuntimeException("Tried to get default TypedPrefs before initialization.");
    }
    return sDefaultTypedPrefs;
  }

  public static <T> T get(PrefKey<T> prefKey) {
    return get().get(prefKey);
  }

  public static @Nullable <T> T get(OptPrefKey<T> prefKey) {
    return get().get(prefKey);
  }

  public static boolean contains(PrefKey<?> prefKey) {
    return get().contains(prefKey);
  }

  public static boolean contains(OptPrefKey<?> prefKey) {
    return get().contains(prefKey);
  }

  public static TypedPrefs.Editor edit() {
    return get().edit();
  }
}
