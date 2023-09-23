// ---------- Plugins ----------

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.binary.compatibility.validator)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.dokka)
    `maven-publish`
    signing
    id("kotools.types.base")
    id("kotools.types.multiplatform")
    id("kotools.types.documentation")
}

// ---------- Project Details ----------

group = "org.kotools"
version = "4.3.1-SNAPSHOT"

// ---------- Extensions ----------

publishing.repositories.maven {
    name = "OSSRH"
    url = uri(
        "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
    )
    credentials {
        username = System.getenv("MAVEN_USERNAME")
        password = System.getenv("MAVEN_PASSWORD")
    }
}

signing {
    val secretKey: String? = System.getenv("GPG_PRIVATE_KEY")
    val password: String? = System.getenv("GPG_PASSWORD")
    useInMemoryPgpKeys(secretKey, password)
}

publishing.publications.withType<MavenPublication>().configureEach {
    signing.sign(this)
    pom {
        name.set("Kotools Types")
        description.set(
            "Multiplatform library providing explicit types for Kotlin."
        )
        val gitRepository = "https://github.com/kotools/types"
        url.set(gitRepository)
        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        issueManagement {
            system.set("GitHub")
            url.set("$gitRepository/issues")
        }
        scm {
            connection.set("$gitRepository.git")
            url.set(gitRepository)
        }
        developers {
            developer {
                val nameValue: String? = System.getenv("GIT_USER")
                name.set(nameValue)
                val emailValue: String? = System.getenv("GIT_EMAIL")
                email.set(emailValue)
            }
        }
    }
}

// ---------- Dependencies ----------

repositories.mavenCentral()

dependencies {
    commonMainImplementation(platform(libs.kotlin.bom))
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonTestImplementation(libs.kotlin.test)
    commonTestImplementation(libs.kotlinx.serialization.json)

    dokkaHtmlPlugin(libs.dokka.versioning)
}

// ---------- Tasks ----------

tasks.register<DependencyReportTask>("runtimeDependencies").configure {
    group = "help"
    description = "Displays the runtime dependencies for all source sets."
    setConfiguration("allSourceSetsRuntimeDependenciesMetadata")
}
