package org.kotools.types

private val git: String = "git"
private val gitTag: List<String> =
    listOf(git, "tag", "${rootProject.name}-$version")

tasks.register<Exec>("createGitTag").configure {
    group = git
    description = "Creates a Git tag from the project's version."
    val tagMessage = "\uD83D\uDD16 Kotools Types 5 v$version"
    commandLine = gitTag + listOf("-s", "-m", tagMessage)
}

tasks.register<Exec>("deleteGitTag").configure {
    group = git
    description = "Deletes the Git tag corresponding to the project's version."
    commandLine = gitTag + listOf("-d")
}
