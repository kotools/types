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
        register("KotoolsTypesDocumentationPlugin").configure {
            val prefix = "org.kotools.types"
            id = "${prefix}.documentation"
            implementationClass = "${prefix}.plugins.DocumentationPlugin"
        }
        register("KotlinJvmConventionPlugin").configure {
            id = "org.kotools.types.kotlin.jvm"
            implementationClass = "org.kotools.types.gradle.KotlinJvmPlugin"
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
