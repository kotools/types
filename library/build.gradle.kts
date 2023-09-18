plugins {
    id("kotools.types.multiplatform")
    id("kotools.types.documentation")
    id("kotools.types.publication")
}

version = "4.3.1-SNAPSHOT"

repositories.mavenCentral()

publishing.publications.named<MavenPublication>("kotlinMultiplatform")
    .configure {
        groupId = "${project.group}"
        artifactId = rootProject.name
        version = "${project.version}"
    }

publishing.publications.withType<MavenPublication>().configureEach {
    artifact(tasks.javadocJar)
    pom {
        name.set("Kotools Types")
        description.set(
            "Kotlin multiplatform library providing explicit types."
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
                name.set(System.getenv("GIT_USER"))
                email.set(System.getenv("GIT_EMAIL"))
            }
        }
    }
}

dependencies {
    // Kotlin
    commonMainImplementation(platform(kotlin("bom")))
    commonTestImplementation(kotlin("test"))

    // Kotlinx Serialization
    commonMainImplementation(libs.kotlinx.serialization.core)
    commonTestImplementation(libs.kotlinx.serialization.json)
}
