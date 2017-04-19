package com.episode6.hackit.typed.preferences;

import com.episode6.hackit.typed.core.TypedKey;

import javax.annotation.Nullable;

/**
 *
 */
public interface TypedPrefs {
  <T> T get(PrefKey<T> prefKey);
  @Nullable <T> T get(NullablePrefKey<T> prefKey);

  boolean contains(PrefKey<?> prefKey);
  boolean contains(NullablePrefKey<?> prefKey);

  Editor edit();

  interface Editor {
    void apply();
    void commit();

    <T> Editor put(PrefKey<T> prefKey, T instance);
    <T> Editor put(NullablePrefKey<T> prefKey, @Nullable T instance);

    Editor clear();
    Editor remove(PrefKey<?> prefKey);
    Editor remove(NullablePrefKey<?> prefKey);
  }
}
