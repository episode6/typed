package com.episode6.hackit.typed.preferences;

import android.content.SharedPreferences;
import com.episode6.hackit.typed.core.TypedKey;
import com.episode6.hackit.typed.core.util.Preconditions;
import com.episode6.hackit.typed.core.util.Supplier;
import com.google.gson.Gson;

import javax.annotation.Nullable;
import java.lang.reflect.Type;

/**
 *
 */
public class TypedPreferencesImpl implements TypedPreferences {

  private final SharedPreferences mBackingPrefs;
  private final Supplier<Gson> mGsonSupplier;

  private TypedPreferencesImpl(SharedPreferences backingPrefs, Supplier<Gson> gsonSupplier) {
    mBackingPrefs = backingPrefs;
    mGsonSupplier = gsonSupplier;
  }

  @Override
  public <T> T get(PrefKey<T> prefKey) {
    if (!containsInternal(prefKey)) {
      return prefKey.getDefaultValue();
    }
    return getInternal(prefKey);
  }

  @Nullable
  @Override
  public <T> T get(NullablePrefKey<T> prefKey) {
    if (!containsInternal(prefKey)) {
      return null;
    }
    return getInternal(prefKey);
  }

  @Override
  public boolean contains(PrefKey<?> prefKey) {
    return containsInternal(prefKey);
  }

  @Override
  public boolean contains(NullablePrefKey<?> prefKey) {
    return containsInternal(prefKey);
  }

  @Override
  public Editor edit() {
    return new EditorImpl(mBackingPrefs.edit());
  }

  private <T> T getInternal(TypedKey<T> prefKey) {
    Type objType = prefKey.getObjectType();
    String keyName = prefKey.getKeyName().toString();
    if (objType == Boolean.class) {
      return (T)(Boolean) mBackingPrefs.getBoolean(keyName, false);
    } else if (objType == Float.class) {
      return (T)(Float) mBackingPrefs.getFloat(keyName, 0f);
    } else if (objType == Integer.class) {
      return (T)(Integer) mBackingPrefs.getInt(keyName, 0);
    } else if (objType == Long.class) {
      return (T)(Long) mBackingPrefs.getLong(keyName, 0L);
    } else if (objType == String.class) {
      return (T) mBackingPrefs.getString(keyName, null);
    } else if (objType == Double.class) {
      long doubleBits = mBackingPrefs.getLong(keyName, 0L);
      return (T)(Double) Double.longBitsToDouble(doubleBits);
    } else {
      String translation = mBackingPrefs.getString(keyName, null);
      return mGsonSupplier.get().fromJson(translation, objType);
    }
  }

  private boolean containsInternal(TypedKey<?> key) {
    return mBackingPrefs.contains(key.getKeyName().toString());
  }

  private class EditorImpl implements TypedPreferences.Editor {

    private final SharedPreferences.Editor mEditor;

    private EditorImpl(SharedPreferences.Editor editor) {
      mEditor = editor;
    }

    @Override
    public void apply() {
      mEditor.apply();
    }

    @Override
    public void commit() {
      mEditor.commit();
    }

    @Override
    public <T> Editor put(PrefKey<T> prefKey, T instance) {
      Preconditions.checkNotNull(instance);
      putInternal(prefKey, instance);
      return this;
    }

    @Override
    public <T> Editor put(NullablePrefKey<T> prefKey, @Nullable T instance) {
      if (instance == null) {
        removeInternal(prefKey);
      } else {
        putInternal(prefKey, instance);
      }
      return this;
    }

    @Override
    public Editor clear() {
      mEditor.clear();
      return this;
    }

    @Override
    public Editor remove(PrefKey<?> prefKey) {
      removeInternal(prefKey);
      return this;
    }

    @Override
    public Editor remove(NullablePrefKey<?> prefKey) {
      removeInternal(prefKey);
      return this;
    }

    private <T> void putInternal(TypedKey<T> prefKey, T instance) {
      Type objType = prefKey.getObjectType();
      String keyName = prefKey.getKeyName().toString();
      if (objType == Boolean.class) {
        mEditor.putBoolean(keyName, (Boolean) instance);
      } else if (objType == Float.class) {
        mEditor.putFloat(keyName, (Float) instance);
      } else if (objType == Integer.class) {
        mEditor.putInt(keyName, (Integer) instance);
      } else if (objType == Long.class) {
        mEditor.putLong(keyName, (Long) instance);
      } else if (objType == String.class) {
        mEditor.putString(keyName, (String) instance);
      } else if (objType == Double.class) {
        long doubleBits = Double.doubleToRawLongBits((Double) instance);
        mEditor.putLong(keyName, doubleBits);
      } else {
        String translation = mGsonSupplier.get().toJson(instance);
        mEditor.putString(keyName, translation);
      }
    }

    private void removeInternal(TypedKey<?> key) {
      mEditor.remove(key.getKeyName().toString());
    }
  }
}
