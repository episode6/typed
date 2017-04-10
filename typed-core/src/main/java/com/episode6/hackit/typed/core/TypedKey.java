package com.episode6.hackit.typed.core;

import java.lang.reflect.Type;

/**
 *
 */
public interface TypedKey<V> {
  TypedKeyName getKeyName();
  Type getObjectType();
}
