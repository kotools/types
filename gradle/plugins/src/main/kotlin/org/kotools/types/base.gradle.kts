package org.kotools.types

plugins { base }

private val recommendedTaskGroup = "Recommended"
tasks.register<TaskReportTask>("recommendedTasks").configure {
    group = recommendedTaskGroup
    description = "Displays the recommended tasks available for this project."
    displayGroup = recommendedTaskGroup
}
listOf(
    LifecycleBasePlugin.ASSEMBLE_TASK_NAME,
    LifecycleBasePlugin.BUILD_TASK_NAME,
    LifecycleBasePlugin.CHECK_TASK_NAME,
    LifecycleBasePlugin.CLEAN_TASK_NAME
).forEach {
    tasks.named(it).configure { group = recommendedTaskGroup }
}
