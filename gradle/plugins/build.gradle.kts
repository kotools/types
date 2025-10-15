plugins { `kotlin-dsl` }

repositories.mavenCentral()

kotlin.explicitApi()

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.dokka.base)
    implementation(libs.dokka.gradle.plugin)
    implementation(libs.dokka.versioning)
}

tasks.withType<ValidatePlugins>().configureEach {
    failOnWarning = true
    enableStricterValidation = true
}
