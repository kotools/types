package convention.base

plugins { base }

group = "org.kotools"

repositories.mavenCentral()

tasks.named<TaskReportTask>("tasks").configure {
    displayGroup = TaskGroup.Module.toString()
}

tasks.named(BasePlugin.CLEAN_TASK_NAME).configure {
    group = TaskGroup.Module.toString()
}

private val coordinates: TaskProvider<Task> by tasks.registering
coordinates.configure {
    description = "Prints the coordinates (group, name and version)."
    group = TaskGroup.Module.toString()
    doLast { println("${project.group}:${project.name}:$version") }
}
