# Typed! Bundles (for Android)
A typed! wrapper for android's Bundle object that simplifies callsite access to extras and arguments.

[Installation Instructions](README.md)

#### Define your keys
```java
static class Extras {

    // Namespaces allow different modules of your app to avoid naming collisions.
    // A namespace created with fromClass() will prefix all your key names with the
    // fully-qualified class name.
    private static final BundleNamespace EXTRAS = BundleNamespace.fromClass(Extras.class);

    // A BundleKey always has a default value.
    public static final BundleKey<String> USER_NAME = EXTRAS.key(String.class)
        .named("UserName")
        .buildWithDefault("Player 1");

    // An OptBundleKey (optional) has a default value of null
    public static final OptBundleKey<CharSequence> USER_TAG = EXTRAS.key(CharSequence.class)
        .named("UserTag")
        .buildOptional();

    // A ReqBundleKey (required) has no default value, but an exception will be thrown
    // from TypedBundle.get(ReqBundleKey) if the key does not exist in the bundle
    public static final ReqBundleKey<Long> USER_ID = EXTRAS.key(Long.class)
        .named("UserId")
        .buildRequired();

    // Custom java objects (POJOs) may be used as key types, they will be serialized via gson
    public static final BundleKey<CustomJavaPojo> CUSTOM_POJO = EXTRAS.key(CustomJavaPojo.class)
        .named("CustomPojo")
        .buildWithDefault(new Supplier<CustomPojo>() {
            public CustomPojo get() {
                return new CustomPojo();
            }
        });

    // Generics can also be used as key types, and serialized via gson if a TypeToken
    // is used to create the key.
    public static final BundleKey<HashMap<CustomKey, CustomObj>> CUSTOM_HASHMAP = EXTRAS
        .key(new TypeToken<HashMap<CustomKey, CustomObj>>() {})
        .named("CustomHashMap")
        .buildWithDefault(/* ... */);
}
```

#### Write to a TypedBundle
```java
// values are all type-checked at compile-time
Bundle bundle = TypedBundles.create()
    .put(MyActivity.Extras.USER_NAME, "Wade Wilson")
    .put(MyActivity.Extras.USER_TAG, "Maximum Effort")
    .put(MyActivity.Extras.USER_ID, 9823233L)
    .put(MyActivity.Extras.CUSTOM_POJO, new CustomPojo())
    .asBundle();
```

#### Read from a TypedBundle
```java
public void readFromBundle(Bundle bundle) {
    TypedBundle typedBundle = TypedBundles.wrap(bundle);

    // should never be null
    String userName = typedBundle.get(Extras.USER_NAME);

    // can be null becuase USER_TAG is an OptBundleKey
    @Nullable CharSequence userTag = typedBundle.get(Extras.USER_TAG);

    // An exception will be thrown here if user_id is missing from the bundle.
    // Because null should never be returned, its safe to auto-unbox the returned Long.
    long userId = typedBundle.get(Extras.USER_ID);

    // Read a serialized object just like anything else.
    CustomPojo pojo = typedBundle.get(Extras.CUSTOM_POJO);
}
```

#### Directly Translated Keys
Most of the BundleKeys you'll create are what we call "directly translated." These include boxed primitives (Integer, String, Long, etc.) as well as a few android-specific classes (CharSequence, Size, Bundle). A directly translated key will use Bundle's built in methods to read and write that specific type of data (i.e. getInt()/putInt(), getString()/putString(), getBundle()/putBundle() etc.). This ensures your TypedBundles will remain compatible with 3rd-party apps and services that expect extras to be written in a specific fashion.

You don't need to do anything special to create a directly-translated key, simply use BundleNamespace.key(Class) to create your key. See [DirectBundleTranslators.java](src/main/java/com/episode6/hackit/typed/bundles/DirectBundleTranslators.java) for all supported direct translators. If a direct-translator is not found for your key's type, gson will be used for serialization.

#### Custom Translated Keys
While direct translation handles translating most of the simple types, Android's Bundle still supports several other methods of passing around data including Parcelables, Serializables, arrays and array-lists. While gson could handle most, if not all of these cases, the built-in support will often be more efficient and sometimes even required (if interacting with 3rd-party services). To handle these cases you can create a custom translated key by using one of the alternative BundleNamespace.*key() methods. For example...
 ```java

// Bundle.getParcelable/putParcelable methods will be used to read/write these objects
// Compile-time type-checking ensure MyParcelable implements Parcelable.
static final BundleKey<MyParcelable> MY_PARCELABLE = EXTRAS.parcelableKey(MyParcelable.class)
    .named("MyParcelable")
    .buildWithDefault(/* ... */);

// Bundle.getSerializable/putSerializable methods will be used to read/write these objects
// Compile-time type-checking ensure MySerializable implements Serializable.
static final BundleKey<MySerializable> MY_SERIALIZABLE = EXTRAS.serializableKey(MySerializable.class)
    .named("MySerializable")
    .buildWithDefault(/* ... */);

// Bundle.getStringArrayList/putStringArrayList methods will be used to read/write these objects.
static final BundleKey<ArrayList<String>> STRING_ARRAY_LIST = EXTRAS.stringArrayListKey()
    .named("StringArrayList")
    .buildWithDefault(/* ... */);
 ```

#### Handling Arrays
Because Java won't let us use an array as a Generic sub-type, typed! bundles does not directly support working with arrays. However, when dealing with 3rd-party apps & services, passing arrays in a bundle may be required. To deal with this, we've added custom translated keys that spit-out ArrayLists to the caller, but read and write to arrays under the hood. For example...
```java
// While the signature of this key is identical to STRING_ARRAY_LIST mentioned above,
// notice here we use the method stringArrayKey() instead of stringArrayListKey().
// That means the ArrayList<String> will be converted to/from a String[] array
// and read/written to the bundle using the Bundle.getStringArray/putStringArray methods.
static final BundleKey<ArrayList<String>> STRING_ARRAY = EXTRAS.stringArrayKey()
    .named("StringArray")
    .buildWithDefault(/* ... */);
```

See [BundleNamespace.java](src/main/java/com/episode6/hackit/typed/bundles/BundleNamespace.java) for more supported key-builder methods.

#### Customizing Gson
If the your key-types require custom TypeAdapters in order for gson to serialize/deserialize them, you can set the default instance using `TypedBundles.setDefaultGson(Gson)` or `TypedBundles.setDefaultGsonSupplier(Supplier<Gson>)`. However, if using typed! bundles from within a library project, you may prefer to initialize your own TypedBundle.Factory via `TypedBundles.createFactory(Gson)`. The factory may be stored statically, and used to create TypedBundles using your custom gson instance (and it can't be overridden inadvertently by the implementing application).

#### Extra Utilities
- `TypedBundles.memoize(TypedBundle)` provides a TypedBundle that memoizes its contents as they're read and written. This can be useful for dealing with a Fragment's arguments from inside said fragment, as you'll avoid unnecessary deserialization.
- `FragmentBuilder<V extends Fragment>` is a utility class for creating a fragment using a TypedBundle for arguments, potentially bypassing the need for a boilerplate static newInstance method.
