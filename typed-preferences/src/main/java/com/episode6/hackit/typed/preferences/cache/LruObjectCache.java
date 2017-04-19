package com.episode6.hackit.typed.preferences.cache;

import android.annotation.TargetApi;
import android.util.LruCache;
import com.episode6.hackit.typed.core.TypedKey;

/**
 * An implementation of {@link ObjectCache} that uses android's {@link LruCache} as its backing store.
 */
@TargetApi(12)
public class LruObjectCache implements ObjectCache {

  private final LruCache<TypedKey, Object> mDelegateCache;

  public LruObjectCache(int maxSize) {
    mDelegateCache = new LruCache<>(maxSize);
  }

  @Override
  public Object put(TypedKey key, Object instance) {
    synchronized (mDelegateCache) {
      return mDelegateCache.put(key, instance);
    }
  }

  @Override
  public Object get(TypedKey key) {
    synchronized (mDelegateCache) {
      return mDelegateCache.get(key);
    }
  }

  @Override
  public Object remove(TypedKey key) {
    synchronized (mDelegateCache) {
      return mDelegateCache.remove(key);
    }
  }

  @Override
  public void clear() {
    synchronized (mDelegateCache) {
      mDelegateCache.evictAll();
    }
  }
}
