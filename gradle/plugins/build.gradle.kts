plugins { `kotlin-dsl` }

repositories.mavenCentral()

kotlin.explicitApi()

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.dokka.base)
    implementation(libs.dokka.gradle.plugin)
    implementation(libs.dokka.versioning)
}

gradlePlugin {
    plugins {
        registerKotoolsTypesPlugin("Base")
        registerKotoolsTypesPlugin("Documentation")
        registerKotoolsTypesPlugin("Multiplatform")
        registerKotoolsTypesPlugin("Publication")
    }
}

fun NamedDomainObjectContainer<PluginDeclaration>.registerKotoolsTypesPlugin(
    name: String
): Unit = register("KotoolsTypes${name}Plugin").configure {
    val idSuffix: String = name.toLowerCase()
    kotoolsTypesId(idSuffix)
    kotoolsTypesImplementationClass("${name}Plugin")
}

fun PluginDeclaration.kotoolsTypesId(value: String) {
    id = "kotools.types.$value"
}

fun PluginDeclaration.kotoolsTypesImplementationClass(name: String) {
    implementationClass = "kotools.types.plugins.$name"
}

tasks.withType<ValidatePlugins>().configureEach {
    failOnWarning.set(true)
    enableStricterValidation.set(true)
}
