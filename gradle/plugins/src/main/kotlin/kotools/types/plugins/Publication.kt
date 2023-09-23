package kotools.types.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
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
        publishing.repositories.maven {
            name = "OSSRH"
            url = project.uri(
                "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            )
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
        val signing: SigningExtension = project.extensions.getByType()
        signing.run {
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
    }
}
