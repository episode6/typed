package com.episode6.hackit.typed.preferences;

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
public class PrefNamespace extends TypedKeyNamespace {

  private static final String DELINEATER = "/";

  public static PrefNamespace ROOT = new PrefNamespace();

  private PrefNamespace() {
    super(DELINEATER);
  }

  private PrefNamespace(TypedKeyNamespace parent, String childName) {
    super(parent, childName);
  }

  public PrefNamespace extend(String newNameSegment) {
    return new PrefNamespace(this, newNameSegment);
  }

  public <T> KeyBuilder<T> key(Class<T> keyType) {
    return new KeyBuilder<T>(this, keyType);
  }

  public <T> KeyBuilder<T> key(TypeToken<T> keyType) {
    return new KeyBuilder<T>(this, keyType.getType());
  }

  public static class KeyBuilder<V> {
    private final PrefNamespace mNamespace;
    private final Type mObjectType;

    private @Nullable String mName;

    private KeyBuilder(PrefNamespace namespace, Type objectType) {
      mNamespace = namespace;
      mObjectType = objectType;
    }

    public KeyBuilder<V> named(String name) {
      mName = name;
      return this;
    }

    public PrefKey<V> buildWithDefault(V defaultInstance) {
      Preconditions.checkNotNull(defaultInstance);
      return buildWithDefault(new InstanceSupplier<V>(defaultInstance));
    }

    public PrefKey<V> buildWithDefault(Supplier<V> defaultInstanceSupplier) {
      Preconditions.checkNotNull(defaultInstanceSupplier);
      return new PrefKey<V>(
          new TypedKeyName(mNamespace, mName),
          mObjectType,
          defaultInstanceSupplier);
    }

    public OptPrefKey<V> buildNullable() {
      return new OptPrefKey<V>(
          new TypedKeyName(mNamespace, mName),
          mObjectType);
    }
  }
}
