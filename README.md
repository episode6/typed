# Typed! (for Android)
Bring sanity to android's key-value stores by defining your keys with types and defaults, so your call-sites don't have to.

#### Installation
```groovy
repositories { maven { url "https://oss.sonatype.org/content/repositories/snapshots/" } }
dependencies {
    // typed! preferences
    compile 'com.episode6.hackit.typed:typed-preferences:0.0.5-SNAPSHOT'

    // typed! bundles
    compile 'com.episode6.hackit.typed:typed-bundles:0.0.5-SNAPSHOT'
}
```

## Documentation
This project currently offers two typed! wrappers for Android. See their respective READMEs for implementation instructions.

- [typed-preferences](typed-preferences/README.md): A typed! wrapper for android's SharedPreferences.
- [typed-bundles](typed-bundles/README.md): A typed!  wrapper for android's Bundle object.

## Why?
Typed! (for Android) has 3 primary objectives...

#### 1) Documentation through code
If you've ever worked on a large team, you've probably run into a scenario where you need to launch a pre-existing activity or modify an existing preference and found yourself staring at some mysterious keys for one of android's key-value stores...
```java
// WHAT DOES IT MEAN!?
public static final String PREF_KEY_DOUBLE_RAINBOW = "double_rainbow";
public static final String EXTRA_DOUBLE_RAINBOW = "activity_extra_double_rainbow";
```
These... Are... Useless! Unless these keys have some really good documentation to go with them, they tell us absolutely nothing about what to do with them. Our only option is to go hunting around the codebase to find another call-site that already uses them, and hope we're not copy-pasting a mistake.

With Typed! we turn those basic key-strings into typed generic objects
```java
// NOTE: See individual project docs for full documentation

// normal: can never be null becuase a default is defined
public static final PrefKey<String> PREF_KEY_DOUBLE_RAINBOW = PREFS.key(String.class)
    .named("doubleRainbow")
    .buildWithDefault("none");

// optional: can be null because defined as optional
public static final OptPrefKey<String> PREF_KEY_OPTIONAL_RAINBOW = PREFS.key(String.class)
    .named("optionalRainbow")
    .buildOptional();

// required: exception will be thrown if this property is missing when trying to read it
public static final ReqBundleKey<String> EXTRA_DOUBLE_RAINBOW = EXTRAS.key(String.class)
    .named("DoubleRainbow")
    .buildRequired();
```
While these key definitions are a bit more verbose, we're now forced to document everything we need to know to interact with them, including the type of object they represent (in this case Strings), their default value (if any) and if they're required to be non-null (in the case of bundle keys). Awesome! But we're still stuck with some Strings that are supposed to represent rainbows somehow, and that still makes no damn sense...

#### 2) Automatic object serialization with [Gson](https://github.com/google/gson)
Android's shared preferences only support basic primitive types. Android's bundles support a few more, but still with many restrictions. But thanks to Google's [Gson](https://github.com/google/gson), we can translate almost any object into a String and back again. This allows us to clear up the above example even further using a concrete java class...
```java
public static final PrefKey<DoubleRainbow> PREF_KEY_DOUBLE_RAINBOW = PREFS.key(DoubleRainbow.class)
    .named("doubleRainbow")
    .buildWithDefault(new Supplier<DoubleRainbow>() {
        public DoubleRainbow get() {
            return new DoubleRainbow();
        }
    });

public static final OptPrefKey<DoubleRainbow> PREF_KEY_OPTIONAL_RAINBOW = PREFS.key(DoubleRainbow.class)
    .named("optionalRainbow")
    .buildOptional();

// Note: if your object already implements Parcelable or Serializable, you can still utilize
// the more optimized translation with BundleKeys, see the typed-bundles readme for details.
public static final ReqBundleKey<DoubleRainbow> EXTRA_DOUBLE_RAINBOW = EXTRAS.key(DoubleRainbow.class)
    .named("DoubleRainbow")
    .buildRequired();
```
There's nothing special about our pretend DoubleRainbow class here except that it is a concrete class (not an interface or abstract class).

Note: you can still use interfaces and abstracts (i.e. [auto-value](https://github.com/google/auto/tree/master/value) and [Immutables](https://immutables.github.io/)) with typed! by providing your own custom gson instance to each typed! module. See module docs for details.

#### 3) Simplified call-site access
Now that our keys expose the types they represent (and they're nullability) in their signatures, our call-sites become much simpler...

SharedPreferences
```java
TypedPrefs prefs = TypedPrefs.Wrap.defaultSharedPrefs(getContext());

// write to shared prefs
prefs.edit()
    .put(PREF_KEY_USER_INFO, new UserInfo(/* some data */))
    .put(PREF_KEY_USER_NOTE, "some note")
    .commit();

// read from shared prefs
UserInfo userInfo = prefs.get(PREF_KEY_USER_INFO);

// when reading an OptPrefKey, a @Nullable will be returned
@Nullable String userNote = prefs.get(PREF_KEY_USER_NOTE);
```

Bundles
```java
// write to a bundle
Bundle bundle = TypedBundles.create()
    .put(EXTRA_USER_INFO, new UserInfo(/* some data */))
    .asBundle();

// read from a bundle
TypedBundle typedBundle = TypedBundles.wrap(bundle);
UserInfo userInfo = typedBundle.get(EXTRA_USER_INFO);
```

Our call-sites no-longer need to know how an object is written to these key-value stores, as the object translation is all defined as part of the key. Only the type and nullability matter, which are now all verifiable at compile-time. This keeps our functional code clean and should make it a bit easier for other devs to interact with our components.

## License
MIT: https://github.com/episode6/typed/blob/master/LICENSE