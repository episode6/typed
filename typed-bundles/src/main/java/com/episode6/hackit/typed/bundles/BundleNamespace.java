package com.episode6.hackit.typed.bundles;

import com.episode6.hackit.typed.core.TypedKeyName;
import com.episode6.hackit.typed.core.TypedKeyNamespace;
import com.episode6.hackit.typed.core.util.InstanceSupplier;
import com.episode6.hackit.typed.core.util.Preconditions;
import com.episode6.hackit.typed.core.util.Supplier;
import com.google.gson.reflect.TypeToken;

import javax.annotation.Nullable;
import java.lang.reflect.Type;

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
    return new KeyBuilder<T>(this, keyType);
  }

  public <T> KeyBuilder<T> key(TypeToken<T> keyType) {
    return new KeyBuilder<T>(this, keyType.getType());
  }

  public static class KeyBuilder<V> {
    private final BundleNamespace mNamespace;
    private final Type mObjectType;

    private @Nullable String mName;

    private KeyBuilder(BundleNamespace namespace, Type objectType) {
      mNamespace = namespace;
      mObjectType = objectType;
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
          defaultInstanceSupplier);
    }

    public NullableBundleKey<V> buildNullable() {
      return new NullableBundleKey<V>(
          new TypedKeyName(mNamespace, mName),
          mObjectType);
    }
  }
}
