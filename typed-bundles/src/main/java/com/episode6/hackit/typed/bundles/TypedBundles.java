package com.episode6.hackit.typed.bundles;

import android.content.Intent;
import android.os.Bundle;
import com.episode6.hackit.typed.core.util.*;
import com.google.gson.Gson;

import javax.annotation.Nullable;

/**
 * Utility class for createing {@link TypedBundle}s
 */
public class TypedBundles {

  private static TypedBundle.Factory sDefaultFactory = new TypedBundleFactoryImpl(new DefaultGsonSupplier());

  public static void setDefaultGsonSupplier(Supplier<Gson> gsonSupplier) {
    sDefaultFactory = createFactory(gsonSupplier);
  }

  public static void setDefaultGson(Gson gson) {
    sDefaultFactory = createFactory(gson);
  }

  public static TypedBundle.Factory createFactory(Supplier<Gson> gsonSupplier) {
    return new TypedBundleFactoryImpl(gsonSupplier);
  }

  public static TypedBundle.Factory createFactory(Gson gson) {
    return new TypedBundleFactoryImpl(new InstanceSupplier<>(gson));
  }

  public static TypedBundle wrap(Bundle bundle) {
    return sDefaultFactory.wrap(bundle);
  }

  public static TypedBundle create() {
    return sDefaultFactory.create();
  }

  public static TypedBundle wrapNullable(@Nullable Bundle bundle) {
    return sDefaultFactory.wrapNullable(bundle);
  }

  private static class TypedBundleFactoryImpl implements TypedBundle.Factory {

    private final Supplier<Gson> mGsonSupplier;

    private TypedBundleFactoryImpl(Supplier<Gson> gsonSupplier) {
      mGsonSupplier = Suppliers.memoize(gsonSupplier);
    }

    @Override
    public TypedBundle create() {
      return wrap(new Bundle());
    }

    @Override
    public TypedBundle wrap(Bundle bundle) {
      return new TypedBundleImpl(mGsonSupplier, Preconditions.checkNotNull(bundle));
    }

    @Override
    public TypedBundle wrapNullable(@Nullable Bundle bundle) {
      return bundle == null ? create() : wrap(bundle);
    }

    @Override
    public TypedBundle intentExtras(@Nullable Intent intent) {
      if (intent == null) {
        return create();
      }
      return wrapNullable(intent.getExtras());
    }
  }
}
