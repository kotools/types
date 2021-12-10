package com.github.kotools.csvfile.core

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal fun <T : Any> file(): ReadWriteProperty<T, String> = File()

internal class File<in T : Any> : ReadWriteProperty<T, String> {
    private var name: String = ""

    override fun getValue(thisRef: T, property: KProperty<*>): String = name

    override fun setValue(thisRef: T, property: KProperty<*>, value: String) {
        if (value.isNotBlank() && !name.contains(value))
            name = value.takeIf { it.endsWith(EXTENSION) } ?: "$value$EXTENSION"
    }

    private companion object {
        private const val EXTENSION: String = ".csv"
    }
}
