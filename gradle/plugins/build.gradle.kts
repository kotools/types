plugins { `kotlin-dsl` }

repositories.mavenCentral()

// ----------------------------- Plugin extensions -----------------------------

gradlePlugin {
    plugins {
        register("KotlinMultiplatform").configure {
            val prefix = "org.kotools.types"
            this.id = "$prefix.kotlin.multiplatform"
            this.implementationClass = "$prefix.gradle.${this.name}Plugin"
        }
        register("Documentation").configure {
            val prefix = "org.kotools.types"
            this.id = "$prefix.${this.name.lowercase()}"
            this.implementationClass = "$prefix.plugins.${this.name}Plugin"
        }
        register("Publication").configure {
            val prefix = "kotools.types"
            this.id = "$prefix.${this.name.lowercase()}"
            this.implementationClass = "$prefix.plugins.${this.name}Plugin"
        }
    }
}

kotlin.explicitApi()

// -----------------------------------------------------------------------------

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.dokka.base)
    implementation(libs.dokka.gradle.plugin)
    implementation(libs.dokka.versioning)
}

// ----------------------------------- Tasks -----------------------------------

tasks.withType<ValidatePlugins>().configureEach {
    failOnWarning.set(true)
    enableStricterValidation.set(true)
}

tasks.test.configure(Test::useJUnitPlatform)
