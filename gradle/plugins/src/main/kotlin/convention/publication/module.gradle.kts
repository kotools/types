package convention.publication

// ------------------- Maven Publish & Signing integrations --------------------

pluginManager.withPlugin("maven-publish") {
    // Plugin extensions
    val publishing: PublishingExtension = extensions.getByType()
    publishing.repositories.maven {
        name = "project"
        url = uri(rootProject.layout.buildDirectory.dir("maven"))
    }
    val publications: NamedDomainObjectCollection<MavenPublication> =
        publishing.publications.withType()
    publications.configureEach {
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
    publications.matching { it.name == "kotlinMultiplatform" }.configureEach {
        groupId = project.group.toString()
        artifactId = project.name
        version = project.version.toString()
    }
    pluginManager.withPlugin("signing") {
        val gpgPrivateKey: String? = System.getenv("GPG_PRIVATE_KEY")
        val gpgPassword: String? = System.getenv("GPG_PASSWORD")
        if (gpgPrivateKey != null && gpgPassword != null) {
            val signing: SigningExtension = extensions.getByType()
            signing.useInMemoryPgpKeys(gpgPrivateKey, gpgPassword)
            signing.sign(publishing.publications)
        }
    }

    // Tasks
    val signTasks: TaskCollection<Sign> = tasks.withType<Sign>()
    tasks.withType<PublishToMavenRepository>()
        .configureEach { dependsOn(signTasks) }
}
