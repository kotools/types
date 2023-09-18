package kotools.types

plugins { base }

group = "org.kotools"

tasks {
    named<TaskReportTask>("tasks") {
        displayGroups = TaskGroup.all.map { "$it" }
    }
    build { description = "Assembles and checks this project." }
    register("coordinates") {
        group = "${TaskGroup.INFORMATION}"
        description = "Shows the coordinates 'group:module:version'."
        doLast { println("${project.group}:types:${project.version}") }
    }
}
