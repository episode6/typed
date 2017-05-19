package com.episode6.hackit.typed.core.util;

/**
 *
 */
public class InstanceSupplier<V> implements Supplier<V> {

  private final V mInstance;

  public InstanceSupplier(V instance) {
    mInstance = instance;
  }

  @Override
  public V get() {
    return mInstance;
  }
}
