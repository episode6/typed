package com.episode6.hackit.typed.preferences;

import android.annotation.TargetApi;

import javax.annotation.Nullable;

/**
 *
 */
public interface TypedPrefs {

  <T> T get(PrefKey<T> prefKey);
  @Nullable <T> T get(OptPrefKey<T> prefKey);

  boolean contains(PrefKey<?> prefKey);
  boolean contains(OptPrefKey<?> prefKey);

  Editor edit();

  interface Editor {
    @TargetApi(9)
    void apply();

    void commit();

    <T> Editor put(PrefKey<T> prefKey, T instance);
    <T> Editor put(OptPrefKey<T> prefKey, @Nullable T instance);

    Editor clear();
    Editor remove(PrefKey<?> prefKey);
    Editor remove(OptPrefKey<?> prefKey);
  }
}
