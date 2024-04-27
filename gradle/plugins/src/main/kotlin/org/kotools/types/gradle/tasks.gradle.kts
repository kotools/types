package org.kotools.types.gradle

private val extension: TasksPluginExtension = extensions.create("devTasks")

private val devTasks by tasks.registering(TaskReportTask::class) {
    description = "Displays the recommended tasks for development."
    val devTaskGroup: String = extension.groupName.get()
    group = devTaskGroup
    displayGroup = devTaskGroup
}
