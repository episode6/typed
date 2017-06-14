package com.episode6.hackit.typed.preferences;

import com.episode6.hackit.typed.core.TypedKeyName;
import com.episode6.hackit.typed.core.util.Supplier;

import java.lang.reflect.Type;

/**
 * A Key representing a preference in {@link android.content.SharedPreferences}
 * A {@link PrefKey} requires a default value that is provided when its underlying preference is unset.
 */
public final class PrefKey<V> extends AbstractPrefKey<V> {
  private final Supplier<V> mDefaultValueSupplier;

  PrefKey(TypedKeyName keyName, Type objectType, Supplier<V> defaultValueSupplier) {
    super(keyName, objectType);
    mDefaultValueSupplier = defaultValueSupplier;
  }

  V getDefaultValue() {
    return mDefaultValueSupplier.get();
  }
}
