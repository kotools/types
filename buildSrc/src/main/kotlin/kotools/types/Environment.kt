package kotools.types

/**
 * Returns the value of the environment variable corresponding to the given
 * [name], or returns `null` if no environment variable exists with the given
 * [name].
 */
fun env(name: String): String? = System.getenv(name)

/** Represents a group of environment variables. */
sealed class EnvironmentGroup(private val prefix: String) {
    /**
     * Returns the value of the environment variable corresponding to the
     * given [name], or returns `null` if no environment variable exists with
     * the given [name].
     *
     * If a [prefix] is defined, this function will search an environment
     * variable that has a name matching the concatenation of the [prefix] and
     * the given [name].
     * For example, given a [prefix] that equals `HELLO` and a [name] that
     * equals `WORLD`, this function will search for an environment variable
     * called `HELLO_WORLD`.
     */
    internal infix operator fun get(name: String): String? {
        println("> Searching for environment variable called '${prefix}_$name'")
        return env("${prefix}_$name")
    }
}

object Git : EnvironmentGroup("GIT") {
    val email: String? = this["EMAIL"]
    val user: String? = this["USER"]
}

object GitHub : EnvironmentGroup("GITHUB") {
    val password: String? = this["TOKEN"]
    val username: String? = this["ACTOR"]
}

object Gpg : EnvironmentGroup("GPG") {
    val password: String? = this["PASSWORD"]
    val secretKey: String? = this["PRIVATE_KEY"]
}

object Maven : EnvironmentGroup("MAVEN") {
    val password: String? = this["PASSWORD"]
    val username: String? = this["USERNAME"]
}
