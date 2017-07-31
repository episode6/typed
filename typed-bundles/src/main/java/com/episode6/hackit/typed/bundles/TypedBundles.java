package com.episode6.hackit.typed.bundles;

import android.content.Intent;
import android.os.Bundle;
import com.episode6.hackit.typed.core.util.*;
import com.google.gson.Gson;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for createing {@link TypedBundle}s
 */
public class TypedBundles {

  private static TypedBundle.Factory sDefaultFactory = createFactory(new DefaultGsonSupplier());

  /**
   * Sets the default factory used by this class's static methods. In production code, you'll
   * probably prefer {@link #setDefaultGson(Gson)} or {@link #setDefaultGsonSupplier(Supplier)},
   * however this can be useful in your unit tests.
   * @param factory The factory to be used by default to create new TypedBundles
   */
  public static void setDefaultFactory(TypedBundle.Factory factory) {
    sDefaultFactory = Preconditions.checkNotNull(factory);
  }

  /**
   * Sets the default gson supplier used when creating new TypedBundles via
   * the static {@link #create()}, {@link #wrap(Bundle)}, {@link #wrapNullable(Bundle)}
   * and {@link #intentExtras(Intent)} methods
   * @param gsonSupplier A Supplier for a default Gson instance.
   */
  public static void setDefaultGsonSupplier(Supplier<Gson> gsonSupplier) {
    setDefaultFactory(createFactory(gsonSupplier));
  }

  /**
   * Sets the default gson instance used when creating new TypedBundles via
   * the static {@link #create()}, {@link #wrap(Bundle)}, {@link #wrapNullable(Bundle)}
   * and {@link #intentExtras(Intent)} methods
   * @param gson The gson instance to use.
   */
  public static void setDefaultGson(Gson gson) {
    setDefaultFactory(createFactory(gson));
  }

  /**
   * Reset the default factory.
   */
  public static void resetDefaultFactory() {
    setDefaultFactory(createFactory(new DefaultGsonSupplier()));
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

  /**
   * Memoize a {@link TypedBundle}. The resulting bundle will cache
   * it's entries so multiple gets to the same object don't need to
   * be repeatedly deserialized when the same instance is used.
   * @param typedBundle The {@link TypedBundle} to be memoized
   * @return A memoized typed bundle (or typedBundle itself if
   * its already memoized)
   */
  public static TypedBundle memoize(TypedBundle typedBundle) {
    if (typedBundle instanceof MemoizedTypedBundle) {
      return typedBundle;
    }
    return new MemoizedTypedBundle(typedBundle);
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

  private static class MemoizedTypedBundle implements TypedBundle {

    private final TypedBundle mDelegate;
    private final Map<AbstractBundleKey<?>, Object> mCache = new HashMap<>();

    MemoizedTypedBundle(TypedBundle delegate) {
      mDelegate = delegate;
    }

    @Override
    public boolean contains(BundleKey<?> key) {
      return mDelegate.contains(key);
    }

    @Override
    public boolean contains(ReqBundleKey<?> key) {
      return mDelegate.contains(key);
    }

    @Override
    public boolean contains(OptBundleKey<?> key) {
      return mDelegate.contains(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized <T> T get(BundleKey<T> key) {
      if (mCache.containsKey(key)) {
        return (T) mCache.get(key);
      }
      T obj = mDelegate.get(key);
      mCache.put(key, obj);
      return obj;
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized <T> T get(ReqBundleKey<T> key) {
      if (mCache.containsKey(key)) {
        return (T) mCache.get(key);
      }
      T obj = mDelegate.get(key);
      mCache.put(key, obj);
      return obj;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public synchronized <T> T get(OptBundleKey<T> key) {
      if (mCache.containsKey(key)) {
        return (T) mCache.get(key);
      }
      T obj = mDelegate.get(key);
      mCache.put(key, obj);
      return obj;
    }

    @Override
    public synchronized <T> TypedBundle put(BundleKey<T> key, T value) {
      mDelegate.put(key, value);
      mCache.put(key, value);
      return this;
    }

    @Override
    public synchronized <T> TypedBundle put(ReqBundleKey<T> key, T value) {
      mDelegate.put(key, value);
      mCache.put(key, value);
      return this;
    }

    @Override
    public synchronized <T> TypedBundle put(OptBundleKey<T> key, @Nullable T value) {
      mDelegate.put(key, value);
      mCache.put(key, value);
      return this;
    }

    @Override
    public synchronized <T> TypedBundle remove(BundleKey<?> key) {
      mDelegate.remove(key);
      mCache.remove(key);
      return this;
    }

    @Override
    public synchronized <T> TypedBundle remove(ReqBundleKey<?> key) {
      mDelegate.remove(key);
      mCache.remove(key);
      return this;
    }

    @Override
    public synchronized <T> TypedBundle remove(OptBundleKey<?> key) {
      mDelegate.remove(key);
      mCache.remove(key);
      return this;
    }

    @Override
    public Bundle asBundle() {
      return mDelegate.asBundle();
    }
  }
}
