package convention.base

plugins { base }

repositories.mavenCentral()

tasks.named<TaskReportTask>("tasks").configure {
    displayGroup = TaskGroup.Root.toString()
}

tasks.named(BasePlugin.ASSEMBLE_TASK_NAME).configure {
    group = TaskGroup.Root.toString()
}
