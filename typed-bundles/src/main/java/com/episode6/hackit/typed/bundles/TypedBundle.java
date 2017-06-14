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

  /**
   * @param key the key to check
   * @return true if the key is present in the bundle, false otherwise
   */
  boolean contains(BundleKey<?> key);

  /**
   * @param key the key to check
   * @return true if the key is present in the bundle, false otherwise
   */
  boolean contains(ReqBundleKey<?> key);

  /**
   * @param key the key to check
   * @return true if the key is present in the bundle, false otherwise
   */
  boolean contains(OptBundleKey<?> key);

  /**
   * Get the value for a given {@link BundleKey}. If the key is not present
   * in the bundle, the default value will be returned.
   * @param key The key to get the value for.
   * @param <T> The type of the key.
   * @return The value for the given key or the default value if it's not present.
   */
  <T> T get(BundleKey<T> key);

  /**
   * Get the value for a given required {@link ReqBundleKey}. If the
   * key is not present in the bundle, a {@link MissingPropertyException}
   * will be thrown.
   * @param key The key to get the value for.
   * @param <T> The type of the key.
   * @return The value for the given key.
   */
  <T> T get(ReqBundleKey<T> key);

  /**
   * Get the value for a given optional {@link OptBundleKey} or null if
   * the key is not present in the bundle.
   * @param key The key to get the value for.
   * @param <T> The type of the key.
   * @return The value for the given key, or null if it's not present.
   */
  @Nullable <T> T get(OptBundleKey<T> key);

  /**
   * Set the value for the given key in the underlying bundle.
   * This method will throw a {@link NullPointerException} if
   * the provided value is null.
   * @param key the key to set a value for
   * @param value the value to set
   * @param <T> the type of the key and value.
   * @return this {@link TypedBundle} (for chaining)
   */
  <T> TypedBundle put(BundleKey<T> key, T value);

  /**
   * Set the value for the given key in the underlying bundle.
   * This method will throw a {@link NullPointerException} if
   * the provided value is null.
   * @param key the key to set a value for
   * @param value the value to set
   * @param <T> the type of the key and value.
   * @return this {@link TypedBundle} (for chaining)
   */
  <T> TypedBundle put(ReqBundleKey<T> key, T value);

  /**
   * Set the value for the given key in the underlying bundle.
   * @param key the key to set a value for
   * @param value the value to set (can be null)
   * @param <T> the type of the key and value.
   * @return this {@link TypedBundle} (for chaining)
   */
  <T> TypedBundle put(OptBundleKey<T> key, @Nullable T value);

  /**
   * Remove this key (and value) from the underlying bundle
   * @param key the key to remove
   * @param <T> the type of the key
   * @return this {@link TypedBundle} (for chaining)
   */
  <T> TypedBundle remove(BundleKey<?> key);

  /**
   * Remove this key (and value) from the underlying bundle
   * @param key the key to remove
   * @param <T> the type of the key
   * @return this {@link TypedBundle} (for chaining)
   */
  <T> TypedBundle remove(ReqBundleKey<?> key);

  /**
   * Remove this key (and value) from the underlying bundle
   * @param key the key to remove
   * @param <T> the type of the key
   * @return this {@link TypedBundle} (for chaining)
   */
  <T> TypedBundle remove(OptBundleKey<?> key);

  /**
   * @return the underlying bundle.
   */
  Bundle asBundle();
}
