pluginManagement {
  val kotlinVersion: String by settings
  val kspVersion: String by settings
  plugins {
    id("com.google.devtools.ksp") version kspVersion
    kotlin("jvm") version kotlinVersion
  }
  repositories {
    gradlePluginPortal()
    google()
  }
}

rootProject.name = "android-sharedprefs-codegen"
include(":sharedpreferences_processor")

