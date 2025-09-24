package org.kotools.types.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import kotlin.reflect.KClass

/** Plugin configuring the documentation of Kotools Types. */
public class DocumentationPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        val extension = DocumentationExtension(project)
        val tasks = DocumentationTasks(project)
        val apiReference: TaskProvider<Task> = tasks.apiReference()
        if (project.tasks.findByName("dokkaHtmlMultiModule") != null) {
            val cleanApiReference: TaskProvider<Delete> =
                tasks.cleanApiReference(extension)
            val dokkaHtmlMultiModule: TaskProvider<DokkaMultiModuleTask> = tasks
                .dokkaMultiModuleTaskConfiguration(extension)
                .let {
                    project.tasks.named<DokkaMultiModuleTask>(
                        "dokkaHtmlMultiModule",
                        it
                    )
                }
            dokkaHtmlMultiModule.configure { dependsOn += cleanApiReference }
            apiReference.configure { dependsOn += dokkaHtmlMultiModule }
            // The code below should be in the Publication Root Plugin.
            if (project.hasSnapshotVersion()) return
            val archiveApiReference: TaskProvider<Copy> =
                tasks.archiveApiReference(extension)
            archiveApiReference.configure { dependsOn += dokkaHtmlMultiModule }
            /*
            We should delete the current API reference while keeping the CNAME
            file before updating it.
            */
            val setCurrentApiReference: TaskProvider<Copy> =
                tasks.setCurrentApiReference(extension)
            setCurrentApiReference.configure {
                dependsOn += dokkaHtmlMultiModule
            }
            return tasks.publishApiReference().configure {
                listOf(archiveApiReference, setCurrentApiReference)
                    .let(this::setDependsOn)
            }
        }
        project.tasks.withType<DokkaTaskPartial>()
            .configureEach(tasks.dokkaTaskPartialConfiguration(extension))
        val dokkaHtml: TaskProvider<DokkaTask> = tasks
            .dokkaTaskConfiguration(extension)
            .let { project.tasks.named<DokkaTask>("dokkaHtml", it) }
        apiReference.configure { dependsOn += dokkaHtml }
        val apiReferenceJar: TaskProvider<Jar> =
            tasks.apiReferenceJar(dokkaHtml)
        project.tasks.named("assemble")
            .configure { dependsOn += apiReferenceJar }
        val publishing: KClass<PublishingExtension> = PublishingExtension::class
        project.extensions.findByType(publishing)
            ?.publications
            ?.withType<MavenPublication>()
            ?.configureEach { artifact(apiReferenceJar) }
    }
}

private fun Project.hasSnapshotVersion(): Boolean =
    "$version".endsWith("SNAPSHOT")
