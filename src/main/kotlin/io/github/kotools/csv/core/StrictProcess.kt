package io.github.kotools.csv.core

internal fun interface StrictProcess<out R> {
    fun process(): R
}
