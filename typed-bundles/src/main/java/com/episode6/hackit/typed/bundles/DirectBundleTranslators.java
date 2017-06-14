package com.episode6.hackit.typed.bundles;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import javax.annotation.Nullable;
import java.lang.reflect.Type;

/**
 *
 */
public class DirectBundleTranslators {

  static @Nullable BundleTranslator getDirectTranslator(Type keyType) {
    if (keyType == Boolean.class) {
      return BOOL;
    } else if (keyType == Integer.class) {
      return INT;
    } else if (keyType == String.class) {
      return STRING;
    } else if (keyType == Float.class) {
      return FLOAT;
    } else if (keyType == Long.class) {
      return LONG;
    } else if (keyType == Double.class) {
      return DOUBLE;
    } else if (keyType == Short.class) {
      return SHORT;
    } else if (keyType == Character.class) {
      return CHAR;
    } else if (keyType == Byte.class) {
      return BYTE;
    } else if (keyType == Bundle.class) {
      return BUNDLE;
    } else if (keyType == TypedBundle.class) {
      return TYPED_BUNDLE;
    } else if (keyType == CharSequence.class) {
      return CHAR_SEQUENCE;
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      if (keyType == android.util.Size.class) {
        return SIZE;
      } else if (keyType == android.util.SizeF.class) {
        return SIZEF;
      }
    }
    return null;
  }

  private static final BundleTranslator BOOL = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getBoolean(keyName);
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putBoolean(keyName, (Boolean) instance);
    }
  };

  private static final BundleTranslator INT = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getInt(keyName);
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putInt(keyName, (Integer) instance);
    }
  };

  private static final BundleTranslator STRING = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getString(keyName);
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putString(keyName, (String) instance);
    }
  };

  private static final BundleTranslator FLOAT = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getFloat(keyName);
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putFloat(keyName, (Float) instance);
    }
  };

  private static final BundleTranslator LONG = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getLong(keyName);
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putLong(keyName, (Long) instance);
    }
  };

  private static final BundleTranslator DOUBLE = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getDouble(keyName);
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putDouble(keyName, (Double) instance);
    }
  };

  private static final BundleTranslator SHORT = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getShort(keyName);
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putShort(keyName, (Short) instance);
    }
  };

  private static final BundleTranslator CHAR = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getChar(keyName);
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putChar(keyName, (Character) instance);
    }
  };

  private static final BundleTranslator BYTE = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getByte(keyName);
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putByte(keyName, (Byte) instance);
    }
  };

  private static final BundleTranslator BUNDLE = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getBundle(keyName);
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putBundle(keyName, (Bundle) instance);
    }
  };

  private static final BundleTranslator TYPED_BUNDLE = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      return TypedBundles.fromBundle(b.getBundle(keyName));
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putBundle(keyName, ((TypedBundle)instance).asBundle());
    }
  };

  private static final BundleTranslator CHAR_SEQUENCE = new BundleTranslator() {
    @Override
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getCharSequence(keyName);
    }

    @Override
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putCharSequence(keyName, (CharSequence) instance);
    }
  };

  private static final BundleTranslator SIZE = new BundleTranslator() {
    @Override @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getSize(keyName);
    }

    @Override @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putSize(keyName, (android.util.Size) instance);
    }
  };

  private static final BundleTranslator SIZEF = new BundleTranslator() {
    @Override @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Object getFromBundle(Bundle b, String keyName) {
      return b.getSizeF(keyName);
    }

    @Override @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void writeToBundle(Bundle b, String keyName, Object instance) {
      b.putSizeF(keyName, (android.util.SizeF) instance);
    }
  };
}
