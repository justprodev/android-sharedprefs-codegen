package com.justprodev.annotations

/**
 *  Interface used for simplified access to Android shared preferences.
 *  Main purpose is reducing typos and manual code generation.
 *
 *  0. You can access generated class like "ExamplePreferencesImpl(context)" just after build.
 *  1. You can use String/Int/Float/Boolean fields.
 *  2. By default, all fields should be nullable because of 3. (@Default fields can be non-nullable)
 *  3. To remove a value from shared preferences: "field=null" (@Default fields can be non-nullable)
 *  4. To define default value, use [Default] field annotation
 *  5. A preference will be named as your interface ("ExamplePreferences"),
 *     and sharedpreferences fields will be named as interface fields
 *
 *   @SharedPreferences
 *   interface ExamplePreferences {
 *       @Default("3")
 *       var intField: Int?
 *       var stringField: String?
 *       var floatField: Int?
 *       var booleanField: Int?
 *   }
 */
annotation class SharedPreferences {}

/**
 * @param value value in String representation, will be converted to target type
 */
annotation class Default(val value: String) {}


/**
 * Add an annotation to the function in the [SharedPreferences] interface that should clear all fields.
 */
annotation class Clear() {}
