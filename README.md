# A clean way to access the shared preferences

**Using**

Create the interface

```kotlin
@SharedPreferences
interface ExamplePreferences {
    @Default("3")
    var intField: Int?
    var stringField: String?
    var floatField: Float?
    var booleanField: Boolean?
    @Default("123")
    var floatField2: Float?
    @Default("true")
    var booleanField2: Boolean?
}
```

- You can access generated class like "ExamplePreferencesImpl(context)" just after build.
- You can use String/Int/Float/Boolean fields.
- By default, all fields should be nullable. (@Default fields can be non-nullable)
- To remove a value from shared preferences: "field=null" (Exception: @Default fields can be non-nullable)
- To define default value, use Default field annotation
- A preference will be named as your interface ("ExamplePreferences"), and sharedpreferences fields will be named as interface fields

**Install**

1. Add `kspVersion=1.6.10-1.0.2` (or other version corresponds to your kotlin's version) to `gradle.properties`
2. Add to `build.gradle` in a module (like `app` or other):

```
plugins {
    id 'com.google.devtools.ksp' version "$kspVersion"
    // other plugins
}

dependencies {
    implementation 'com.github.justprodev:android-sharedprefs-codegen:1.0'
    ksp 'com.github.justprodev:android-sharedprefs-codegen:1.0'
    testImplementation 'com.github.justprodev:android-sharedprefs-codegen:1.0'
    // other dependencies
}
```

*You should have `maven { url 'https://jitpack.io' }` in your repositories list
