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
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
internal fun Int.isStrictlyPositive(): Boolean = this > 0

/**
 * Returns this number as an encapsulated [StrictlyPositiveInt], which may
 * involve rounding or truncation, or returns an encapsulated
 * [IllegalArgumentException] if this number is [negative][NegativeInt].
 */
@Since(KotoolsTypesVersion.V4_1_0)
public fun Number.toStrictlyPositiveInt(): Result<StrictlyPositiveInt> =
    runCatching { StrictlyPositiveInt(toInt()) }

/** Represents an integer number of type [Int] that is greater than zero. */
@JvmInline
@Serializable(StrictlyPositiveIntSerializer::class)
@Since(KotoolsTypesVersion.V1_1_0)
public value class StrictlyPositiveInt
internal constructor(private val value: Int) : NonZeroInt, PositiveInt {
    init {
        require(value.isStrictlyPositive()) { errorMessageFor(value) }
    }

    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toInt(): Int = value

    @Since(KotoolsTypesVersion.V4_0_0)
    override fun toString(): String = "$value"

    /**
     * Contains declarations for holding or building a [StrictlyPositiveInt].
     */
    public companion object {
        /** The minimum value a [StrictlyPositiveInt] can have. */
        public val min: StrictlyPositiveInt by lazy(
            1.toStrictlyPositiveInt()::getOrThrow
        )

        /** The maximum value a [StrictlyPositiveInt] can have. */
        public val max: StrictlyPositiveInt by lazy(
            Int.MAX_VALUE.toStrictlyPositiveInt()::getOrThrow
        )

        internal infix fun errorMessageFor(number: Number): NotBlankString =
            "Number should be strictly positive (tried with $number)."
                .toNotBlankString()
                .getOrThrow()

        /** Returns a random [StrictlyPositiveInt]. */
        @Since(KotoolsTypesVersion.V3_0_0)
        public fun random(): StrictlyPositiveInt = (min.value..max.value)
            .random()
            .toStrictlyPositiveInt()
            .getOrThrow()
    }
}

internal object StrictlyPositiveIntSerializer :
    AnyIntSerializer<StrictlyPositiveInt> {
    override val serialName: Result<NotBlankString> by lazy {
        "${Package.NUMBER}.StrictlyPositiveInt".toNotBlankString()
    }

    override fun deserialize(value: Int): StrictlyPositiveInt = value
        .toStrictlyPositiveInt()
        .getOrNull()
        ?: throw SerializationException(
            "${StrictlyPositiveInt errorMessageFor value}"
        )
}
