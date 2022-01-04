package io.github.kotools.csv

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal class SuffixedString<in T : Any>
private constructor(private val suffix: String) : ReadWriteProperty<T, String> {
    private var value: String = ""

    override fun getValue(thisRef: T, property: KProperty<*>): String = value

    override fun setValue(thisRef: T, property: KProperty<*>, value: String) {
        value.takeIf(String::isNotBlank)
            ?.let { if (it.endsWith(suffix)) it else "$it$suffix" }
            ?.takeIf { this.value != it }
            ?.let { this.value = it }
    }

    companion object {
        fun <T : Any> csvFile(): ReadWriteProperty<T, String> =
            SuffixedString(".csv")

        fun <T : Any> folder(): ReadWriteProperty<T, String> =
            SuffixedString("/")
    }
}
