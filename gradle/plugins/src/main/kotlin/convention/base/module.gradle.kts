package convention.base

import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.kotools.types.gradle.internal.TaskGroup

plugins { base }

repositories.mavenCentral()

tasks.register("coordinates").configure {
    this.description =
        "Prints the coordinates (group, name and version)."
    this.group = TaskGroup.Module.toString()
    this.doLast {
        println("${project.group}:${project.name}:${project.version}")
    }
}

tasks.named<TaskReportTask>("tasks")
    .configure { this.displayGroup = TaskGroup.Module.toString() }

private val baseTaskNames: Set<String> =
    setOf("clean", "check", "assemble", "build")
tasks.named { it in baseTaskNames }
    .configureEach { this.group = TaskGroup.Module.toString() }

tasks.withType<AbstractTestTask>().configureEach {
    this.testLogging { this.events(TestLogEvent.FAILED) }
}
