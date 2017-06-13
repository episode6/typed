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
  public boolean contains(ReqBundleKey<?> key) {
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
    T instance = getInternal(key);
    if (instance == null) {
      return key.getDefaultValue();
    }
    return instance;
  }

  @Override
  public <T> T get(ReqBundleKey<T> key) {
    if (!containsInternal(key)) {
      throw new MissingPropertyException(key);
    }
    T instance = getInternal(key);
    if (instance == null) {
      throw new MissingPropertyException(key);
    }
    return instance;
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
  public <T> TypedBundle put(ReqBundleKey<T> key, T value) {
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
  public <T> TypedBundle remove(ReqBundleKey<?> key) {
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

  private void removeInternal(TypedKey<?> key) {
    mDelegate.remove(key.getKeyName().toString());
  }

  @SuppressWarnings("unchecked")
  private @Nullable <T> T getInternal(AbstractBundleKey<T> key) {
    final String keyName = key.getKeyName().toString();
    final @Nullable BundleTranslator translator = key.getTranslator();
    if (translator != null) {
      return (T) translator.getFromBundle(mDelegate, keyName);
    } else {
      String translation = mDelegate.getString(keyName);
      return mGsonSupplier.get().fromJson(translation, key.getObjectType());
    }
  }

  private <T> void putInternal(AbstractBundleKey<T> key, T value) {
    final String keyName = key.getKeyName().toString();
    final @Nullable BundleTranslator translator = key.getTranslator();
    if (translator != null) {
      translator.writeToBundle(mDelegate, keyName, value);
    } else {
      String translation = mGsonSupplier.get().toJson(value, key.getObjectType());
      mDelegate.putString(keyName, translation);
    }
  }
}
