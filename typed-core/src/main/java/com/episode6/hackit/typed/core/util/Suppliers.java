/*
 * Logic copied from Guava's Suppliers: https://github.com/google/guava/blob/master/guava/src/com/google/common/base/Suppliers.java
 * Original license included
 */

/*
 * Copyright (C) 2007 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.episode6.hackit.typed.core.util;

/**
 * Everytime I re-write simple memoization logic, I die a little inside.
 */
public class Suppliers {

  public static <T> Supplier<T> memoize(Supplier<T> delegate) {
    if (delegate instanceof MemoizedSupplier) {
      return delegate;
    }
    return new MemoizedSupplier<>(delegate);
  }

  private static class MemoizedSupplier<T> implements Supplier<T> {

    private final Supplier<T> mDelegate;

    transient volatile boolean mIsInitialized;
    // "value" does not need to be volatile; visibility piggy-backs
    // on volatile read of "initialized".
    transient T mValue;

    private MemoizedSupplier(Supplier<T> delegate) {
      mDelegate = Preconditions.checkNotNull(delegate);
    }

    @Override
    public T get() {
      // A 2-field variant of Double Checked Locking.
      if (!mIsInitialized) {
        synchronized (this) {
          if (!mIsInitialized) {
            T t = mDelegate.get();
            mValue = t;
            mIsInitialized = true;
            return t;
          }
        }
      }
      return mValue;
    }
  }
}
