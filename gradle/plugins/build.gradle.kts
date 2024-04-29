plugins { `kotlin-dsl` }

repositories.mavenCentral()

kotlin.explicitApi()

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.dokka.base)
    implementation(libs.dokka.gradle.plugin)
    implementation(libs.dokka.versioning)

    testImplementation(libs.kotlin.test.junit5)
}

gradlePlugin {
    plugins {
        kotoolsTypesPlugin("Publication")
        register("DevTasksConventionPlugin").configure {
            val prefix = "org.kotools.types.gradle"
            id = "${prefix}.dev.tasks"
            implementationClass = "${prefix}.DevTasksPlugin"
        }
        register("KotlinJvmConventionPlugin").configure {
            val prefix = "org.kotools.types.gradle"
            id = "${prefix}.kotlin.jvm"
            implementationClass = "${prefix}.KotlinJvmPlugin"
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

tasks.test.configure(Test::useJUnitPlatform)
tasks.withType<ValidatePlugins>().configureEach {
    failOnWarning.set(true)
    enableStricterValidation.set(true)
}
