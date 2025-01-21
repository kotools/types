plugins { `kotlin-dsl` }

repositories.mavenCentral()

// ----------------------------- Plugin extensions -----------------------------

gradlePlugin {
    plugins {
        kotoolsTypesPlugin("Publication")
        register("DevTasks").configure {
            val prefix = "org.kotools.types"
            id = "${prefix}.dev.tasks"
            implementationClass = "${prefix}.gradle.${this.name}Plugin"
        }
        register("KotlinMultiplatform").configure {
            val prefix = "org.kotools.types"
            id = "${prefix}.kotlin.multiplatform"
            implementationClass = "${prefix}.gradle.${this.name}Plugin"
        }
        register("KotoolsTypesDocumentationPlugin").configure {
            val prefix = "org.kotools.types"
            id = "${prefix}.documentation"
            implementationClass = "${prefix}.plugins.DocumentationPlugin"
        }
    }
}

private fun NamedDomainObjectContainer<PluginDeclaration>.kotoolsTypesPlugin(
    name: String
): Unit = register("KotoolsTypes${name}Plugin").configure {
    id = "kotools.types.${name.lowercase()}"
    implementationClass = "kotools.types.plugins.${name}Plugin"
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
