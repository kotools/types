package io.github.kotools.csv.old

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal fun <T : Dsl> file(): ReadWriteProperty<T, String> =
    SuffixedString(".csv")

internal fun <T : Dsl> folder(): ReadWriteProperty<T, String> =
    SuffixedString("/")

internal class SuffixedString<in T : Dsl>(private val suffix: String) :
    ReadWriteProperty<T, String> {
    private var value: String = ""

    override fun getValue(thisRef: T, property: KProperty<*>): String = value

    override fun setValue(thisRef: T, property: KProperty<*>, value: String) {
        if (value.isNotBlank() && !this.value.contains(value))
            this.value = value.takeIf { it.endsWith(suffix) } ?: "$value$suffix"
    }
}
