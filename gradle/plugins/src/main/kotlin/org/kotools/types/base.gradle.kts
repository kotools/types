package org.kotools.types

plugins { base }

tasks {
    val tasks: TaskReportTask by getting(TaskReportTask::class) {
        group = "lifecycle"
        description = "Displays the recommended lifecycle tasks available."
        displayGroup = group
    }
    listOf(assemble, build, check, clean).forEach {
        it.configure { group = tasks.displayGroup }
    }
}
