plugins { java }

repositories.mavenCentral()

java {
    sourceCompatibility = JavaVersion.VERSION_17 // Java language
    targetCompatibility = JavaVersion.VERSION_17 // JVM bytecode
}

dependencies {
    testImplementation(rootProject)
    testImplementation(libs.junit.jupiter)
}

tasks.test.configure { useJUnitPlatform() }
