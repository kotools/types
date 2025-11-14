package org.kotools.types.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.PublishToMavenRepository
import org.gradle.api.tasks.TaskCollection
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.Sign
import org.gradle.plugins.signing.SigningExtension

/** Gradle plugin responsible for configuring the publication of a project. */
public class PublicationPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project): Unit =
        project.withMavenPublishPlugin()
}

private fun Project.withMavenPublishPlugin() {
    val project: Project = this
    project.pluginManager.withPlugin("maven-publish") {
        val publishing: PublishingExtension = project.extensions.getByType()
        publishing.repositories.maven {
            this.name = "project"
            val path: Provider<Directory> =
                project.rootProject.layout.buildDirectory.dir("maven")
            this.url = project.uri(path)
        }
        publishing.publications.withType<MavenPublication>().configureEach {
            this.pom {
                this.name.set("Kotools Types")
                this.description.set("Explicit types for Kotlin Multiplatform.")
                val kotoolsUrl = "https://github.com/kotools"
                val kotoolsTypesUrl = "$kotoolsUrl/types"
                this.url.set(kotoolsTypesUrl)
                this.licenses {
                    this.license {
                        this.name.set("MIT")
                        this.url.set("https://opensource.org/licenses/MIT")
                    }
                }
                this.developers {
                    this.developer {
                        this.name.set("Loïc Lamarque")
                        this.email.set("contact@kotools.org")
                        this.organization.set("Kotools")
                        this.organizationUrl.set(kotoolsUrl)
                    }
                }
                this.scm {
                    this.connection.set("$kotoolsTypesUrl.git")
                    this.url.set(kotoolsTypesUrl)
                }
            }
        }
        project.withKotlinMultiplatformPlugin(publishing)
        project.withSigningPlugin(publishing)
    }
}

private fun Project.withKotlinMultiplatformPlugin(
    publishing: PublishingExtension
) {
    val project: Project = this
    project.pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
        publishing.publications.named<MavenPublication>("kotlinMultiplatform")
            .configure {
                this.groupId = project.group.toString()
                this.artifactId = project.name
                this.version = project.version.toString()
            }
    }
}

private fun Project.withSigningPlugin(publishing: PublishingExtension) {
    val project: Project = this
    project.pluginManager.withPlugin("signing") {
        // Plugin extension
        val gpgPrivateKey: String? = System.getenv("GPG_PRIVATE_KEY")
        val gpgPassword: String? = System.getenv("GPG_PASSWORD")
        if (gpgPrivateKey == null || gpgPassword == null) return@withPlugin
        val signing: SigningExtension = project.extensions.getByType()
        signing.useInMemoryPgpKeys(gpgPrivateKey, gpgPassword)
        signing.sign(publishing.publications)
        // Tasks
        val signTasks: TaskCollection<Sign> = project.tasks.withType()
        project.tasks.withType<PublishToMavenRepository>().configureEach {
            this.dependsOn(signTasks)
        }
    }
}
