package com.episode6.hackit.typed.preferences;

import com.episode6.hackit.typed.core.TypedKey;
import com.episode6.hackit.typed.core.TypedKeyName;

import java.lang.reflect.Type;

/**
 * Abstract implementation of pref key, contains the basics that every prefkey needs
 */
public abstract class AbstractPrefKey<V> implements TypedKey<V> {

  private final TypedKeyName mKeyName;
  private final Type mObjectType;

  public AbstractPrefKey(TypedKeyName keyName, Type objectType) {
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

    AbstractPrefKey<?> that = (AbstractPrefKey<?>) o;

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
