package com.episode6.hackit.typed.bundles;

import com.episode6.hackit.typed.core.TypedKeyName;
import com.episode6.hackit.typed.core.util.Supplier;

import javax.annotation.Nullable;
import java.lang.reflect.Type;

/**
 * Standard bundle key with a default value
 */
public class BundleKey<V> extends AbstractBundleKey<V> {

  private final Supplier<V> mDefaultValueSupplier;

  BundleKey(
      TypedKeyName keyName,
      Type objectType,
      @Nullable BundleTranslator translator,
      Supplier<V> defaultValueSupplier) {
    super(keyName, objectType, translator);
    mDefaultValueSupplier = defaultValueSupplier;
  }

  V getDefaultValue() {
    return mDefaultValueSupplier.get();
  }
}
