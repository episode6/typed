package com.episode6.hackit.typed.preferences;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.episode6.hackit.typed.core.util.DefaultGsonSupplier;
import com.episode6.hackit.typed.core.util.Supplier;
import com.google.gson.Gson;

import javax.annotation.Nullable;

/**
 * Interface for accessing and modifying data stored in android's {@link android.content.SharedPreferences} using
 * typed keys.
 */
public interface TypedPrefs {

  /**
   * Offers static methods to Wrap android's SharedPreferences as TypedPrefs
   */
  class Wrap {

    /**
     * Wrap this context's default {@link SharedPreferences} with a TypedPrefs implementation.
     * @param context android Context
     * @return An implementation of TypedPrefs wrapping your app's default shared preferences file.
     */
    public static TypedPrefs defaultSharedPrefs(Context context) {
      return sharedPrefs(PreferenceManager.getDefaultSharedPreferences(context));
    }

    /**
     * Wrap an existing instance of android's {@link SharedPreferences} with a TypedPrefs implementation
     * @param sharedPreferences The {@link SharedPreferences} instance to Wrap
     * @return An implementation of TypedPrefs wrapping the supplied instance of SharedPreferences
     */
    public static TypedPrefs sharedPrefs(SharedPreferences sharedPreferences) {
      return sharedPrefs(sharedPreferences, new DefaultGsonSupplier());
    }

    /**
     * Wrap an existing instance of android's {@link SharedPreferences} with a TypedPrefs implementation and
     * uses the provided Gson supplied to translate non-primitive objects.
     * @param sharedPreferences The {@link SharedPreferences} instance to Wrap
     * @param gsonSupplier A supplier for a {@link Gson} instance, which will be used to translate
     *                     non-primitive objects
     * @return An implementation of TypedPrefs wrapping the supplied instance of SharedPreferences
     */
    public static TypedPrefs sharedPrefs(SharedPreferences sharedPreferences, Supplier<Gson> gsonSupplier) {
      return new TypedPrefsImpl(sharedPreferences, gsonSupplier);
    }
  }

  /**
   * Get the value for a given {@link PrefKey}
   * @param prefKey The key to fetch a value for
   * @param <T> The type being returned
   * @return The value stored in {@link android.content.SharedPreferences} or the key's default value (if its not set)
   */
  <T> T get(PrefKey<T> prefKey);

  /**
   * Get the value for a given {@link OptPrefKey}
   * @param prefKey The key to fetch a value for
   * @param <T> The type being returned
   * @return The value stored in {@link android.content.SharedPreferences} or null (if its not set)
   */
  @Nullable <T> T get(OptPrefKey<T> prefKey);

  /**
   * Check if a given {@link PrefKey} has been set in {@link android.content.SharedPreferences}
   * @param prefKey The key to check for existence of
   * @return true if the key exists in {@link android.content.SharedPreferences}, false otherwise
   */
  boolean contains(PrefKey<?> prefKey);

  /**
   * Check if a given {@link PrefKey} has been set in {@link android.content.SharedPreferences}
   * @param prefKey The key to check for existence of
   * @return true if the key exists in {@link android.content.SharedPreferences}, false otherwise
   */
  boolean contains(OptPrefKey<?> prefKey);

  /**
   * @return a {@link TypedPrefs.Editor} to edit the {@link android.content.SharedPreferences}
   */
  Editor edit();

  /**
   * Interface used for modifying values in a {@link TypedPrefs} object
   */
  interface Editor {

    /**
     * Commit your preferences changes back from this Editor to the {@link TypedPrefs} object it is editing.
     * @see android.content.SharedPreferences.Editor#apply()
     */
    @TargetApi(9)
    void apply();

    /**
     * Commit your preferences changes back from this Editor to the {@link TypedPrefs} object it is editing.
     * @see android.content.SharedPreferences.Editor#commit()
     */
    void commit();

    /**
     * Set a value in this preference editor, to be written back once {@link #commit()} or {@link #apply()} are called.
     * This method will throw a NullPointerException if instance is null.
     * @param prefKey The key for which to write this data
     * @param instance The object (value) to write
     * @param <T> The type of object being written.
     * @return this {@link Editor}
     */
    <T> Editor put(PrefKey<T> prefKey, T instance);

    /**
     * Set a value in this preference editor, to be written back once {@link #commit()} or {@link #apply()} are called.
     * Passing a null value for instance will result in the key being removed from the {@link TypedPrefs} object.
     * @param prefKey The key for which to write this data
     * @param instance The object (value) to write
     * @param <T> The type of object being written.
     * @return this {@link Editor}
     */
    <T> Editor put(OptPrefKey<T> prefKey, @Nullable T instance);

    /**
     * Mark in the editor to remove all values from the preferences. Once commit is called, the only remaining
     * preferences will be any that you have defined in this editor.
     *
     * Note that when committing back to the preferences, the clear is done first, regardless of whether you called
     * clear before or after put methods on this editor.
     * @return this {@link Editor}
     */
    Editor clear();

    /**
     * Mark in the editor that a preference value should be removed, which will be done in the actual preferences once
     * {@link #commit()} or {@link #apply()} is called
     * @param prefKey The key to remove
     * @return this {@link Editor}
     */
    Editor remove(PrefKey<?> prefKey);

    /**
     * Mark in the editor that a preference value should be removed, which will be done in the actual preferences once
     * {@link #commit()} or {@link #apply()} is called
     * @param prefKey The key to remove
     * @return this {@link Editor}
     */
    Editor remove(OptPrefKey<?> prefKey);
  }
}
