package com.episode6.hackit.typed.core;

import com.episode6.hackit.typed.core.util.Preconditions;
import com.episode6.hackit.typed.core.util.Supplier;
import com.episode6.hackit.typed.core.util.Suppliers;

import javax.annotation.Nullable;

public class TypedKeyNamespace {
  private final String mDelineator;

  @Nullable private final TypedKeyNamespace mParent;
  @Nullable private final String mName;

  private final Supplier<String> mFullNameSupplier;

  TypedKeyNamespace(String delineator) {
    mDelineator = Preconditions.checkNotNull(delineator);
    mParent = null;
    mName = null;
    mFullNameSupplier = Suppliers.memoize(new FullNameSupplier());
  }

  TypedKeyNamespace(TypedKeyNamespace parent, String childName) {
    mParent = Preconditions.checkNotNull(parent);
    mDelineator = parent.mDelineator;
    mName = Preconditions.checkNotNull(childName);
    mFullNameSupplier = Suppliers.memoize(new FullNameSupplier());
  }

  public String getNameForChild(String childName) {
    if (isAnonymous()) {
      return childName;
    }
    return mFullNameSupplier.get() + mDelineator + childName;
  }

  private boolean isAnonymous() {
    return mName == null;
  }

  @Override
  public String toString() {
    return mFullNameSupplier.get();
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

  private class FullNameSupplier implements Supplier<String> {

    @Override
    public String get() {
      if (isAnonymous()) {
        return "";
      }
      if (mParent == null) {
        return mName;
      }
      return mParent.getNameForChild(mName);
    }
  }
}
