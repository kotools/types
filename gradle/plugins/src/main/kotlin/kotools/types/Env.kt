package kotools.types

internal object Env {
    internal val gitEmail: String? by lazy { env("GIT_EMAIL") }
    internal val gitUser: String? by lazy { env("GIT_USER") }
    internal val gpgPassword: String? by lazy { env("GPG_PASSWORD") }
    internal val gpgPrivateKey: String? by lazy { env("GPG_PRIVATE_KEY") }

    internal fun sonatypeUsernameOrNull(): String? =
        this.env("SONATYPE_USERNAME")

    internal fun sonatypePasswordOrNull(): String? =
        this.env("SONATYPE_PASSWORD")

    internal fun sonatypeRepositoryIdentifierOrNull(): String? =
        this.env("SONATYPE_REPOSITORY_IDENTIFIER")

    private fun env(name: String): String? = System.getenv(name)
}
