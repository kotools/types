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
        this.pom {
            this.name = "Kotools Types"
            this.description = "Explicit types for Kotlin Multiplatform."
            this.url = "https://github.com/kotools/types"
            this.licenses {
                this.license {
                    this.name = "MIT"
                    this.url = "https://opensource.org/licenses/MIT"
                }
            }
            this.developers {
                this.developer {
                    this.name = "Loïc Lamarque"
                    this.email = "contact@kotools.org"
                    this.organization = "Kotools"
                    this.organizationUrl =
                        this@pom.url.map { it.removeSuffix("/types") }
                }
            }
            this.scm {
                this.url = this@pom.url
                this.connection = this.url.map { "${it}.git" }
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
