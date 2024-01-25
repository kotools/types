plugins { java }

repositories.mavenCentral()

java {
    sourceCompatibility = JavaVersion.VERSION_1_8 // Java language
    targetCompatibility = JavaVersion.VERSION_1_8 // JVM bytecode
}

dependencies {
    testImplementation(rootProject)
    testImplementation(libs.junit.jupiter)
}

tasks.test.configure { useJUnitPlatform() }
