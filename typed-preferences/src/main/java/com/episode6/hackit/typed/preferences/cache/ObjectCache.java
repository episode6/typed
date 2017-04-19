package com.episode6.hackit.typed.preferences.cache;

import com.episode6.hackit.typed.core.TypedKey;

/**
 *
 */
public interface ObjectCache {
  Object put(TypedKey key, Object instance);
  Object get(TypedKey key);
  Object remove(TypedKey key);
  void clear();
}
