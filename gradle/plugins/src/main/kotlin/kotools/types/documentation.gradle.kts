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

tasks.dokkaHtml {
    outputDirectory.set(dokkaDir)
    dokkaSourceSets.configureEach {
        includes.from += docsDir.resolve("packages.md")
        reportUndocumented.set(true)
        skipEmptyPackages.set(true)
    }
    moduleName.set("Kotools Types")
    val oldVersionsDir: File = docsDir.resolve("versions")
    pluginConfiguration<VersioningPlugin, VersioningConfiguration> {
        version = "${project.version}"
        olderVersionsDir = oldVersionsDir
    }
    doLast {
        if ("SNAPSHOT" !in "${project.version}") {
            copy {
                from(dokkaDir)
                into(oldVersionsDir)
            }
            delete {
                oldVersionsDir.resolve("${project.version}/older")
            }
        }
    }
}

val dokkaImagesDir: File = dokkaDir.resolve("images")
val logoFileName = "logo-icon.svg"

val deleteApiDocsLogo = tasks.register<Delete>("deleteApiDocsLogo") {
    group(TaskGroup.DOCUMENTATION)
    description("Deletes the logo of the API docs.")
    dependsOn += tasks.dokkaHtml
    val target: File = dokkaImagesDir.resolve(logoFileName)
    delete(target)
}

val copyApiDocsLogo = tasks.register<Copy>("copyApiDocsLogo") {
    group(TaskGroup.DOCUMENTATION)
    description("Copies the Kotools logo to the API docs.")
    dependsOn += deleteApiDocsLogo
    val sourceTarget: File = docsDir.resolve(logoFileName)
    from(sourceTarget)
    into(dokkaImagesDir)
}

val apiDocs = tasks.register<Task>("apiDocs") {
    group(TaskGroup.RECOMMENDED)
    description("Generates the API docs in HTML.")
    dependsOn(tasks.dokkaHtml, copyApiDocsLogo)
}

val javadocJar = tasks.register<Jar>("javadocJar") {
    group(TaskGroup.DOCUMENTATION)
    description("Archives the API docs in a JAR file.")
    dependsOn += apiDocs
    from(tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
}

tasks.assemble { dependsOn += javadocJar }
