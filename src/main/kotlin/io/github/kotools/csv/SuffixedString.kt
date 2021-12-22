package io.github.kotools.csv

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal fun <T : Any> csvFile(): ReadWriteProperty<T, String> =
    SuffixedString(".csv")

internal fun <T : Any> folder(): ReadWriteProperty<T, String> =
    SuffixedString("/")

internal class SuffixedString<in T : Any>(private val suffix: String) :
    ReadWriteProperty<T, String> {
    private var value: String = ""

    override fun getValue(thisRef: T, property: KProperty<*>): String = value

    override fun setValue(thisRef: T, property: KProperty<*>, value: String) {
        if (value.isBlank() || this.value.contains(value)) return
        this.value = if (value.endsWith(suffix)) value else "$value$suffix"
    }
}
