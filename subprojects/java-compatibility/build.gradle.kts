plugins {
    java
    id("kotools.types.base")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17 // Java language
    targetCompatibility = JavaVersion.VERSION_17 // JVM bytecode
}

dependencies {
    testImplementation(rootProject)
    testImplementation(projects.typesInternal)
    testImplementation(libs.junit.jupiter)
}

tasks.test.configure(Test::useJUnitPlatform)
