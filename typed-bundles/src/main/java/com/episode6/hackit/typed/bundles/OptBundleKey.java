package com.episode6.hackit.typed.bundles;

import com.episode6.hackit.typed.core.TypedKeyName;

import javax.annotation.Nullable;
import java.lang.reflect.Type;

/**
 * Optional bundle key doesn't provide a default value. Nulls are allowed.
 */
public class OptBundleKey<V> extends AbstractBundleKey<V> {

  OptBundleKey(TypedKeyName keyName, Type objectType, @Nullable BundleTranslator translator) {
    super(keyName, objectType, translator);
  }
}
