package org.kotools.types.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.jetbrains.dokka.gradle.DokkaTask
import kotlin.reflect.KClass

/** Plugin configuring the documentation of Kotools Types. */
public class DocumentationPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        val documentation = DocumentationExtension(project)
        val tasks = DocumentationTasks(project)
        val cleanApiReference: TaskProvider<Delete> =
            tasks.cleanApiReference(documentation)
        val apiReference: TaskProvider<Task> = tasks.apiReference()
        if (!tasks.dokkaHtmlMultiModuleExists()) {
            val dokkaHtml: TaskProvider<DokkaTask> =
                tasks.dokkaHtml(documentation)
            apiReferenceJarTask(tasks, dokkaHtml, project)
            return apiReference.configure { dependsOn += dokkaHtml }
        }
        val dokkaHtmlMultiModule: TaskProvider<DokkaMultiModuleTask> =
            tasks.dokkaHtmlMultiModule(documentation)
        dokkaHtmlMultiModule.configure { dependsOn += cleanApiReference }
        apiReference.configure { dependsOn += dokkaHtmlMultiModule }
    }

    private fun apiReferenceJarTask(
        tasks: DocumentationTasks,
        dokkaTask: TaskProvider<DokkaTask>,
        project: Project
    ) {
        val apiReferenceJar: TaskProvider<Jar> =
            tasks.apiReferenceJar(dokkaTask)
        project.tasks.named("assemble")
            .configure { dependsOn += apiReferenceJar }
        val publishing: KClass<PublishingExtension> = PublishingExtension::class
        project.extensions.findByType(publishing)
            ?.publications
            ?.withType<MavenPublication>()
            ?.configureEach { artifact(apiReferenceJar) }
    }
}
