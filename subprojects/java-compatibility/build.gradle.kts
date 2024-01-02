plugins { java }

repositories.mavenCentral()

property("java.version")
    ?.toString()
    ?.let(JavaLanguageVersion::of)
    ?.let(java.toolchain.languageVersion::set)
    ?: error("The 'java.version' property wasn't found.")

dependencies {
    testImplementation(rootProject)
    testImplementation(libs.junit.jupiter)
}

tasks.test.configure { useJUnitPlatform() }
