package com.episode6.hackit.typed.preferences;

import com.episode6.hackit.typed.core.TypedKey;
import com.episode6.hackit.typed.core.TypedKeyName;

import java.lang.reflect.Type;

/**
 *
 */
public class NullablePrefKey<V> implements TypedKey<V> {

  private final TypedKeyName mKeyName;
  private final Type mObjectType;

  NullablePrefKey(TypedKeyName keyName, Type objectType) {
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
}
