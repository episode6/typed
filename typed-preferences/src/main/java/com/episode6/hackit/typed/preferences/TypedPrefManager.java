package com.episode6.hackit.typed.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

  public static void useCustomGson(Gson gson) {
    sGsonSupplier = new InstanceSupplier<>(gson);
  }

  public static void useCustomGson(Supplier<Gson> gsonSupplier) {
    sGsonSupplier = Suppliers.memoize(gsonSupplier);
  }

  public static TypedPreferences getDefaultTypedPreferences(Context context) {
    return new TypedPreferencesImpl(
        PreferenceManager.getDefaultSharedPreferences(context),
        sGsonSupplier);
  }

  public static TypedPreferences wrapSharedPreferences(SharedPreferences sharedPreferences) {
    return new TypedPreferencesImpl(sharedPreferences, sGsonSupplier);
  }
}
