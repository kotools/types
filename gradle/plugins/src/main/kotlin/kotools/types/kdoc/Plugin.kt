package kotools.types.kdoc

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.FileTree
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.language.base.plugins.LifecycleBasePlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/** Plugin responsible for applying configurations working on KDoc. */
public class KDocPlugin : Plugin<Project> {
    /** Applies this plugin to the specified [project]. */
    override fun apply(project: Project): Unit =
        project.extractKotlinSamplesTask()
}

private fun Project.extractKotlinSamplesTask() {
    val sources: Provider<FileTree> = tasks
        .named<KotlinCompile>("compileKotlin")
        .map { it.sources.asFileTree }
    val output: Provider<Directory> = layout.buildDirectory.dir("samples")
    val extractKotlinSamples: TaskProvider<KotlinSamplesExtractor> =
        tasks.register<KotlinSamplesExtractor>("extractKotlinSamples") {
            this.sources.set(sources)
            this.output.set(output)
        }
    tasks.named(LifecycleBasePlugin.ASSEMBLE_TASK_NAME).configure {
        dependsOn += extractKotlinSamples
    }
}
