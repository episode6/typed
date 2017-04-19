package com.episode6.hackit.typed.preferences;

import android.content.Context;
import android.preference.PreferenceManager;

import javax.annotation.Nullable;

/**
 * Convenience class for statically accessing the {@link TypedPrefs} wrapper for
 * {@link PreferenceManager#getDefaultSharedPreferences(Context)}
 *
 * To use any part of this class, you must first initialize it via the {@link TypedPrefInitializer}
 */
public class DefaultPrefs {

  private static TypedPrefs sDefaultTypedPrefs = null;

  static void init(Context context) {
     sDefaultTypedPrefs = TypedPrefWrapper.wrapSharedPreferences(PreferenceManager.getDefaultSharedPreferences(context));
  }

  /**
   * @return the default {@link TypedPrefs} for this app
   */
  public static TypedPrefs get() {
    if (sDefaultTypedPrefs == null) {
      throw new RuntimeException("Tried to get default TypedPrefs before initialization.");
    }
    return sDefaultTypedPrefs;
  }

  /**
   * Get the value for a given {@link PrefKey}
   * @param prefKey The key to fetch a value for
   * @param <T> The type being returned
   * @return The value stored in {@link android.content.SharedPreferences} or the key's default value (if its not set)
   */
  public static <T> T get(PrefKey<T> prefKey) {
    return get().get(prefKey);
  }

  /**
   * Get the value for a given {@link OptPrefKey}
   * @param prefKey The key to fetch a value for
   * @param <T> The type being returned
   * @return The value stored in {@link android.content.SharedPreferences} or null (if its not set)
   */
  public static @Nullable <T> T get(OptPrefKey<T> prefKey) {
    return get().get(prefKey);
  }

  /**
   * Check if a given {@link PrefKey} has been set in {@link android.content.SharedPreferences}
   * @param prefKey The key to check for existence of
   * @return true if the key exists in {@link android.content.SharedPreferences}, false otherwise
   */
  public static boolean contains(PrefKey<?> prefKey) {
    return get().contains(prefKey);
  }

  /**
   * Check if a given {@link PrefKey} has been set in {@link android.content.SharedPreferences}
   * @param prefKey The key to check for existence of
   * @return true if the key exists in {@link android.content.SharedPreferences}, false otherwise
   */
  public static boolean contains(OptPrefKey<?> prefKey) {
    return get().contains(prefKey);
  }

  /**
   * @return a {@link TypedPrefs.Editor} to edit the {@link android.content.SharedPreferences}
   */
  public static TypedPrefs.Editor edit() {
    return get().edit();
  }
}
