package com.episode6.hackit.typed.bundles;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.SparseArray;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Holds our static {@link BundleTranslator}s for object types that require
 * a custom builder methods (i.e. we can't do a simple objType == check).
 *
 * Each of these translators should be referenced in exactly 1 builder method
 * in {@link BundleNamespace}.
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

  static final BundleTranslator PARCELABLE_SPARSE_ARRAY_LIST = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getSparseParcelableArray(keyName);
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putSparseParcelableArray(keyName, (SparseArray<? extends Parcelable>) instance);
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

  static final BundleTranslator IBINDER = new BundleTranslator() {
    @Override @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getBinder(keyName);
    }

    @Override @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putBinder(keyName, (IBinder) instance);
    }
  };

  static final BundleTranslator BOOL_ARRAY = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      boolean[] array = b.getBooleanArray(keyName);
      ArrayList<Boolean> list = new ArrayList<>(array.length);
      for (boolean bool : array) {
        list.add(bool);
      }
      return list;
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      ArrayList<Boolean> list = (ArrayList<Boolean>) instance;
      boolean[] array = new boolean[list.size()];
      for (int i = 0; i<array.length; i++) {
        array[i] = list.get(i);
      }
      b.putBooleanArray(keyName, array);
    }
  };

  static final BundleTranslator INT_ARRAY = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      int[] array = b.getIntArray(keyName);
      ArrayList<Integer> list = new ArrayList<>(array.length);
      for (int i : array) {
        list.add(i);
      }
      return list;
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      ArrayList<Integer> list = (ArrayList<Integer>) instance;
      int[] array = new int[list.size()];
      for (int i = 0; i<array.length; i++) {
        array[i] = list.get(i);
      }
      b.putIntArray(keyName, array);
    }
  };

  static final BundleTranslator INT_ARRAY_LIST = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getIntegerArrayList(keyName);
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putIntegerArrayList(keyName, (ArrayList<Integer>) instance);
    }
  };

  static final BundleTranslator STRING_ARRAY = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      String[] array = b.getStringArray(keyName);
      return new ArrayList<String>(Arrays.asList(array));
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      ArrayList<String> arrayList = (ArrayList<String>) instance;
      String[] array = new String[arrayList.size()];
      array = arrayList.toArray(array);
      b.putStringArray(keyName, array);
    }
  };

  static final BundleTranslator STRING_ARRAY_LIST = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getStringArrayList(keyName);
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putStringArrayList(keyName, (ArrayList<String>) instance);
    }
  };

  static final BundleTranslator FLOAT_ARRAY = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      float[] array = b.getFloatArray(keyName);
      ArrayList<Float> list = new ArrayList<>(array.length);
      for (float i : array) {
        list.add(i);
      }
      return list;
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      ArrayList<Float> list = (ArrayList<Float>) instance;
      float[] array = new float[list.size()];
      for (int i = 0; i<array.length; i++) {
        array[i] = list.get(i);
      }
      b.putFloatArray(keyName, array);
    }
  };

  static final BundleTranslator LONG_ARRAY = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      long[] array = b.getLongArray(keyName);
      ArrayList<Long> list = new ArrayList<>(array.length);
      for (long i : array) {
        list.add(i);
      }
      return list;
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      ArrayList<Long> list = (ArrayList<Long>) instance;
      long[] array = new long[list.size()];
      for (int i = 0; i<array.length; i++) {
        array[i] = list.get(i);
      }
      b.putLongArray(keyName, array);
    }
  };

  static final BundleTranslator DOUBLE_ARRAY = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      double[] array = b.getDoubleArray(keyName);
      ArrayList<Double> list = new ArrayList<>(array.length);
      for (double i : array) {
        list.add(i);
      }
      return list;
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      ArrayList<Double> list = (ArrayList<Double>) instance;
      double[] array = new double[list.size()];
      for (int i = 0; i<array.length; i++) {
        array[i] = list.get(i);
      }
      b.putDoubleArray(keyName, array);
    }
  };

  static final BundleTranslator SHORT_ARRAY = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      short[] array = b.getShortArray(keyName);
      ArrayList<Short> list = new ArrayList<>(array.length);
      for (short i : array) {
        list.add(i);
      }
      return list;
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      ArrayList<Short> list = (ArrayList<Short>) instance;
      short[] array = new short[list.size()];
      for (int i = 0; i<array.length; i++) {
        array[i] = list.get(i);
      }
      b.putShortArray(keyName, array);
    }
  };

  static final BundleTranslator CHAR_ARRAY = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      char[] array = b.getCharArray(keyName);
      ArrayList<Character> list = new ArrayList<>(array.length);
      for (char i : array) {
        list.add(i);
      }
      return list;
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      ArrayList<Character> list = (ArrayList<Character>) instance;
      char[] array = new char[list.size()];
      for (int i = 0; i<array.length; i++) {
        array[i] = list.get(i);
      }
      b.putCharArray(keyName, array);
    }
  };

  static final BundleTranslator BYTE_ARRAY = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      byte[] array = b.getByteArray(keyName);
      return ByteBuffer.wrap(array);
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      ByteBuffer buffer = (ByteBuffer) instance;
      b.putByteArray(keyName, buffer.array());
    }
  };

  static final BundleTranslator CHAR_SEQUENCE_ARRAY = new BundleTranslator() {
    @Override @TargetApi(Build.VERSION_CODES.FROYO)
    public Object getFromBundle(Bundle b, String keyName) {
      CharSequence[] array = b.getCharSequenceArray(keyName);
      return new ArrayList<CharSequence>(Arrays.asList(array));
    }

    @Override @TargetApi(Build.VERSION_CODES.FROYO)
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      ArrayList<CharSequence> arrayList = (ArrayList<CharSequence>) instance;
      CharSequence[] array = new CharSequence[arrayList.size()];
      array = arrayList.toArray(array);
      b.putCharSequenceArray(keyName, array);
    }
  };

  static final BundleTranslator CHAR_SEQUENCE_ARRAY_LIST = new BundleTranslator() {
    @Override @TargetApi(Build.VERSION_CODES.FROYO)
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getCharSequenceArrayList(keyName);
    }

    @Override @TargetApi(Build.VERSION_CODES.FROYO)
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putCharSequenceArrayList(keyName, (ArrayList<CharSequence>) instance);
    }
  };
}
