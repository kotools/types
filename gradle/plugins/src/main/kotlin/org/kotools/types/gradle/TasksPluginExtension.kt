package org.kotools.types.gradle

import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory
import org.gradle.api.tasks.TaskProvider
import javax.inject.Inject

/** Gradle plugin extension for configuring the [TasksPlugin]. */
public abstract class TasksPluginExtension(
    @Inject private val providerFactory: ProviderFactory
) {
    @get:JvmSynthetic
    internal val groupName: Provider<String> = this.providerFactory.provider {
        "Development"
    }

    /** Adds the specified tasks to the development tasks group. */
    public fun list(first: TaskProvider<*>, vararg others: TaskProvider<*>) {
        val tasks: List<TaskProvider<*>> = listOf(first) + others
        tasks.forEach {
            it.configure { group = this@TasksPluginExtension.groupName.get() }
        }
    }
}
