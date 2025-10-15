// ---------------------------------- Plugins ----------------------------------

plugins { `kotlin-dsl` }

// ------------------------------- Repositories --------------------------------

repositories.mavenCentral()

// ----------------------------- Plugin extensions -----------------------------

gradlePlugin {
    plugins {
        register("BaseModule").configure {
            val prefix = "org.kotools.types"
            this.id = "${prefix}.base.module"
            this.implementationClass = "${prefix}.gradle.${this.name}Plugin"
        }
        register("DocumentationModule").configure {
            val prefix = "org.kotools.types"
            this.id = "$prefix.documentation.module"
            this.implementationClass = "${prefix}.gradle.${this.name}Plugin"
        }
        register("KotlinMultiplatform").configure {
            val prefix = "org.kotools.types"
            this.id = "$prefix.kotlin.multiplatform"
            this.implementationClass = "$prefix.gradle.${this.name}Plugin"
        }
    }
}

kotlin.explicitApi()

// ------------------------------- Dependencies --------------------------------

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
