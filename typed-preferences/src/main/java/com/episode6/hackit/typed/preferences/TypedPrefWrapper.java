package com.episode6.hackit.typed.preferences;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.util.LruCache;
import com.episode6.hackit.typed.core.TypedKey;
import com.episode6.hackit.typed.core.util.Supplier;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.annotation.Nullable;

/**
 *
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

  private static Supplier<LruCache<TypedKey, Object>> sCacheSupplier = new Supplier<LruCache<TypedKey, Object>>() {
    @TargetApi(12)
    @Override
    public LruCache<TypedKey, Object> get() {
      return new LruCache<>(25);
    }
  };

  static void setDefaults(
      @Nullable Supplier<Gson> gsonSupplier,
      @Nullable Supplier<LruCache<TypedKey, Object>> cacheSupplier) {

    if (gsonSupplier != null) {
      sGsonSupplier = gsonSupplier;
    }
    if (cacheSupplier != null) {
      sCacheSupplier = cacheSupplier;
    }
  }

  public static TypedPrefs wrapSharedPreferences(SharedPreferences sharedPreferences) {
    return wrapSharedPreferences(sharedPreferences, sCacheSupplier.get());
  }

  public static TypedPrefs wrapSharedPreferences(
      SharedPreferences sharedPreferences,
      @Nullable LruCache<TypedKey, Object> cache) {
    return new TypedPrefsImpl(
        sharedPreferences,
        sGsonSupplier,
        cache);
  }
}
