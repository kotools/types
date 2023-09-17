package kotools.types

import org.jetbrains.dokka.versioning.VersioningConfiguration
import org.jetbrains.dokka.versioning.VersioningPlugin

plugins {
    id("kotools.types.base")
    id("org.jetbrains.dokka")
}

dependencies { dokkaHtmlPlugin("org.jetbrains.dokka:versioning-plugin:1.7.20") }

val dokkaDir: File = buildDir.resolve("dokka")
val docsDir: File = projectDir.resolve("docs")
val oldDocsDir: File = docsDir.resolve("versions")

tasks.dokkaHtml.configure {
    outputDirectory.set(dokkaDir)
    dokkaSourceSets.configureEach {
        val packageDocsFile: File = docsDir.resolve("packages.md")
        includes.from(packageDocsFile)
        reportUndocumented.set(true)
        skipEmptyPackages.set(true)
    }
    moduleName.set("Kotools Types")
    pluginConfiguration<VersioningPlugin, VersioningConfiguration> {
        version = "${project.version}"
        olderVersionsDir = oldDocsDir
    }
}

val apiDocsLogo: TaskProvider<Copy> = tasks.register<Copy>("apiDocsLogo") {
    group(TaskGroup.DOCUMENTATION)
    description("Adds the Kotools logo to the API documentation.")
    mustRunAfter(tasks.dokkaHtml)
    val source: File = docsDir.resolve("logo-icon.svg")
    from(source)
    val destination: File = dokkaDir.resolve("images")
    into(destination)
}

val apiDocs: TaskProvider<Task> = tasks.register("apiDocs") {
    group(TaskGroup.RECOMMENDED)
    description("Generates the API documentation in HTML.")
    dependsOn(tasks.dokkaHtml, apiDocsLogo)
}

val javadocJar: TaskProvider<Jar> = tasks.register<Jar>("javadocJar") {
    group(TaskGroup.DOCUMENTATION)
    description("Archives the API documentation in a JAR file.")
    dependsOn(apiDocs)
    from(tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
}

val saveApiDocs: TaskProvider<Task> = tasks.register("saveApiDocs") {
    group(TaskGroup.DOCUMENTATION)
    description("Saves the API documentation.")
    dependsOn(tasks.dokkaHtml, apiDocsLogo)
    onlyIf { "SNAPSHOT" !in "${project.version}" }
    doLast {
        copy {
            from(dokkaDir)
            into(oldDocsDir)
        }
        delete { oldDocsDir.resolve("${project.version}/older") }
    }
}

val assembleApiDocs: TaskProvider<Task> = tasks.register("assembleApiDocs") {
    group(TaskGroup.DOCUMENTATION)
    description("Assembles the outputs of the API documentation.")
    dependsOn(saveApiDocs)
}

tasks.assemble.configure { dependsOn(javadocJar, assembleApiDocs) }
