package com.episode6.hackit.typed.core;

import com.episode6.hackit.typed.core.util.Preconditions;

import javax.annotation.Nullable;

public class TypedKeyNamespace {
  private final String mDelineator;

  @Nullable private final TypedKeyNamespace mParent;
  @Nullable private final String mName;

  private final String mFullName;

  protected TypedKeyNamespace(String delineator) {
    mDelineator = Preconditions.checkNotNull(delineator);
    mParent = null;
    mName = null;
    mFullName = "";
  }

  protected TypedKeyNamespace(TypedKeyNamespace parent, String childName) {
    mParent = Preconditions.checkNotNull(parent);
    mDelineator = parent.mDelineator;
    mName = Preconditions.checkNotNull(childName);
    mFullName = parent.getNameForChild(childName);
  }

  String getNameForChild(String childName) {
    if (isAnonymous()) {
      return childName;
    }
    return mFullName + mDelineator + childName;
  }

  private boolean isAnonymous() {
    return mName == null;
  }

  @Override
  public String toString() {
    return mFullName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TypedKeyNamespace that = (TypedKeyNamespace) o;

    if (!mDelineator.equals(that.mDelineator)) return false;
    if (mParent != null ? !mParent.equals(that.mParent) : that.mParent != null) return false;
    return mName != null ? mName.equals(that.mName) : that.mName == null;

  }

  @Override
  public int hashCode() {
    int result = mDelineator.hashCode();
    result = 31 * result + (mParent != null ? mParent.hashCode() : 0);
    result = 31 * result + (mName != null ? mName.hashCode() : 0);
    return result;
  }
}
