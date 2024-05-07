package org.kotools.types.gradle

import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory
import org.gradle.api.tasks.TaskProvider
import javax.inject.Inject

/** Plugin extension for configuring [DevTasksPlugin]. */
public abstract class DevTasksExtension(
    @Inject private val providerFactory: ProviderFactory
) {
    @get:JvmSynthetic
    internal val groupName: Provider<String> = this.providerFactory.provider {
        "Development"
    }

    /** Adds the specified tasks to the development tasks group. */
    public fun list(first: TaskProvider<*>, vararg others: TaskProvider<*>) {
        val tasks: List<TaskProvider<*>> = listOf(first) + others
        tasks.forEach(this::addToDevTasks)
    }

    private fun addToDevTasks(task: TaskProvider<*>): Unit = task.configure {
        this.group = this@DevTasksExtension.groupName.get()
    }
}
