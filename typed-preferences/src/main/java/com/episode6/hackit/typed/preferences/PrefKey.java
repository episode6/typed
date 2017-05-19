package com.episode6.hackit.typed.preferences;

import com.episode6.hackit.typed.core.TypedKey;
import com.episode6.hackit.typed.core.TypedKeyName;
import com.episode6.hackit.typed.core.util.Supplier;

import java.lang.reflect.Type;

/**
 * A Key representing a preference in {@link android.content.SharedPreferences}
 * A {@link PrefKey} requires a default value that is provided when its underlying preference is unset.
 */
public class PrefKey<V> implements TypedKey<V> {
  private final TypedKeyName mKeyName;
  private final Type mObjectType;
  private final Supplier<V> mDefaultValueSupplier;

  PrefKey(TypedKeyName keyName, Type objectType, Supplier<V> defaultValueSupplier) {
    mKeyName = keyName;
    mObjectType = objectType;
    mDefaultValueSupplier = defaultValueSupplier;
  }

  @Override
  public TypedKeyName getKeyName() {
    return mKeyName;
  }

  @Override
  public Type getObjectType() {
    return mObjectType;
  }

  V getDefaultValue() {
    return mDefaultValueSupplier.get();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PrefKey<?> prefKey = (PrefKey<?>) o;

    if (!mKeyName.equals(prefKey.mKeyName)) return false;
    return mObjectType.equals(prefKey.mObjectType);
  }

  @Override
  public int hashCode() {
    int result = mKeyName.hashCode();
    result = 31 * result + mObjectType.hashCode();
    return result;
  }
}
