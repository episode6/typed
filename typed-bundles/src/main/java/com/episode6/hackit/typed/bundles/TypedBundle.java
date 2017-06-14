package com.episode6.hackit.typed.bundles;

import android.content.Intent;
import android.os.Bundle;

import javax.annotation.Nullable;

/**
 * Interface for a bundle with typed keys.
 */
public interface TypedBundle {

  /**
   * Interface for an object that creates TypedBundles.
   */
  interface Factory {
    TypedBundle create();
    TypedBundle wrap(Bundle bundle);
    TypedBundle wrapNullable(@Nullable Bundle bundle);
    TypedBundle intentExtras(@Nullable Intent intent);
  }

  boolean contains(BundleKey<?> key);
  boolean contains(ReqBundleKey<?> key);
  boolean contains(OptBundleKey<?> key);

  <T> T get(BundleKey<T> key);
  <T> T get(ReqBundleKey<T> key);
  @Nullable <T> T get(OptBundleKey<T> key);

  <T> TypedBundle put(BundleKey<T> key, T value);
  <T> TypedBundle put(ReqBundleKey<T> key, T value);
  <T> TypedBundle put(OptBundleKey<T> key, @Nullable T value);

  <T> TypedBundle remove(BundleKey<?> key);
  <T> TypedBundle remove(ReqBundleKey<?> key);
  <T> TypedBundle remove(OptBundleKey<?> key);

  Bundle asBundle();
}
