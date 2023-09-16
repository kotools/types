package kotools.types

plugins { base }

group = "org.kotools"

tasks.named<TaskReportTask>("tasks").configure {
    display(TaskGroup.RECOMMENDED)
}

tasks.register("coordinates").configure {
    group(TaskGroup.INFORMATION)
    description("Prints this project's coordinates ('group:artifact:version').")
    doLast {
        val group = "${project.group}"
        val artifact: String = rootProject.name
        val version = "${project.version}"
        println("$group:$artifact:$version")
    }
}

tasks.register<Task>("version").configure {
    group(TaskGroup.INFORMATION)
    description("Prints this project's version.")
    doLast { println(project.version) }
}
