package com.episode6.hackit.typed.preferences;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import com.episode6.hackit.typed.core.util.Supplier;
import com.episode6.hackit.typed.preferences.cache.LruObjectCache;
import com.episode6.hackit.typed.preferences.cache.ObjectCache;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.annotation.Nullable;

/**
 * Class to assist in wrapping existing instances of {@link SharedPreferences}
 */
public class TypedPrefWrapper {

  private static Supplier<Gson> sGsonSupplier = new Supplier<Gson>() {
    @Override
    public Gson get() {
      return new GsonBuilder()
          .enableComplexMapKeySerialization()
          .create();
    }
  };

  private static Supplier<ObjectCache> sCacheSupplier = new Supplier<ObjectCache>() {
    @TargetApi(12)
    @Override
    public ObjectCache get() {
      return new LruObjectCache(25);
    }
  };

  static void setDefaults(
      @Nullable Supplier<Gson> gsonSupplier,
      @Nullable Supplier<ObjectCache> cacheSupplier) {

    if (gsonSupplier != null) {
      sGsonSupplier = gsonSupplier;
    }
    if (cacheSupplier != null) {
      sCacheSupplier = cacheSupplier;
    }
  }

  /**
   * Wrap an existing instance of {@link SharedPreferences} using the default
   * gson and cache suppliers
   * @param sharedPreferences The {@link SharedPreferences} to wrap
   * @return a {@link TypedPrefs} object that wraps the supplied {@link SharedPreferences} object
   */
  public static TypedPrefs wrapSharedPreferences(SharedPreferences sharedPreferences) {
    return wrapSharedPreferences(sharedPreferences, sCacheSupplier.get());
  }

  /**
   * Wrap an existing instance of {@link SharedPreferences} and provide a specific ObjectCache to use
   * @param sharedPreferences The {@link SharedPreferences} to wrap
   * @return a {@link TypedPrefs} object that wraps the supplied {@link SharedPreferences} object
   */
  public static TypedPrefs wrapSharedPreferences(
      SharedPreferences sharedPreferences,
      @Nullable ObjectCache cache) {
    return new TypedPrefsImpl(
        sharedPreferences,
        sGsonSupplier,
        cache);
  }
}
