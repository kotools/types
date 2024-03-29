package kotools.types.base

import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.diagnostics.TaskReportTask
import org.gradle.kotlin.dsl.register

/**
 * Configures the recommendation of tasks through a new `recommendedTasks`
 * Gradle task.
 *
 * @constructor Creates an instance of [TaskRecommendation] by registering a new
 * `recommendedTasks` Gradle task using the specified [container].
 */
internal class TaskRecommendation(private val container: TaskContainer) {
    init {
        this.container.register<TaskReportTask>("recommendedTasks").configure {
            this.group = TASK_GROUP
            this.description =
                "Displays the recommended tasks available for this project."
            this.displayGroup = TASK_GROUP
        }
    }

    /**
     * Adds the tasks corresponding to the specified [names] to the recommended
     * ones.
     */
    operator fun plusAssign(names: List<String>): Unit = names
        .mapNotNull(this.container::named)
        .forEach {
            it.configure { group = TASK_GROUP }
        }

    private companion object {
        private const val TASK_GROUP: String = "Recommended"
    }
}
