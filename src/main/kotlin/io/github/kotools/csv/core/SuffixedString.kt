package io.github.kotools.csv.core

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal fun <T : Any> file(): ReadWriteProperty<T, String> =
    SuffixedString(suffix = ".csv")

internal fun <T : Any> folder(): ReadWriteProperty<T, String> =
    SuffixedString(suffix = "/")

internal class SuffixedString<in T : Any>(private val suffix: String) :
    ReadWriteProperty<T, String> {
    private var value: String = ""

    override fun getValue(thisRef: T, property: KProperty<*>): String = value

    override fun setValue(thisRef: T, property: KProperty<*>, value: String) {
        if (value.isNotBlank() && !this.value.contains(value))
            this.value = value.takeIf { it.endsWith(suffix) } ?: "$value$suffix"
    }
}
