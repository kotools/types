package kotools.types

plugins { base }

group = "org.kotools"

tasks {
    named<TaskReportTask>("tasks") {
        displayGroups = TaskGroup.all.map { "$it" }
    }
    build { description = "Assembles and checks this project." }
    TaskGroup.LIFECYCLE += listOf(assemble, build, check)
    val coordinates: TaskProvider<Task> = register("coordinates") {
        description = "Shows the coordinates 'group:module:version'."
        doLast {
            println("${project.group}:${rootProject.name}:${project.version}")
        }
    }
    val version: TaskProvider<Task> = register("version") {
        description = "Shows this project's version."
        doLast { println(project.version) }
    }
    TaskGroup.INFORMATION += listOf(coordinates, version)
}
