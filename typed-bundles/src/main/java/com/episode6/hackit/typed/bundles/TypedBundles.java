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

  /**
   * Sets the default gson supplier used when creating new TypedBundles via
   * the static {@link #create()}, {@link #wrap(Bundle)}, {@link #wrapNullable(Bundle)}
   * and {@link #intentExtras(Intent)} methods
   * @param gsonSupplier A Supplier for a default Gson instance.
   */
  public static void setDefaultGsonSupplier(Supplier<Gson> gsonSupplier) {
    sDefaultFactory = createFactory(gsonSupplier);
  }

  /**
   * Sets the default gson instance used when creating new TypedBundles via
   * the static {@link #create()}, {@link #wrap(Bundle)}, {@link #wrapNullable(Bundle)}
   * and {@link #intentExtras(Intent)} methods
   * @param gson The gson instance to use.
   */
  public static void setDefaultGson(Gson gson) {
    sDefaultFactory = createFactory(gson);
  }

  /**
   * Create a {@link TypedBundle.Factory} using the provided gson Supplier. This
   * should be useful for library projects that use TypedBundles and need a custom
   * Gson implementation. Instead of using the static create/wrap methods, said library
   * project should create a store their own static TypedBundle.Factory, and use that
   * to create their TypedBundles. This will ensure implementors of said libraries can't
   * inadvertently override an important gson instance/supplier.
   * @param gsonSupplier A gson supplier to be used by the Factory
   * @return A new {@link TypedBundle.Factory}
   */
  public static TypedBundle.Factory createFactory(Supplier<Gson> gsonSupplier) {
    return new TypedBundleFactoryImpl(gsonSupplier);
  }

  /**
   * Create a {@link TypedBundle.Factory} using the provided gson instance. This
   * should be useful for library projects that use TypedBundles and need a custom
   * Gson implementation. Instead of using the static create/wrap methods, said library
   * project should create a store their own static TypedBundle.Factory, and use that
   * to create their TypedBundles. This will ensure implementors of said libraries can't
   * inadvertently override an important gson instance/supplier.
   * @param gson A gson instance to be used by the Factory
   * @return A new {@link TypedBundle.Factory}
   */
  public static TypedBundle.Factory createFactory(Gson gson) {
    return new TypedBundleFactoryImpl(new InstanceSupplier<>(gson));
  }

  /**
   * @see TypedBundle.Factory#wrap(Bundle)
   * @param bundle The bundle to wrap
   * @return a new {@link TypedBundle} that wraps the provided bundle
   */
  public static TypedBundle wrap(Bundle bundle) {
    return sDefaultFactory.wrap(bundle);
  }

  /**
   * @see TypedBundle.Factory#create()
   * @return a new {@link TypedBundle}
   */
  public static TypedBundle create() {
    return sDefaultFactory.create();
  }

  /**
   * @see TypedBundle.Factory#wrapNullable(Bundle)
   * @param bundle The bundle to wrap
   * @return a new {@link TypedBundle} that wraps the provided bundle
   * if it's non-null
   */
  public static TypedBundle wrapNullable(@Nullable Bundle bundle) {
    return sDefaultFactory.wrapNullable(bundle);
  }

  /**
   * @see TypedBundle.Factory#intentExtras(Intent)
   * @param intent The intent to getExtras from
   * @return A new {@link TypedBundle} that wraps the intent's extras
   * if the intent and its extras bundle is non-null.
   */
  public static TypedBundle intentExtras(@Nullable Intent intent) {
    return sDefaultFactory.intentExtras(intent);
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
