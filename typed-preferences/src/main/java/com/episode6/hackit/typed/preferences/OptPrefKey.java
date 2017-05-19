package com.episode6.hackit.typed.preferences;

import com.episode6.hackit.typed.core.TypedKey;
import com.episode6.hackit.typed.core.TypedKeyName;

import java.lang.reflect.Type;

/**
 * A key representing an optional or nullable preference in {@link android.content.SharedPreferences}.
 * An {@link OptPrefKey} has no default value because its default value is null.
 */
public class OptPrefKey<V> implements TypedKey<V> {

  private final TypedKeyName mKeyName;
  private final Type mObjectType;

  OptPrefKey(TypedKeyName keyName, Type objectType) {
    mKeyName = keyName;
    mObjectType = objectType;
  }

  @Override
  public TypedKeyName getKeyName() {
    return mKeyName;
  }

  @Override
  public Type getObjectType() {
    return mObjectType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    OptPrefKey<?> that = (OptPrefKey<?>) o;

    if (!mKeyName.equals(that.mKeyName)) return false;
    return mObjectType.equals(that.mObjectType);
  }

  @Override
  public int hashCode() {
    int result = mKeyName.hashCode();
    result = 31 * result + mObjectType.hashCode();
    return result;
  }
}
