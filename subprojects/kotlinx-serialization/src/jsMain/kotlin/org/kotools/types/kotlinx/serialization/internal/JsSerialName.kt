package org.kotools.types.kotlinx.serialization.internal

import kotools.types.internal.simpleNameOf

internal actual inline fun <reified T : Any> serialNameOf(): String =
    simpleNameOf<T>()
