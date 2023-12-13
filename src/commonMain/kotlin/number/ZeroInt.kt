/*
 * Copyright 2022-2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.Package
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.Since
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

/** Represents an integer number of type [Int] that equals zero. */
@Serializable(ZeroIntSerializer::class)
@Since(KotoolsTypesVersion.V4_0_0)
public object ZeroInt : PositiveInt, NegativeInt {
    override fun toInt(): Int = 0
    override fun toString(): String = "${toInt()}"
}

internal object ZeroIntSerializer : AnyIntSerializer<ZeroInt> {
    override val serialName: Result<NotBlankString> by lazy {
        "${Package.NUMBER}.ZeroInt".toNotBlankString()
    }

    override fun deserialize(value: Int): ZeroInt = if (value == 0) ZeroInt
    else throw SerializationException(
        "Unable to deserialize $value to ZeroInt."
    )
}
