package kotools.gradle

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

// ---------- Environment ----------

private fun <T : EnvironmentGroup> environment(suffix: String): Environment<T> =
    Environment(suffix)

private class Environment<in T : EnvironmentGroup>(
    private val suffix: String
) : ReadOnlyProperty<T, String> {
    override fun getValue(thisRef: T, property: KProperty<*>): String =
        thisRef.prefix?.let { "${it}_$suffix" } ?: suffix
}

// ---------- Environment groups ----------

/** Represents a group of environment variables. */
sealed class EnvironmentGroup(internal val prefix: String? = null)

object Git : EnvironmentGroup("GIT") {
    val email: String by environment("EMAIL")
    val user: String by environment("USER")
}

object GitHub : EnvironmentGroup("GITHUB") {
    val password: String by environment("TOKEN")
    val username: String by environment("ACTOR")
}

object Gpg : EnvironmentGroup("GPG") {
    val password: String by environment("PASSWORD")
    val secretKey: String by environment("PRIVATE_KEY")
}

object Maven : EnvironmentGroup("MAVEN") {
    val password: String by environment("PASSWORD")
    val username: String by environment("USERNAME")
}
