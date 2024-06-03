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
        register("KotlinJvm").configure {
            val prefix = "org.kotools.types"
            id = "${prefix}.kotlin.jvm"
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

tasks.test.configure(Test::useJUnitPlatform)
tasks.withType<ValidatePlugins>().configureEach {
    failOnWarning.set(true)
    enableStricterValidation.set(true)
}
