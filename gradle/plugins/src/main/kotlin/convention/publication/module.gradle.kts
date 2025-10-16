package convention.publication

plugins {
    `maven-publish`
    signing
}

// ----------------------------- Plugin extensions -----------------------------

private val publishing: PublishingExtension = extensions.getByType()
publishing.repositories.maven {
    name = "project"
    url = rootProject.layout.buildDirectory.dir("maven")
        .let(::uri)
}
private val mavenPublications: NamedDomainObjectCollection<MavenPublication> =
    publishing.publications.withType()
mavenPublications.configureEach {
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
                name.set(System.getenv("GIT_USER"))
                email.set(System.getenv("GIT_EMAIL"))
            }
        }
    }
}
mavenPublications.matching { it.name == "kotlinMultiplatform" }
    .configureEach {
        groupId = project.group.toString()
        artifactId = project.name
        version = project.version.toString()
    }

private val gpgPrivateKey: String? = System.getenv("GPG_PRIVATE_KEY")
private val gpgPassword: String? = System.getenv("GPG_PASSWORD")
if (gpgPrivateKey != null && gpgPassword != null) {
    val signing: SigningExtension = extensions.getByType()
    signing.useInMemoryPgpKeys(gpgPrivateKey, gpgPassword)
    signing.sign(publishing.publications)
}

// ----------------------------------- Tasks -----------------------------------

private val signTasks: TaskCollection<Sign> = tasks.withType<Sign>()
tasks.withType<PublishToMavenRepository>()
    .configureEach { dependsOn(signTasks) }
