package kotools.types

plugins {
    id("kotools.types.base")
    `maven-publish`
    signing
}

val publishing: PublishingExtension = extensions.getByType()

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

val signing: SigningExtension = extensions.getByType()
val secretKey: String? = System.getenv("GPG_PRIVATE_KEY")
val password: String? = System.getenv("GPG_PASSWORD")
signing.useInMemoryPgpKeys(secretKey, password)
publishing.publications.withType<MavenPublication>().configureEach {
    signing.sign(this)
}
