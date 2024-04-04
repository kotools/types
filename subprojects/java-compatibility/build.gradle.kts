plugins { java }

java {
    sourceCompatibility = JavaVersion.VERSION_17 // Java language
    targetCompatibility = JavaVersion.VERSION_17 // JVM bytecode
}

dependencies {
    testImplementation(project(":types"))
    testImplementation(project(":types-internal"))
    testImplementation(libs.junit.jupiter)
}

tasks.test.configure(Test::useJUnitPlatform)
