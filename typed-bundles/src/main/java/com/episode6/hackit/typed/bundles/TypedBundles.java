package com.episode6.hackit.typed.bundles;

import android.os.Bundle;
import com.episode6.hackit.typed.core.util.DefaultGsonSupplier;
import com.episode6.hackit.typed.core.util.InstanceSupplier;
import com.episode6.hackit.typed.core.util.Supplier;
import com.episode6.hackit.typed.core.util.Suppliers;
import com.google.gson.Gson;

import javax.annotation.Nullable;

/**
 *
 */
public class TypedBundles {

  private static Supplier<Gson> sGsonSupplier = Suppliers.memoize(new DefaultGsonSupplier());

  public static void setDefaultGsonSupplier(Supplier<Gson> gsonSupplier) {
    sGsonSupplier = Suppliers.memoize(gsonSupplier);
  }

  public static void setDefaultGson(Gson gson) {
    setDefaultGsonSupplier(new InstanceSupplier<>(gson));
  }

  public static TypedBundle fromBundle(Bundle bundle) {
    return new TypedBundleImpl(sGsonSupplier, bundle);
  }

  public static TypedBundle create() {
    return fromBundle(new Bundle());
  }

  public static @Nullable TypedBundle fromBundleOrNull(@Nullable Bundle bundle) {
    return bundle == null ? null : fromBundle(bundle);
  }

  public static TypedBundle fromNullable(@Nullable Bundle bundle) {
    return bundle == null ? create() : fromBundle(bundle);
  }
}
