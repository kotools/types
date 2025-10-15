package convention.base

import org.kotools.types.gradle.internal.TaskGroup

plugins { base }

repositories.mavenCentral()

tasks.named<TaskReportTask>("tasks")
    .configure { this.displayGroup = TaskGroup.Root.toString() }

tasks.named("clean")
    .configure { this.group = TaskGroup.Root.toString() }
