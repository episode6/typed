package com.episode6.hackit.typed.bundles;

import android.os.Bundle;
import com.episode6.hackit.typed.core.TypedKey;
import com.episode6.hackit.typed.core.util.Preconditions;
import com.episode6.hackit.typed.core.util.Supplier;
import com.episode6.hackit.typed.core.util.Suppliers;
import com.google.gson.Gson;

import javax.annotation.Nullable;

/**
 *
 */
public class TypedBundleImpl implements TypedBundle {

  private final Supplier<Gson> mGsonSupplier;
  private final Bundle mDelegate;

  public TypedBundleImpl(Supplier<Gson> gsonSupplier, Bundle delegate) {
    mGsonSupplier = Suppliers.memoize(gsonSupplier);
    mDelegate = delegate;
  }

  @Override
  public boolean contains(BundleKey<?> key) {
    return containsInternal(key);
  }

  @Override
  public boolean contains(OptBundleKey<?> key) {
    return containsInternal(key);
  }

  @Override
  public <T> T get(BundleKey<T> key) {
    if (!containsInternal(key)) {
      return key.getDefaultValue();
    }
    return getInternal(key);
  }

  @Nullable
  @Override
  public <T> T get(OptBundleKey<T> key) {
    if (!containsInternal(key)) {
      return null;
    }
    return getInternal(key);
  }

  @Override
  public <T> TypedBundle put(BundleKey<T> key, T value) {
    putInternal(key, Preconditions.checkNotNull(value));
    return this;
  }

  @Override
  public <T> TypedBundle put(OptBundleKey<T> key, @Nullable T value) {
    if (value == null) {
      removeInternal(key);
    } else {
      putInternal(key, value);
    }
    return this;
  }

  @Override
  public <T> TypedBundle remove(BundleKey<?> key) {
    removeInternal(key);
    return this;
  }

  @Override
  public <T> TypedBundle remove(OptBundleKey<?> key) {
    removeInternal(key);
    return this;
  }

  @Override
  public Bundle asBundle() {
    return mDelegate;
  }

  private boolean containsInternal(TypedKey<?> key) {
    return mDelegate.containsKey(key.getKeyName().toString());
  }

  private <T> T getInternal(TypedKey<T> key) {
    return null;
  }

  private <T> void putInternal(TypedKey<T> key, T value) {

  }

  private void removeInternal(TypedKey<?> key) {
    mDelegate.remove(key.getKeyName().toString());
  }
}
