package com.episode6.hackit.typed.preferences;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import com.episode6.hackit.typed.core.TypedKey;
import com.episode6.hackit.typed.core.util.Preconditions;
import com.episode6.hackit.typed.core.util.Supplier;
import com.episode6.hackit.typed.core.util.Suppliers;
import com.episode6.hackit.typed.preferences.cache.ObjectCache;
import com.google.gson.Gson;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
class TypedPrefsImpl implements TypedPrefs {

  private final SharedPreferences mBackingPrefs;
  private final Supplier<Gson> mGsonSupplier;
  private final @Nullable ObjectCache mCache;

  TypedPrefsImpl(
      SharedPreferences backingPrefs,
      Supplier<Gson> gsonSupplier,
      @Nullable ObjectCache cache) {
    mBackingPrefs = backingPrefs;
    mGsonSupplier = Suppliers.memoize(gsonSupplier);
    mCache = cache;
  }

  @Override
  public <T> T get(PrefKey<T> prefKey) {
    T obj = getFromCache(prefKey);
    if (obj != null) {
      return obj;
    }

    if (!containsInternal(prefKey)) {
      return prefKey.getDefaultValue();
    }
    return getInternalAndCacheResult(prefKey);
  }

  @Nullable
  @Override
  public <T> T get(OptPrefKey<T> prefKey) {
    T obj = getFromCache(prefKey);
    if (obj != null) {
      return obj;
    }

    if (!containsInternal(prefKey)) {
      return null;
    }
    return getInternalAndCacheResult(prefKey);
  }


  @Override
  public boolean contains(PrefKey<?> prefKey) {
    return containsInternal(prefKey);
  }

  @Override
  public boolean contains(OptPrefKey<?> prefKey) {
    return containsInternal(prefKey);
  }

  @Override
  public Editor edit() {
    return new EditorImpl(mBackingPrefs.edit());
  }

  @SuppressWarnings("unchecked")
  private <T> T getFromCache(TypedKey<T> prefKey) {
    if (mCache == null) {
      return null;
    }

    return (T) mCache.get(prefKey);
  }

  private void putInCache(TypedKey<?> prefKey, Object instance) {
    if (mCache == null) {
      return;
    }

    mCache.put(prefKey, instance);
  }

  private void removeFromCache(TypedKey<?> prefKey) {
    if (mCache == null) {
      return;
    }
    mCache.remove(prefKey);
  }

  private void clearCache() {
    if (mCache == null) {
      return;
    }
    mCache.clear();
  }

  private <T> T getInternalAndCacheResult(TypedKey<T> prefKey) {
    T obj = getFromSharedPrefs(prefKey);
    putInCache(prefKey, obj);
    return obj;
  }

  @SuppressWarnings("unchecked")
  private <T> T getFromSharedPrefs(TypedKey<T> prefKey) {
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

  private class EditorImpl implements TypedPrefs.Editor {

    private final SharedPreferences.Editor mEditor;
    private HashMap<TypedKey<?>, Object> mPutMap = new HashMap<>();
    private boolean mClearCacheOnCommit = false;

    private EditorImpl(SharedPreferences.Editor editor) {
      mEditor = editor;
    }

    @TargetApi(9)
    @Override
    public void apply() {
      processPutMap();
      mEditor.apply();
    }

    @Override
    public void commit() {
      processPutMap();
      mEditor.commit();
    }

    @Override
    public <T> Editor put(PrefKey<T> prefKey, T instance) {
      Preconditions.checkNotNull(instance);
      mPutMap.put(prefKey, instance);
      return this;
    }

    @Override
    public <T> Editor put(OptPrefKey<T> prefKey, @Nullable T instance) {
      mPutMap.put(prefKey, instance);
      return this;
    }

    @Override
    public Editor clear() {
      mEditor.clear();
      mPutMap.clear();
      mClearCacheOnCommit = true;
      return this;
    }

    @Override
    public Editor remove(PrefKey<?> prefKey) {
      mPutMap.put(prefKey, null);
      return this;
    }

    @Override
    public Editor remove(OptPrefKey<?> prefKey) {
      mPutMap.put(prefKey, null);
      return this;
    }

    private void processPutMap() {
      if (mClearCacheOnCommit) {
        clearCache();
      }

      for (Map.Entry<TypedKey<?>, Object> entry : mPutMap.entrySet()) {
        if (entry.getValue() == null) {
          removeInternal(entry.getKey());
        } else {
          putInternal(entry.getKey(), entry.getValue());
        }
      }
    }

    private void putInternal(TypedKey<?> prefKey, Object instance) {
      putInCache(prefKey, instance);

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

    private void removeInternal(TypedKey<?> prefKey) {
      removeFromCache(prefKey);
      mEditor.remove(prefKey.getKeyName().toString());
    }
  }
}