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
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * A namespace under which all {@link BundleKey}s, {@link OptBundleKey}s and {@link ReqBundleKey}s exist.
 * Usually you'll want to use the {@link #fromClass(Class)} to create namespace for your class, however if
 * you need to deal with custom-named bundle extras/arguments from other apps/components, you can extend or
 * build keys from the {@link #ANONYMOUS} namespace.
 */
public class BundleNamespace extends TypedKeyNamespace {

  private static final String DELINEATER = ".";

  /**
   * An anonymous {@link BundleNamespace}. Start here if you need to handle arguments
   * with custom names.
   */
  public static final BundleNamespace ANONYMOUS = new BundleNamespace();

  /**
   * Create a Namespace named after the provided class. All keys created from this
   * namespace will be prefixed with the class name.
   * @param classNamespace The class to name this namespace after
   * @return The new namespace.
   */
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

  /**
   * Create a new namespace by adding .newNameSegment to the end of this namespace.
   * @param newNameSegment The new segment to add to the namespace ('.' delineater will be added)
   * @return The new sub namespace.
   */
  public BundleNamespace extend(String newNameSegment) {
    return new BundleNamespace(this, newNameSegment);
  }

  /**
   * Start building a new key for any type of object that isn't generic. If {@link android.os.Bundle} has a special
   * method to handle keyType, it will be used to get and set instances. Otherwise the object(s) will be translated
   * via gson, and stored in bundles as strings.
   * @param keyType The type of object this key represents
   * @param <T> The type of object this key represents
   * @return A new {@link KeyBuilder}, with which to build the new key.
   */
  public <T> KeyBuilder<T> key(Class<T> keyType) {
    return new KeyBuilder<T>(this, keyType, DirectBundleTranslators.getDirectTranslator(keyType));
  }

  /**
   * Start building a new key for any type of generic object. The object(s) will be translated via gson
   * and stored in bundles as strings.
   * @param keyType A {@link TypeToken} for the type of object this key represents
   * @param <T> The type of object this key represents
   * @return A new {@link KeyBuilder}, with which to build the new key.
   */
  public <T> KeyBuilder<T> key(TypeToken<T> keyType) {
    return new KeyBuilder<T>(this, keyType.getType(), null);
  }

  /**
   * Start building a new key for an object that implements {@link Parcelable}. {@link android.os.Bundle}'s built in
   * Parcelable methods will be used to read and write the objects.
   * @param keyType The type of object this key represents
   * @param <T> The type of object this key represents
   * @return A new {@link KeyBuilder}, with which to build the new key.
   */
  public <T extends Parcelable> KeyBuilder<T> parcelableKey(Class<T> keyType) {
    return new KeyBuilder<T>(this, keyType, CustomBundleTranslators.PARCELABLE);
  }

  /**
   * Start building a new key for an ArrayList&lt;Parcelable&gt;. The ArrayList will be translated to/from
   * a Parcelable[], and the methods {@link android.os.Bundle#putParcelableArray(String, Parcelable[])}
   * and {@link android.os.Bundle#getParcelableArray(String)} will be used to write the list to the bundle.
   *
   * This creates a less-than-efficient key, and is only intended for use with 3rd-party components that
   * require raw arrays be used. Prefer {@link #parcelableArrayListKey(Class)} for new code.
   *
   * @param itemType The type of Parcelable that the array list should contain
   * @param <T> The type of Parcelable that the array list should contain
   * @return A new {@link KeyBuilder}, with which to build the new key.
   */
  public <T extends Parcelable> KeyBuilder<ArrayList<T>> parcelableArrayKey(Class<T> itemType) {
    return new KeyBuilder<ArrayList<T>>(this, ArrayList.class, CustomBundleTranslators.PARCELABLE_ARRAY);
  }

  /**
   * Start building a new key for an ArrayList&lt;Parcelable&gt;. {@link android.os.Bundle}'s built in
   * parcelableArrayList methods will be used to read/write this list to bundles.
   * @param itemType The type of Parcelable that the array list should contain
   * @param <T> The type of Parcelable that the array list should contain
   * @return A new {@link KeyBuilder}, with which to build the new key.
   */
  public <T extends Parcelable> KeyBuilder<ArrayList<T>> parcelableArrayListKey(Class<T> itemType) {
    return new KeyBuilder<ArrayList<T>>(this, ArrayList.class, CustomBundleTranslators.PARCELABLE_ARRAY_LIST);
  }

  /**
   * Start building a new key for a SparseArray&lt;Parcelable&gt;. {@link android.os.Bundle}'s built in
   * sparseParcelableArray methods will be used to read/write this list to bundles.
   * @param itemType The type of Parcelable that the array should contain
   * @param <T> The type of Parcelable that the array should contain
   * @return A new {@link KeyBuilder}, with which to build the new key.
   */
  public <T extends Parcelable> KeyBuilder<SparseArray<T>> sparseParcelableArrayKey(Class<T> itemType) {
    return new KeyBuilder<SparseArray<T>>(this, SparseArray.class, CustomBundleTranslators.PARCELABLE_SPARSE_ARRAY_LIST);
  }

  /**
   * Start building a new key for an object that implements {@link Serializable}. {@link android.os.Bundle}'s built in
   * Serializable methods will be used to read and write the objects.
   * @param keyType The type of object this key represents
   * @param <T> The type of object this key represents
   * @return A new {@link KeyBuilder}, with which to build the new key.
   */
  public <T extends Serializable> KeyBuilder<T> serializableKey(Class<T> keyType) {
    return new KeyBuilder<T>(this, keyType, CustomBundleTranslators.SERIALIZABLE);
  }

  /**
   * Start building a new key for an object that implements {@link IBinder}. {@link android.os.Bundle}'s built in
   * Binder methods will be used to read and write the objects.
   * @param keyType The type of object this key represents
   * @param <T> The type of object this key represents
   * @return A new {@link KeyBuilder}, with which to build the new key.
   */
  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
  public <T extends IBinder> KeyBuilder<T> binderKey(Class<T> keyType) {
    assertSdkAtLeast(Build.VERSION_CODES.JELLY_BEAN_MR2);
    return new KeyBuilder<T>(this, keyType, CustomBundleTranslators.IBINDER);
  }

  /**
   * Start building a new key for an ArrayList&lt;Boolean&gt;. The ArrayList will be translated to/from
   * a boolean[], and the methods {@link android.os.Bundle#putBooleanArray(String, boolean[])}
   * and {@link android.os.Bundle#getBooleanArray(String)} will be used to write the list to the bundle.
   * @return A new {@link KeyBuilder}, with which to build the new key.
   */
  public KeyBuilder<ArrayList<Boolean>> boolArrayKey() {
    return new KeyBuilder<ArrayList<Boolean>>(this, ArrayList.class, CustomBundleTranslators.BOOL_ARRAY);
  }

  /**
   * Start building a new key for an ArrayList&lt;Integer&gt;. The ArrayList will be translated to/from
   * a int[], and the methods {@link android.os.Bundle#putIntArray(String, int[])}
   * and {@link android.os.Bundle#getIntArray(String)} will be used to write the list to the bundle.
   *
   * This creates a less-than-efficient key, and is only intended for use with 3rd-party components that
   * require raw arrays be used. Prefer {@link #intArrayListKey()} for new code.
   *
   * @return A new {@link KeyBuilder}, with which to build the new key.
   */
  public KeyBuilder<ArrayList<Integer>> intArrayKey() {
    return new KeyBuilder<ArrayList<Integer>>(this, ArrayList.class, CustomBundleTranslators.INT_ARRAY);
  }

  /**
   * Start building a new key for an ArrayList&lt;Integer&gt;. {@link android.os.Bundle}'s built in
   * integerArrayList methods will be used to read/write this list to bundles.
   * @return A new {@link KeyBuilder}, with which to build the new key.
   */
  public KeyBuilder<ArrayList<Integer>> intArrayListKey() {
    return new KeyBuilder<ArrayList<Integer>>(this, ArrayList.class, CustomBundleTranslators.INT_ARRAY_LIST);
  }

  /**
   * Start building a new key for an ArrayList&lt;String&gt;. The ArrayList will be translated to/from
   * a String[], and the methods {@link android.os.Bundle#putStringArray(String, String[])}
   * and {@link android.os.Bundle#getStringArray(String)} will be used to write the list to the bundle.
   *
   * This creates a less-than-efficient key, and is only intended for use with 3rd-party components that
   * require raw arrays be used. Prefer {@link #stringArrayListKey()} for new code.
   *
   * @return A new {@link KeyBuilder}, with which to build the new key.
   */
  public KeyBuilder<ArrayList<String>> stringArrayKey() {
    return new KeyBuilder<ArrayList<String>>(this, ArrayList.class, CustomBundleTranslators.STRING_ARRAY);
  }

  /**
   * Start building a new key for an ArrayList&lt;String&gt;. {@link android.os.Bundle}'s built in
   * stringArrayList methods will be used to read/write this list to bundles.
   * @return A new {@link KeyBuilder}, with which to build the new key.
   */
  public KeyBuilder<ArrayList<String>> stringArrayListKey() {
    return new KeyBuilder<ArrayList<String>>(this, ArrayList.class, CustomBundleTranslators.STRING_ARRAY_LIST);
  }

  /**
   * Start building a new key for a ArrayList&lt;Float&gt;. The ArrayList will be translated to/from
   * a float[], and the methods {@link android.os.Bundle#putFloatArray(String, float[])}
   * and {@link android.os.Bundle#getFloatArray(String)} will be used to read/write the list to the bundle.
   * @return A new {@link KeyBuilder}, with which to build the new key.
   */
  public KeyBuilder<ArrayList<Float>> floatArrayKey() {
    return new KeyBuilder<ArrayList<Float>>(this, ArrayList.class, CustomBundleTranslators.FLOAT_ARRAY);
  }

  /**
   * Start building a new key for an ArrayList&lt;Long&gt;. The ArrayList will be translated to/from
   * a long[], and the methods {@link android.os.Bundle#putLongArray(String, long[])}
   * and {@link android.os.Bundle#getLongArray(String)}  will be used to read/write the list to the bundle.
   * @return A new {@link KeyBuilder}, with which to build the new key.
   */
  public KeyBuilder<ArrayList<Long>> longArrayKey() {
    return new KeyBuilder<ArrayList<Long>>(this, ArrayList.class, CustomBundleTranslators.LONG_ARRAY);
  }

  /**
   * Start building a new key for an ArrayList&lt;Double&gt;. The ArrayList will be translated to/from
   * a double[], and the methods {@link android.os.Bundle#putDoubleArray(String, double[])}
   * and {@link android.os.Bundle#getDoubleArray(String)} will be used to read/write the list to the bundle.
   * @return A new {@link KeyBuilder}, with which to build the new key.
   */
  public KeyBuilder<ArrayList<Double>> doubleArrayKey() {
    return new KeyBuilder<ArrayList<Double>>(this, ArrayList.class, CustomBundleTranslators.DOUBLE_ARRAY);
  }

  /**
   * Start building a new key for an ArrayList&lt;Short&gt;. The ArrayList will be translated to/from
   * a short[], and the methods {@link android.os.Bundle#putShortArray(String, short[])}
   * and {@link android.os.Bundle#getShortArray(String)} will be used to read/write the list to the bundle.
   * @return A new {@link KeyBuilder}, with which to build the new key.
   */
  public KeyBuilder<ArrayList<Short>> shortArrayKey() {
    return new KeyBuilder<ArrayList<Short>>(this, ArrayList.class, CustomBundleTranslators.SHORT_ARRAY);
  }

  /**
   * Start building a new key for an ArrayList&lt;Character&gt;. The ArrayList will be translated to/from
   * a char[], and the methods {@link android.os.Bundle#putCharArray(String, char[])}
   * and {@link android.os.Bundle#getCharArray(String)} will be used to read/write the list to the bundle.
   * @return A new {@link KeyBuilder}, with which to build the new key.
   */
  public KeyBuilder<ArrayList<Character>> charArrayKey() {
    return new KeyBuilder<ArrayList<Character>>(this, ArrayList.class, CustomBundleTranslators.CHAR_ARRAY);
  }

  /**
   * Start building a new key for a {@link ByteBuffer}. The buffer will be translated to/from
   * a byte[], and the method {@link android.os.Bundle#putByteArray(String, byte[])}
   * and {@link android.os.Bundle#getByteArray(String)} will be used to read/write the array to bundles.
   * @return
   */
  public KeyBuilder<ByteBuffer> byteArrayKey() {
    return new KeyBuilder<ByteBuffer>(this, ByteBuffer.class, CustomBundleTranslators.BYTE_ARRAY);
  }

  /**
   * Start building a new key for an ArrayList&lt;CharSequence&gt;. The ArrayList will be translated to/from
   * a CharSequence[], and the methods {@link android.os.Bundle#putCharSequenceArray(String, CharSequence[])}
   * and {@link android.os.Bundle#getCharSequenceArray(String)} will be used to write the list to the bundle.
   *
   * This creates a less-than-efficient key, and is only intended for use with 3rd-party components that
   * require raw arrays be used. Prefer {@link #charSequenceArrayListKey()} for new code.
   *
   * @return A new {@link KeyBuilder}, with which to build the new key.
   */
  @TargetApi(Build.VERSION_CODES.FROYO)
  public KeyBuilder<ArrayList<CharSequence>> charSequenceArrayKey() {
    assertSdkAtLeast(Build.VERSION_CODES.FROYO);
    return new KeyBuilder<ArrayList<CharSequence>>(this, ArrayList.class, CustomBundleTranslators.CHAR_SEQUENCE_ARRAY);
  }

  /**
   * Start building a new key for an ArrayList&lt;CharSequence&gt;. {@link android.os.Bundle}'s built in
   * charSequenceArrayList methods will be used to read/write this list to bundles.
   * @return A new {@link KeyBuilder}, with which to build the new key.
   */
  @TargetApi(Build.VERSION_CODES.FROYO)
  public KeyBuilder<ArrayList<CharSequence>> charSequenceArrayListKey() {
    assertSdkAtLeast(Build.VERSION_CODES.FROYO);
    return new KeyBuilder<ArrayList<CharSequence>>(this, ArrayList.class, CustomBundleTranslators.CHAR_SEQUENCE_ARRAY_LIST);
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

    /**
     * Give this key a name
     * @param name name of the new key (will be appendend to the namespace)
     * @return This KeyBuilder
     */
    public KeyBuilder<V> named(String name) {
      mName = name;
      return this;
    }

    /**
     * Build a {@link BundleKey} with a default value.
     *
     * WARNING: you should only use this method with primitive/immutable types.
     * Otherwise prefer {@link #buildWithDefault(Supplier)}.
     *
     * @param defaultInstance The instance to provide as a default.
     * @return The new {@link BundleKey}
     */
    public BundleKey<V> buildWithDefault(V defaultInstance) {
      Preconditions.checkNotNull(defaultInstance);
      return buildWithDefault(new InstanceSupplier<V>(defaultInstance));
    }

    /**
     * Build a {@link BundleKey} with a default value.
     *
     * @param defaultInstanceSupplier A supplier for the instance to provide as a default.
     * @return The new {@link BundleKey}
     */
    public BundleKey<V> buildWithDefault(Supplier<V> defaultInstanceSupplier) {
      Preconditions.checkNotNull(defaultInstanceSupplier);
      return new BundleKey<V>(
          new TypedKeyName(mNamespace, mName),
          mObjectType,
          mTranslator,
          defaultInstanceSupplier);
    }

    /**
     * Build a {@link ReqBundleKey}. This key has no default value, and
     * if you call {@link TypedBundle#get(ReqBundleKey)} where the key is
     * not present in the bundle, an exception will be thrown.
     * @return The new {@link ReqBundleKey}
     */
    public ReqBundleKey<V> buildRequired() {
      return new ReqBundleKey<V>(
          new TypedKeyName(mNamespace, mName),
          mObjectType,
          mTranslator);
    }

    /**
     * Build a {@link OptBundleKey}. This key has no default value, and
     * null can be returned from {@link TypedBundle#get(OptBundleKey)}
     * @return The new {@link OptBundleKey}
     */
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
