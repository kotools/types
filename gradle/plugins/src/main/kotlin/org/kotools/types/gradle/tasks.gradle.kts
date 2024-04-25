package org.kotools.types.gradle

plugins { base }

private val devTaskGroup: String = "Development"

private val devTasks by tasks.registering(TaskReportTask::class) {
    description = "Displays the recommended tasks for development."
    group = devTaskGroup
    displayGroup = devTaskGroup
}

tasks.assemble.configure { group = devTaskGroup }
tasks.build.configure { group = devTaskGroup }
tasks.check.configure { group = devTaskGroup }
tasks.clean.configure { group = devTaskGroup }
