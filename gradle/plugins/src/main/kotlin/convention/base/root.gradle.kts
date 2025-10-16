package convention.base

plugins { base }

repositories.mavenCentral()

tasks.named<TaskReportTask>("tasks")
    .configure { this.displayGroup = TaskGroup.Root.toString() }

private val baseTaskNames: Set<String> = setOf("assemble", "clean")
tasks.named { it in baseTaskNames }
    .configureEach { this.group = TaskGroup.Root.toString() }
