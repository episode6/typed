# Typed! (for Android)
Bring sanity to android's key-value stores by defining your keys with types and defaults, so your call-sites don't have to.

## Why?
Some key-value stores in android are annoying (SharedPreferences, Bundles, etc). Traditionally, when loading the value from one of these key-value stores, the caller must know at least 3 different bits of information, the key, the type and the default value (if any). Anyone who's worked on a large team will probably have a story about someone copy-pasting the wrong default value, or assuming the wrong type when loading a a SharedPreference or an Intent's extra.

Wouldn't it be nice if we could define our keys, types and defaults all in a single object, and enforce the types at compile-time? Typed! (for Android) aims to do just that by leveraging Java's Generics and Gson's ability to serialize pretty much anything.

## The Libs
This project currently offers two typed! libs for Android

- [typed-preferences](typed-preferences/): A typed! wrapper for android's SharedPreferences.
- [typed-bundles](typed-bundle/): A typed!  wrapper for android's Bundle object.


## License
MIT: https://github.com/episode6/typed/blob/master/LICENSE