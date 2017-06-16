package com.episode6.hackit.typed.bundles;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;

/**
 * A utility class for building fragments that use TypedBundles for arguments
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentBuilder<V extends Fragment> {

  private final TypedBundle mArgs;
  private final Class<V> mFragmentClass;

  public FragmentBuilder(Class<V> fragmentClass) {
    this(TypedBundles.create(), fragmentClass);
  }

  public FragmentBuilder(TypedBundle.Factory typedBundleFactory, Class<V> fragmentClass) {
    this(typedBundleFactory.create(), fragmentClass);
  }

  public FragmentBuilder(TypedBundle args, Class<V> fragmentClass) {
    mArgs = args;
    mFragmentClass = fragmentClass;
  }

  public <T> FragmentBuilder<V> arg(BundleKey<T> key, T arg) {
    mArgs.put(key, arg);
    return this;
  }

  public <T> FragmentBuilder<V> arg(ReqBundleKey<T> key, T arg) {
    mArgs.put(key, arg);
    return this;
  }

  public <T> FragmentBuilder<V> arg(OptBundleKey<T> key, T arg) {
    mArgs.put(key, arg);
    return this;
  }

  public V build() {
    try {
      V fragment = mFragmentClass.newInstance();
      fragment.setArguments(mArgs.asBundle());
      return fragment;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
