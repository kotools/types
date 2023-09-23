plugins { `kotlin-dsl` }

repositories.mavenCentral()

kotlin.explicitApi()

dependencies { implementation(libs.kotlin.gradle.plugin) }

gradlePlugin.plugins.register("KotoolsTypesBasePlugin").configure {
    id = "kotools.types.base"
    implementationClass = "kotools.types.plugins.BasePlugin"
}
