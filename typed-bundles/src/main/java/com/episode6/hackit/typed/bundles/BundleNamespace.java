package com.episode6.hackit.typed.bundles;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.SparseArray;
import com.episode6.hackit.typed.core.TypedKeyName;
import com.episode6.hackit.typed.core.TypedKeyNamespace;
import com.episode6.hackit.typed.core.util.InstanceSupplier;
import com.episode6.hackit.typed.core.util.Preconditions;
import com.episode6.hackit.typed.core.util.Supplier;
import com.google.gson.reflect.TypeToken;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 *
 */
public class BundleNamespace extends TypedKeyNamespace {

  private static final String DELINEATER = ".";

  public static final BundleNamespace ANONYMOUS = new BundleNamespace();

  public static final BundleNamespace fromClass(Class<?> classNamespace) {
    String nameSpaceName = classNamespace.getName().replace("$", DELINEATER);
    return ANONYMOUS.extend(nameSpaceName);
  }

  private BundleNamespace() {
    super(DELINEATER);
  }

  private BundleNamespace(TypedKeyNamespace parent, String childName) {
    super(parent, childName);
  }

  public BundleNamespace extend(String newNameSegment) {
    return new BundleNamespace(this, newNameSegment);
  }

  public <T> KeyBuilder<T> key(Class<T> keyType) {
    return new KeyBuilder<T>(this, keyType, DirectBundleTranslators.getDirectTranslator(keyType));
  }

  public <T> KeyBuilder<T> key(TypeToken<T> keyType) {
    return new KeyBuilder<T>(this, keyType.getType(), null);
  }

  public <T extends Parcelable> KeyBuilder<T> parcelableKey(Class<T> keyType) {
    return new KeyBuilder<T>(this, keyType, CustomBundleTranslators.PARCELABLE);
  }

  public <T extends Parcelable> KeyBuilder<ArrayList<T>> parcelableArrayKey(Class<T> itemType) {
    return new KeyBuilder<ArrayList<T>>(this, ArrayList.class, CustomBundleTranslators.PARCELABLE_ARRAY);
  }

  public <T extends Parcelable> KeyBuilder<ArrayList<T>> parcelableArrayListKey(Class<T> itemType) {
    return new KeyBuilder<ArrayList<T>>(this, ArrayList.class, CustomBundleTranslators.PARCELABLE_ARRAY_LIST);
  }

  public <T extends Parcelable> KeyBuilder<SparseArray<T>> sparseParcelableArrayKey(Class<T> itemType) {
    return new KeyBuilder<SparseArray<T>>(this, SparseArray.class, CustomBundleTranslators.PARCELABLE_SPARSE_ARRAY_LIST);
  }

  public <T extends Serializable> KeyBuilder<T> serializableKey(Class<T> keyType) {
    return new KeyBuilder<T>(this, keyType, CustomBundleTranslators.SERIALIZABLE);
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
  public <T extends IBinder> KeyBuilder<T> binderKey(Class<T> keyType) {
    assertSdkAtLeast(Build.VERSION_CODES.JELLY_BEAN_MR2);
    return new KeyBuilder<T>(this, keyType, CustomBundleTranslators.IBINDER);
  }

  public static class KeyBuilder<V> {
    private final BundleNamespace mNamespace;
    private final Type mObjectType;
    private final @Nullable BundleTranslator mTranslator;

    private @Nullable String mName;

    private KeyBuilder(
        BundleNamespace namespace,
        Type objectType,
        @Nullable BundleTranslator translator) {
      mNamespace = namespace;
      mObjectType = objectType;
      mTranslator = translator;
    }

    public KeyBuilder<V> named(String name) {
      mName = name;
      return this;
    }

    public BundleKey<V> buildWithDefault(V defaultInstance) {
      Preconditions.checkNotNull(defaultInstance);
      return buildWithDefault(new InstanceSupplier<V>(defaultInstance));
    }

    public BundleKey<V> buildWithDefault(Supplier<V> defaultInstanceSupplier) {
      Preconditions.checkNotNull(defaultInstanceSupplier);
      return new BundleKey<V>(
          new TypedKeyName(mNamespace, mName),
          mObjectType,
          mTranslator,
          defaultInstanceSupplier);
    }

    public ReqBundleKey<V> buildRequired() {
      return new ReqBundleKey<V>(
          new TypedKeyName(mNamespace, mName),
          mObjectType,
          mTranslator);
    }

    public OptBundleKey<V> buildOptional() {
      return new OptBundleKey<V>(
          new TypedKeyName(mNamespace, mName),
          mObjectType,
          mTranslator);
    }
  }

  private static void assertSdkAtLeast(int sdkInt) {
    if (Build.VERSION.SDK_INT < sdkInt) {
      throw new NoSuchMethodError(
          "Illegal use of newer api feature, android api should be >= " + sdkInt + " to use this feature.");
    }
  }
}
