plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotools.types.documentation)
    alias(libs.plugins.kotools.samples.jvm)
}

// ----------------------------- Plugin extensions -----------------------------

documentation.excludeFromParentApiReference = true

kotlin.explicitApi()

// ------------------------------- Dependencies --------------------------------

dependencies {
    implementation(platform(libs.kotlin.bom))

    sampleImplementation(libs.kotlin.test.junit5)
}

// ----------------------------------- Tasks -----------------------------------

tasks.test.configure(Test::useJUnitPlatform)
