package kotools.types

import org.jetbrains.dokka.versioning.VersioningConfiguration
import org.jetbrains.dokka.versioning.VersioningPlugin

plugins { id("org.jetbrains.dokka") }

dependencies { dokkaHtmlPlugin("org.jetbrains.dokka:versioning-plugin:1.7.20") }

tasks.dokkaHtml {
    outputDirectory.set(project.buildDir.resolve("dokka"))
    dokkaSourceSets.configureEach {
        includes.from += projectDir.resolve("docs/packages.md")
        reportUndocumented.set(true)
        skipEmptyPackages.set(true)
    }
    moduleName.set("Kotools Types")
    val oldVersionsDir: File = projectDir.resolve("docs/versions")
    pluginConfiguration<VersioningPlugin, VersioningConfiguration> {
        version = "${project.version}"
        olderVersionsDir = oldVersionsDir
    }
    doLast {
        val imagesDir: File = outputDirectory.get().resolve("images")
        val logoFileName = "logo-icon.svg"
        delete { imagesDir.resolve(logoFileName) }
        copy {
            from(projectDir.resolve("docs/$logoFileName"))
            into(imagesDir)
        }
        if ("SNAPSHOT" !in "${project.version}") {
            copy {
                from(outputDirectory)
                into(oldVersionsDir)
            }
            delete {
                oldVersionsDir.resolve("${project.version}/older")
            }
        }
    }
}

tasks.register("apiDocs") {
    group(TaskGroup.LIFECYCLE)
    description = "Generates the API docs in HTML."
    dependsOn(tasks.dokkaHtml)
}
