package com.github.kotools.csvfile.core

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal fun <T : Any> fileName(): ReadWriteProperty<T, String?> = FileName()

internal class FileName<in T : Any> : ReadWriteProperty<T, String?> {
    private var name: String? = null

    override fun getValue(thisRef: T, property: KProperty<*>): String? = name

    override fun setValue(thisRef: T, property: KProperty<*>, value: String?) {
        value?.run {
            name = takeIf { it.endsWith(EXTENSION) } ?: "$this$EXTENSION"
        }
    }

    private companion object {
        private const val EXTENSION: String = ".csv"
    }
}
