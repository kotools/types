package kotools.types

import org.jetbrains.dokka.versioning.VersioningConfiguration
import org.jetbrains.dokka.versioning.VersioningPlugin

plugins {
    id("kotools.types.base")
    id("org.jetbrains.dokka")
}

dependencies { dokkaHtmlPlugin("org.jetbrains.dokka:versioning-plugin:1.7.20") }

val dokkaDir: File = project.buildDir.resolve("dokka")
tasks.dokkaHtml {
    outputDirectory.set(dokkaDir)
    dokkaSourceSets.configureEach {
        includes.from += projectDir.resolve("docs/packages.md")
        reportUndocumented.set(true)
        skipEmptyPackages.set(true)
    }
    moduleName.set("Kotools Types")
    val oldVersionsDir: File = rootDir.resolve("api/references")
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
    description = "Deletes the logo of the API docs."
    dependsOn += tasks.dokkaHtml
    delete(dokkaImagesDir.resolve(logoFileName))
}
val copyApiDocsLogo = tasks.register<Copy>("copyApiDocsLogo") {
    description = "Copies the Kotools logo to the API docs."
    dependsOn += deleteApiDocsLogo
    from(projectDir.resolve("docs/$logoFileName"))
    into(dokkaImagesDir)
}
val apiDocs = tasks.register<Task>("apiDocs") {
    group(TaskGroup.RECOMMENDED)
    description = "Generates the API docs in HTML."
    dependsOn(tasks.dokkaHtml, copyApiDocsLogo)
}
val javadocJar = tasks.register<Jar>("javadocJar") {
    dependsOn += apiDocs
    from(tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
}
tasks.assemble { dependsOn += javadocJar }
