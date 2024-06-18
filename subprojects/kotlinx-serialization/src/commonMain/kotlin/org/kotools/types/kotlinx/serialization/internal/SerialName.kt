package org.kotools.types.kotlinx.serialization.internal

import org.kotools.types.internal.Warning

@Suppress(Warning.UNUSED)
internal expect inline fun <reified T : Any> serialNameOf(): String
