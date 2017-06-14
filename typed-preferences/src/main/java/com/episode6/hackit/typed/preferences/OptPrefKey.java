package com.episode6.hackit.typed.preferences;

import com.episode6.hackit.typed.core.TypedKeyName;

import java.lang.reflect.Type;

/**
 * A key representing an optional or nullable preference in {@link android.content.SharedPreferences}.
 * An {@link OptPrefKey} has no default value because its default value is null.
 */
public final class OptPrefKey<V> extends AbstractPrefKey<V> {

  OptPrefKey(TypedKeyName keyName, Type objectType) {
    super(keyName, objectType);
  }
}
