package com.episode6.hackit.typed.bundles;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
@SuppressWarnings("unchecked")
public class CustomBundleTranslators {

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

  static final BundleTranslator PARCELABLE_ARRAY = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      Parcelable[] array = b.getParcelableArray(keyName);
      return new ArrayList<Parcelable>(Arrays.asList(array));
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      ArrayList<? extends Parcelable> arrayList = (ArrayList<? extends Parcelable>) instance;
      Parcelable[] array = new Parcelable[arrayList.size()];
      array = arrayList.toArray(array);
      b.putParcelableArray(keyName, array);
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


}
