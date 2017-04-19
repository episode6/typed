package com.episode6.hackit.typed.preferences;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.LruCache;
import com.episode6.hackit.typed.core.TypedKey;
import com.episode6.hackit.typed.core.util.InstanceSupplier;
import com.episode6.hackit.typed.core.util.Supplier;
import com.episode6.hackit.typed.core.util.Suppliers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 */
public class TypedPrefManager {
  private static Supplier<Gson> sGsonSupplier = Suppliers.memoize(new Supplier<Gson>() {
    @Override
    public Gson get() {
      return new GsonBuilder()
          .enableComplexMapKeySerialization()
          .create();
    }
  });

  private static Supplier<LruCache<TypedKey, Object>> sCacheSupplier = new Supplier<LruCache<TypedKey, Object>>() {
    @TargetApi(12)
    @Override
    public LruCache<TypedKey, Object> get() {
      return new LruCache<>(50);
    }
  };

  public static void useCustomGson(Gson gson) {
    sGsonSupplier = new InstanceSupplier<>(gson);
  }

  public static void useCustomGson(Supplier<Gson> gsonSupplier) {
    sGsonSupplier = Suppliers.memoize(gsonSupplier);
  }

  public static void useCustomCache(Supplier<LruCache<TypedKey, Object>> cacheSupplier) {
    sCacheSupplier = cacheSupplier;
  }

  public static void disableCache() {
    sCacheSupplier = new Supplier<LruCache<TypedKey, Object>>() {
      @Override
      public LruCache<TypedKey, Object> get() {
        return null;
      }
    };
  }

  public static TypedPrefs getDefaultTypedPreferences(Context context) {
    return new TypedPrefsImpl(
        PreferenceManager.getDefaultSharedPreferences(context),
        sGsonSupplier,
        sCacheSupplier.get());
  }

  public static TypedPrefs wrapSharedPreferences(SharedPreferences sharedPreferences) {
    return new TypedPrefsImpl(
        sharedPreferences,
        sGsonSupplier,
        sCacheSupplier.get());
  }
}
