package com.episode6.hackit.typed.bundles;

/**
 * Exception thrown when trying to get() a {@link ReqBundleKey} that does not exist in the bundle.
 */
public class MissingPropertyException extends RuntimeException {
  MissingPropertyException(ReqBundleKey key) {
    super("Missing required bundle property name: " + key.getKeyName().toString() + ", type: " + key.getObjectType());
  }
}
