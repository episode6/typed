package com.episode6.hackit.typed.core;

import javax.annotation.Nullable;

public class TypedKeyNamespace {
  private final String mDelineator;

  @Nullable private final TypedKeyNamespace mParent;
  @Nullable private final String mName;

  @Nullable private String mFullName;

  TypedKeyNamespace(String delineator) {
    mDelineator = Preconditions.checkNotNull(delineator);
    mParent = null;
    mName = null;
  }

  TypedKeyNamespace(TypedKeyNamespace parent, String childName) {
    mParent = Preconditions.checkNotNull(parent);
    mDelineator = parent.mDelineator;
    mName = Preconditions.checkNotNull(childName);
  }

  public synchronized String getFullName() {
    if (mFullName == null) {
      mFullName = generateFullName();
    }

    return mFullName;
  }

  public String getNameForChild(String childName) {
    if (isAnonymous()) {
      return childName;
    }
    return getFullName() + mDelineator + childName;
  }

  private boolean isAnonymous() {
    return mName == null;
  }

  private String generateFullName() {
    if (isAnonymous()) {
      return "";
    }
    if (mParent == null) {
      return mName;
    }
    return mParent.getNameForChild(mName);
  }

  @Override
  public String toString() {
    return getFullName();
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
