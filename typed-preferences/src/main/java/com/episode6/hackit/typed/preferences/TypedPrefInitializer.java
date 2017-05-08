package com.episode6.hackit.typed.preferences;

import android.content.Context;
import com.episode6.hackit.typed.core.util.InstanceSupplier;
import com.episode6.hackit.typed.core.util.Supplier;
import com.google.gson.Gson;

import javax.annotation.Nullable;

/**
 * Optional initializer for the static elements of the typed-preferences library.
 */
public class TypedPrefInitializer {

  /**
   * Convienence initializer method. Inits the {@link DefaultPrefs} class but leaves the default
   * Gson and ObjectCache implementations.
   * @param context Application context.
   */
  public static void initWithContext(Context context) {
    configure()
        .context(context)
        .init();
  }

  /**
   * @return a {@link Configurater} used to initialize the static elements of the typed-preferences library
   */
  public static Configurater configure() {
    return new Configurater();
  }

  /**
   * A builder-style class to initializing the static elements of the typed-preferences library
   */
  public static class Configurater {

    private @Nullable Context mContext;
    private @Nullable Supplier<Gson> mGsonSupplier;

    /**
     * Set the application context for the {@link DefaultPrefs} class.
     * @param context Your application's context
     * @return this {@link Configurater}
     */
    public Configurater context(Context context) {
      mContext = context.getApplicationContext();
      return this;
    }

    /**
     * Specify a specific instance of Gson be used to translate non-primitives.
     * @param gson The instance of Gson to translate with.
     * @return this {@link Configurater}
     */
    public Configurater gson(Gson gson) {
      mGsonSupplier = new InstanceSupplier<>(gson);
      return this;
    }

    /**
     * Specify a specific instance of Gson be used to translate non-primitives, but don't construct the Gson instance
     * until it's actually needed.
     * @param gsonSupplier The Gson supplier to be used by {@link TypedPrefs}.
     * @return this {@link Configurater}
     */
    public Configurater gsonSupplier(Supplier<Gson> gsonSupplier) {
      mGsonSupplier = gsonSupplier;
      return this;
    }

    /**
     * Initialize the static elements of the typed-preferences library
     */
    public void init() {
      TypedPrefWrapper.setDefaults(mGsonSupplier);
      if (mContext != null) {
        DefaultPrefs.init(mContext);
      }
    }

  }
}
