package com.episode6.hackit.typed.bundles;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 */
@SuppressWarnings("unchecked")
public class BundleTranslators {

  static final BundleTranslator PARCELABLE = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getParcelable(keyName);
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putParcelable(keyName, (Parcelable) instance);
    }
  };

  static final BundleTranslator SERIALIZABLE = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getSerializable(keyName);
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putSerializable(keyName, (Serializable) instance);
    }
  };

  static final BundleTranslator PARCELABLE_ARRAY_LIST = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getParcelableArrayList(keyName);
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putParcelableArrayList(keyName, (ArrayList<? extends Parcelable>) instance);
    }
  };
}
