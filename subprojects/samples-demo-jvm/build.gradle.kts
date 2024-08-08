plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotools.types.documentation)
}

// ----------------------------- Plugin extensions -----------------------------

documentation.excludeFromParentApiReference = true

kotlin.explicitApi()

// ------------------------------- Dependencies --------------------------------

dependencies {
    implementation(platform(libs.kotlin.bom))

    testImplementation(libs.kotlin.test.junit5)
}

// ----------------------------------- Tasks -----------------------------------

tasks.test.configure(Test::useJUnitPlatform)
