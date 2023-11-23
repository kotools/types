plugins { java }

repositories.mavenCentral()

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

dependencies {
    testImplementation(rootProject)
    testImplementation(libs.junit.jupiter)
}

tasks.test.configure { useJUnitPlatform() }
