package io.github.kotools.csv.common

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal fun <T : Manager> csvFile(): ReadWriteProperty<T, String> =
    SuffixedString(".csv")

internal fun <T : Manager> folder(): ReadWriteProperty<T, String> =
    SuffixedString("/")

private class SuffixedString<in T : Manager>(private val suffix: String) :
    ReadWriteProperty<T, String> {
    private var value: String = ""

    override fun getValue(thisRef: T, property: KProperty<*>): String = value

    override fun setValue(thisRef: T, property: KProperty<*>, value: String) {
        value.takeIf(String::isNotBlank)
            ?.let { if (it.endsWith(suffix)) it else "$it$suffix" }
            ?.takeIf { this.value != it }
            ?.let { this.value = it }
    }
}
