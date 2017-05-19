package com.episode6.hackit.typed.core.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A default Gson supplier for typed modules
 */
public class DefaultGsonSupplier implements Supplier<Gson> {
  @Override
  public Gson get() {
    return new GsonBuilder()
        .enableComplexMapKeySerialization()
        .create();
  }
}
