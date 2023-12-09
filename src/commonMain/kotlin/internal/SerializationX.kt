/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.internal

import kotlin.jvm.JvmSynthetic

@JvmSynthetic
internal inline fun <reified T : Any> deserializationErrorMessage(
    decodedValue: Any
): String {
    val typeName: String = T::class.simpleName
        ?: error("Simple name shouldn't be null")
    return "Unable to deserialize '$decodedValue' to $typeName."
}
