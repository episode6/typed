package com.episode6.hackit.typed.preferences;

import android.content.Context;
import com.episode6.hackit.typed.core.util.InstanceSupplier;
import com.episode6.hackit.typed.core.util.Supplier;
import com.episode6.hackit.typed.preferences.cache.ObjectCache;
import com.google.gson.Gson;

import javax.annotation.Nullable;

/**
 *
 */
public class TypedPrefInitializer {

  public static void initWithContext(Context context) {
    configure()
        .context(context)
        .init();
  }

  public static Configurater configure() {
    return new Configurater();
  }

  public static class Configurater {

    private @Nullable Context mContext;
    private @Nullable Supplier<Gson> mGsonSupplier;
    private @Nullable Supplier<ObjectCache> mCacheSupplier;

    public Configurater context(Context context) {
      mContext = context.getApplicationContext();
      return this;
    }

    public Configurater gson(Gson gson) {
      mGsonSupplier = new InstanceSupplier<>(gson);
      return this;
    }

    public Configurater gsonSupplier(Supplier<Gson> gsonSupplier) {
      mGsonSupplier = gsonSupplier;
      return this;
    }

    public Configurater cacheSupplier(Supplier<ObjectCache> cacheSupplier) {
      mCacheSupplier = cacheSupplier;
      return this;
    }

    public Configurater noCache() {
      mCacheSupplier = new Supplier<ObjectCache>() {
        @Override
        public ObjectCache get() {
          return null;
        }
      };
      return this;
    }

    public void init() {
      TypedPrefWrapper.setDefaults(mGsonSupplier, mCacheSupplier);
      if (mContext != null) {
        DefaultPrefs.init(mContext);
      }
    }

  }
}
