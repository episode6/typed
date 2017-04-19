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
 * A Namespace under which all {@link PrefKey}s and {@link OptPrefKey}s exist. Each module or feature
 * in your project can have its own namespace, from which its keys extend. This reduces the chances of a
 * naming collision in large projects.
 */
public class PrefNamespace extends TypedKeyNamespace {

  private static final String DELINEATER = "/";

  /**
   * The root namespace for all preferences.
   */
  public static PrefNamespace ROOT = new PrefNamespace();

  private PrefNamespace() {
    super(DELINEATER);
  }

  private PrefNamespace(TypedKeyNamespace parent, String childName) {
    super(parent, childName);
  }

  /**
   * Create a new sub-namespace
   * @param newNameSegment The name of the new sub-namespace
   * @return The new sub-namespace
   */
  public PrefNamespace extend(String newNameSegment) {
    return new PrefNamespace(this, newNameSegment);
  }

  /**
   * Create a key that represents a shared preference
   * @param keyType The type of object that this key represents
   * @param <T> The type of object that this key represents
   * @return a typed {@link KeyBuilder} from which you can build your {@link PrefKey} or {@link OptPrefKey}
   */
  public <T> KeyBuilder<T> key(Class<T> keyType) {
    return new KeyBuilder<T>(this, keyType);
  }

  /**
   * Create a key that represents a shared preference of a generic type (ex: {@code ArrayList<String>})
   * @param keyType The TypeToken for the type of object that this key represents
   * @param <T> The type of object that this key represents
   * @return a typed {@link KeyBuilder} from which you can build your {@link PrefKey} or {@link OptPrefKey}
   */
  public <T> KeyBuilder<T> key(TypeToken<T> keyType) {
    return new KeyBuilder<T>(this, keyType.getType());
  }

  /**
   * A Builder class used for creating {@link PrefKey}s and {@link OptPrefKey}s
   * @param <V> The type of object for the key we are creating
   */
  public static class KeyBuilder<V> {
    private final PrefNamespace mNamespace;
    private final Type mObjectType;

    private @Nullable String mName;

    private KeyBuilder(PrefNamespace namespace, Type objectType) {
      mNamespace = namespace;
      mObjectType = objectType;
    }

    /**
     * Define a name for this key. The name will be appended to the {@link PrefNamespace} this KeyBuilder was
     * created by.
     * @param name The name for this key
     * @return this KeyBuilder
     */
    public KeyBuilder<V> named(String name) {
      mName = name;
      return this;
    }

    /**
     * Builds a {@link PrefKey} with the supplied default value.
     * @param defaultInstance The default value to be returned when this key has not been set
     *                        in {@link android.content.SharedPreferences}. Only use this method
     *                        if your key is of a Primitive type.
     * @return A new {@link PrefKey}
     */
    public PrefKey<V> buildWithDefault(V defaultInstance) {
      Preconditions.checkNotNull(defaultInstance);
      return buildWithDefault(new InstanceSupplier<V>(defaultInstance));
    }

    /**
     * Builds a {@link PrefKey} with the supplied default value.
     * @param defaultInstanceSupplier A supplier for the default value to be returned when this key has not been set
     *                                in {@link android.content.SharedPreferences}. Use this method if your key is
     *                                not a Primitive type.
     * @return A new {@link PrefKey}
     */
    public PrefKey<V> buildWithDefault(Supplier<V> defaultInstanceSupplier) {
      Preconditions.checkNotNull(defaultInstanceSupplier);
      return new PrefKey<V>(
          new TypedKeyName(mNamespace, mName),
          mObjectType,
          defaultInstanceSupplier);
    }

    /**
     * Builds an {@link OptPrefKey} with no default value (default value is null).
     * @return a new {@link OptPrefKey}
     */
    public OptPrefKey<V> buildNullable() {
      return new OptPrefKey<V>(
          new TypedKeyName(mNamespace, mName),
          mObjectType);
    }
  }
}
