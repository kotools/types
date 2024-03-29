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
        kotoolsTypesPlugin("Base")
        kotoolsTypesPlugin("Documentation")
        kotoolsTypesPlugin("Multiplatform")
        kotoolsTypesPlugin("Publication")
        register("KotoolsTypesBasePluginV2").configure {
            id = "kotools.types.base.v2"
            implementationClass = "kotools.types.base.BasePlugin"
        }
        register("KotoolsTypesKDocPlugin").configure {
            id = "kotools.types.kdoc"
            implementationClass = "$id.KDocPlugin"
        }
    }
}

private fun NamedDomainObjectContainer<PluginDeclaration>.kotoolsTypesPlugin(
    name: String
): Unit = register("KotoolsTypes${name}Plugin").configure {
    id = "kotools.types.${name.lowercase()}"
    implementationClass = "kotools.types.plugins.${name}Plugin"
}

tasks.withType<ValidatePlugins>().configureEach {
    failOnWarning.set(true)
    enableStricterValidation.set(true)
}
