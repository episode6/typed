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
    /**
     * @return a new {@link TypedBundle}
     */
    TypedBundle create();

    /**
     * Wrap a (non-null) {@link Bundle} with a {@link TypedBundle}
     * @param bundle The bundle to wrap
     * @return a new {@link TypedBundle} that wraps the provided bundle
     */
    TypedBundle wrap(Bundle bundle);

    /**
     * Wrap a nullable {@link Bundle} with a {@link TypedBundle} OR
     * create a new {@link TypedBundle} if the provided bundle is null
     * @param bundle The bundle to wrap
     * @return a new {@link TypedBundle} that wraps the provided bundle
     * if it's non-null
     */
    TypedBundle wrapNullable(@Nullable Bundle bundle);

    /**
     * Wrap the provided intent's extras with a {@link TypedBundle} if
     * those extras are non-null
     * @param intent The intent to getExtras from
     * @return A new {@link TypedBundle} that wraps the intent's extras
     * if the intent and its extras bundle is non-null.
     */
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
