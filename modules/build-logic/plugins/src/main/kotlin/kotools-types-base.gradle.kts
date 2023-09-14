plugins { base }

tasks {
    val taskGroup = "Kotools Types"
    named<TaskReportTask>("tasks") { displayGroup = taskGroup }
    assemble { group = taskGroup }
    check {
        group = taskGroup
        description = "Runs all checks of this project."
    }
    build {
        group = taskGroup
        description = "Assembles and runs all checks of this project."
    }
    register("coordinates") {
        group = taskGroup
        description = "Shows the coordinates of this project."
        doLast { println("${project.group}:types:${project.version}") }
    }
}
