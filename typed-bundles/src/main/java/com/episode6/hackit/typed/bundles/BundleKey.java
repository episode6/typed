package com.episode6.hackit.typed.bundles;

import com.episode6.hackit.typed.core.TypedKey;
import com.episode6.hackit.typed.core.TypedKeyName;
import com.episode6.hackit.typed.core.util.Supplier;

import java.lang.reflect.Type;

/**
 *
 */
public class BundleKey<V> implements TypedKey<V> {

  private final TypedKeyName mKeyName;
  private final Type mObjectType;
  private final Supplier<V> mDefaultValueSupplier;

  BundleKey(TypedKeyName keyName, Type objectType, Supplier<V> defaultValueSupplier) {
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

  public V getDefaultValue() {
    return mDefaultValueSupplier.get();
  }
}
