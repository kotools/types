plugins { `kotlin-dsl` }

repositories.mavenCentral()

kotlin.explicitApi()

gradlePlugin.plugins.register("Compatibility").configure {
    this.id = "org.kotools.compatibility"
    this.implementationClass = "org.kotools.gradle.CompatibilityPlugin"
}

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
