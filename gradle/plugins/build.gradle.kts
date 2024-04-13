plugins { `kotlin-dsl` }

repositories.mavenCentral()

kotlin.explicitApi()

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.dokka.base)
    implementation(libs.dokka.gradle.plugin)
    implementation(libs.dokka.versioning)

    testImplementation(libs.kotlin.test)
}

gradlePlugin {
    plugins {
        kotoolsTypesPlugin("Multiplatform")
        kotoolsTypesPlugin("Publication")
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
