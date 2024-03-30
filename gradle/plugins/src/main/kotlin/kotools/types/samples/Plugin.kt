package kotools.types.samples

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.FileTree
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.language.base.plugins.LifecycleBasePlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/** Plugin responsible for extracting Kotlin and Java samples. */
public class SamplesPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project) {
        project.extractKotlinSamplesTask()
        project.cleanSamplesTask()
    }

    internal companion object {
        const val OUTPUT_DIRECTORY: String = "samples"
    }
}

private fun Project.extractKotlinSamplesTask() {
    val sources: Provider<FileTree> = tasks
        .named<KotlinCompile>("compileKotlin")
        .map { it.sources.asFileTree }
    val output: Provider<Directory> =
        layout.buildDirectory.dir(SamplesPlugin.OUTPUT_DIRECTORY)
    val extractKotlinSamples: TaskProvider<KotlinSamplesExtractor> =
        tasks.register<KotlinSamplesExtractor>("extractKotlinSamples") {
            this.sources.set(sources)
            this.output.set(output)
        }
    tasks.named(LifecycleBasePlugin.ASSEMBLE_TASK_NAME).configure {
        dependsOn += extractKotlinSamples
    }
}

private fun Project.cleanSamplesTask() {
    val target: Provider<Directory> =
        layout.buildDirectory.dir(SamplesPlugin.OUTPUT_DIRECTORY)
    val cleanSamples: TaskProvider<Delete> =
        tasks.register<Delete>("cleanSamples") { this.setDelete(target) }
    tasks.named(LifecycleBasePlugin.CLEAN_TASK_NAME).configure {
        dependsOn += cleanSamples
    }
}
