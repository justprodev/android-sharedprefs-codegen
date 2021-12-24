val kspVersion: String by project

plugins {
    kotlin("jvm")
}

version = "1.0"

repositories {
    mavenCentral()
    google()
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.google.devtools.ksp:symbol-processing-api:$kspVersion")

    testImplementation(kotlin("test"))
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}

sourceSets.test {
    java.srcDirs("src/test/kotlin")
}
