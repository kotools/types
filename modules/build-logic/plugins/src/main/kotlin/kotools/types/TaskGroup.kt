package kotools.types

internal enum class TaskGroup {
    BUILD, INFORMATION, VERIFICATION;

    override fun toString(): String = name.toLowerCase()

    companion object {
        val all: List<TaskGroup> by lazy { values().toList() }
    }
}
