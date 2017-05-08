package com.episode6.hackit.typed.preferences;

import android.content.SharedPreferences;
import com.episode6.hackit.typed.core.util.Supplier;
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

  static void setDefaults(
      @Nullable Supplier<Gson> gsonSupplier) {

    if (gsonSupplier != null) {
      sGsonSupplier = gsonSupplier;
    }
  }

  /**
   * Wrap an existing instance of {@link SharedPreferences} using the default
   * gson and cache suppliers
   * @param sharedPreferences The {@link SharedPreferences} to wrap
   * @return a {@link TypedPrefs} object that wraps the supplied {@link SharedPreferences} object
   */
  public static TypedPrefs wrapSharedPreferences(SharedPreferences sharedPreferences) {
    return new TypedPrefsImpl(
        sharedPreferences,
        sGsonSupplier);
  }
}
