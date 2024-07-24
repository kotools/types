package kotools.types.plugins

import kotools.types.Env
import kotools.types.tasks.PrintTask
import kotools.types.tasks.TaskGroup
import kotools.types.tasks.description
import kotools.types.tasks.group
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
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
        project.tasks.register<PrintTask>("version").configure {
            group(TaskGroup.HELP)
            description("Displays the project's version.")
            message.set(project.version)
        }
    }
}

private fun RepositoryHandler.ossrh(project: Project) {
    maven {
        name = "OSSRH"
        val sonatypeUrl = "https://s01.oss.sonatype.org/"
        val isSnapshot: Boolean = project.version.toString()
            .endsWith("SNAPSHOT")
        val repositoryPath: String =
            if (isSnapshot) "content/repositories/snapshots/"
            else "service/local/staging/deploy/maven2/"
        url = project.uri("$sonatypeUrl$repositoryPath")
        credentials {
            username = Env.mavenUsername
            password = Env.mavenPassword
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
