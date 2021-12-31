package io.github.kotools.csv.deprecated

internal sealed class ListHolder<T : Any> : Configurable {
    val items: List<T> get() = mutableList
    private val mutableList: MutableList<T> = mutableListOf()

    override fun isValid(): Boolean = items.isNotEmpty()

    operator fun T.unaryPlus() {
        mutableList += this
    }
}
