package org.kotools.types

internal inline fun repeatTest(block: () -> Unit): Unit =
    repeat(times = 1_000) { block() }
