package com.episode6.hackit.typed.bundles;

import com.episode6.hackit.typed.core.TypedKey;
import com.episode6.hackit.typed.core.TypedKeyName;

import java.lang.reflect.Type;

/**
 *
 */
public class OptBundleKey<V> implements TypedKey<V> {

  private final TypedKeyName mKeyName;
  private final Type mObjectType;

  OptBundleKey(TypedKeyName keyName, Type objectType) {
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
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    OptBundleKey<?> that = (OptBundleKey<?>) o;

    if (!mKeyName.equals(that.mKeyName)) {
      return false;
    }
    return mObjectType.equals(that.mObjectType);
  }

  @Override
  public int hashCode() {
    int result = mKeyName.hashCode();
    result = 31 * result + mObjectType.hashCode();
    return result;
  }
}
