package com.github.kotools.csvfile.core

internal fun interface Process<out R> {
    fun process(): R?
}
