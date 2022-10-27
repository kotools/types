package io.github.kotools.csv.common

internal abstract class ListHolder<T : Any> {
    val items: List<T> get() = mutableList
    private val mutableList: MutableList<T> = mutableListOf()

    fun isValid(): Boolean = items.isNotEmpty()

    operator fun T.unaryPlus() {
        mutableList += this
    }
}
