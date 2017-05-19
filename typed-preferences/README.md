# Typed Preferences (for Android)

A wrapper around android's SharedPreferences to simplify call-site access to them.

#### Add Dependency
```groovy
repositories { jcenter() }
dependencies {
    compile 'com.episode6.hackit.typed:typed-preferences:0.0.1-SNAPSHOT'
}
```

#### Define your keys
```java
public final class AppPrefs {

  // Namespaces allow different modules of your app to avoid naming collisions.
  public static final PrefNamespace APP_PREFS = PrefNamespace.ROOT.extend("app_prefs");

  // PrefKeys always have a default value defined, and should never return null.
  public static final PrefKey<String> USERNAME = APP_PREFS
      .key(String.class)
      .named("username")
      .buildWithDefault("Player 1");
  public static final PrefKey<Integer> HIT_COUNT = APP_PREFS
      .key(Integer.class)
      .named("hit_count")
      .buildWithDefault(0);

  // OptPrefKeys will default to null.
  public static final OptPrefKey<Long> USER_ID = APP_PREFS
      .key(Long.class)
      .named("user_id")
      .buildOptional();
}
```

#### Read using TypedPrefs
```java
// Wrap an instance of SharedPreferences
TypedPrefs typedPrefs = TypedPrefs.Wrap.sharedPrefs(sharedPrefs);

// No need to null-check these, default values are already defined
// and types are enforced at compile time.
String username = typedPrefs.get(USERNAME);
int hitCount = typedPrefs.get(HIT_COUNT);

// because USER_ID is an OptPrefKey, this method will return a @Nullable
@Nullable Long userId = typedPrefs.get(USER_ID);
```

#### Write using TypedPrefs
```java
typedPrefs.edit()
    .put(USERNAME, "Someone")
    .put(HIT_COUNT, hitCount+1)
    .commit();
```

#### Serialize POJOs with Gson
Typed libraries rely on Gson to serialize and deserialize (non-primitive) plain old java objects
```java
public static final PrefKey<UserProfile> USER_PROFILE = APP_PREFS
  .key(UserProfile.class)
  .named("user_profile")
  .buildWithDefault(new Supplier<UserProfile>() {  // User a Supplier to provide default Objects
    @Override
    public UserProfile get() {
      return new UserProfile();
    }
  });
```

Access POJOs just like primitives
```java
UserProfile profile = typedPrefs.get(USER_PROFILE);

profile.name = "Player 2";
profile.age = 34;
profile.email = "sample@email.com";

typedPrefs.edit()
    .put(USER_PROFILE, profile)
    .commit();
```


## License
MIT: https://github.com/episode6/typed/blob/master/LICENSE