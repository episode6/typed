package com.episode6.hackit.typed.core;

import com.episode6.hackit.typed.core.util.Preconditions;

/**
 *
 */
public class TypedKeyName {

  private final TypedKeyNamespace mNamespace;
  private final String mName;

  private final String mFullName;

  public TypedKeyName(TypedKeyNamespace namespace, String name) {
    mNamespace = Preconditions.checkNotNull(namespace);
    mName = Preconditions.checkNotNull(name);
    mFullName = mNamespace.getNameForChild(name);
  }

  @Override
  public String toString() {
    return mFullName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TypedKeyName that = (TypedKeyName) o;

    if (!mNamespace.equals(that.mNamespace)) return false;
    return mName.equals(that.mName);
  }

  @Override
  public int hashCode() {
    int result = mNamespace.hashCode();
    result = 31 * result + mName.hashCode();
    return result;
  }
}
