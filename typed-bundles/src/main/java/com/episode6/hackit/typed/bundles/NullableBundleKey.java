package com.episode6.hackit.typed.bundles;

import com.episode6.hackit.typed.core.TypedKey;
import com.episode6.hackit.typed.core.TypedKeyName;

import java.lang.reflect.Type;

/**
 *
 */
public class NullableBundleKey<V> implements TypedKey<V> {

  private final TypedKeyName mKeyName;
  private final Type mObjectType;

  NullableBundleKey(TypedKeyName keyName, Type objectType) {
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
