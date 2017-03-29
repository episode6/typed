package com.episode6.hackit.typed.core.util;

public class Preconditions {

  public static <T> T checkNotNull(T obj) {
    if (obj == null) {
      throw new NullPointerException();
    }
    return obj;
  }
}
