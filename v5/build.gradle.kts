plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.kotlinx.binary.compatibility.validator) apply false
}

allprojects { repositories.mavenCentral() }

subprojects {
    group = "org.kotools"
    version = "0.1.0-SNAPSHOT"
}
