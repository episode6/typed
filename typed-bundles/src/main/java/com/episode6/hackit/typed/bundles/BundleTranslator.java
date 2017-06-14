package com.episode6.hackit.typed.bundles;

import android.os.Bundle;

/**
 * A totally, completely and ridiculously unsafe interface for translating
 * objects to/from bundles.
 */
interface BundleTranslator {
  Object getFromBundle(Bundle b, String keyName);
  void writeToBundle(Bundle b, String keyName, Object instance);
}
