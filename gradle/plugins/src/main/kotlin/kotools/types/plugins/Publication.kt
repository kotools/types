package kotools.types.plugins

import kotools.types.Env
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.SigningExtension

/** Plugin configuring the publication of Kotools Types projects. */
public class PublicationPlugin : Plugin<Project> {
    /** Applies this plugin to the given [project]. */
    override fun apply(project: Project) {
        val publishing: PublishingExtension = project.extensions.getByType()
        publishing.repositories.ossrh(project)
        val signing: SigningExtension = project.extensions.getByType()
        signing.useInMemoryPgpKeys(Env.gpgPrivateKey, Env.gpgPassword)
        publishing.publications.withType<MavenPublication>().configureEach {
            signing.sign(this)
            configurePom()
        }
    }
}

private fun MavenPublication.configurePom(): Unit = pom {
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
            name.set(Env.gitUser)
            email.set(Env.gitEmail)
        }
    }
}

private fun RepositoryHandler.ossrh(project: Project) {
    maven {
        name = "OSSRH"
        url = project.uri(
            "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
        )
        credentials {
            username = Env.mavenUsername
            password = Env.mavenPassword
        }
    }
}
