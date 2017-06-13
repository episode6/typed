package com.episode6.hackit.typed.bundles;

import android.os.Bundle;

/**
 *
 */
public interface BundleTranslator {
  Object getFromBundle(Bundle b, String keyName);
  void writeToBundle(Bundle b, String keyName, Object instance);
}
