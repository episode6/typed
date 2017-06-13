package com.episode6.hackit.typed.bundles;

import com.episode6.hackit.typed.core.TypedKeyName;

import javax.annotation.Nullable;
import java.lang.reflect.Type;

/**
 * A required bundle key. This key doesn't provide a default value, but an exception will be thrown if
 * the key does not exist in the bundle.
 */
public class ReqBundleKey<V> extends AbstractBundleKey<V> {
  ReqBundleKey(
      TypedKeyName keyName,
      Type objectType,
      @Nullable BundleTranslator translator) {
    super(keyName, objectType, translator);
  }
}
