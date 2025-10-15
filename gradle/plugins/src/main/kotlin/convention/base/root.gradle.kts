package convention.base

import org.kotools.types.gradle.internal.TaskGroup

plugins { base }

repositories.mavenCentral()

tasks.named<TaskReportTask>("tasks")
    .configure { this.displayGroup = TaskGroup.Root.toString() }

private val baseTaskNames: Set<String> = setOf("clean", "assemble")
tasks.named { it in baseTaskNames }
    .configureEach { this.group = TaskGroup.Root.toString() }
