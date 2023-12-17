/*
 * Copyright 2022-2023 Loïc Lamarque and Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.text

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.types.experimental.ExperimentalKotoolsTypesApi
import kotools.types.experimental.ExperimentalTextApi
import kotools.types.experimental.plus
import kotools.types.internal.ExperimentalSince
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.Since
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyPositiveInt
import kotlin.jvm.JvmInline

/**
 * Returns this string as an encapsulated [NotBlankString], or returns an
 * encapsulated [IllegalArgumentException] if this string is
 * [blank][String.isBlank].
 */
@Since(KotoolsTypesVersion.V4_0_0)
public fun String.toNotBlankString(): Result<NotBlankString> =
    runCatching { NotBlankString(this) }

/**
 * Represents a string that has at least one character excluding whitespaces.
 */
@JvmInline
@Serializable(NotBlankStringSerializer::class)
@Since(KotoolsTypesVersion.V4_0_0)
public value class NotBlankString internal constructor(
    private val value: String
) : Comparable<NotBlankString> {
    /** Returns the length of this string. */
    public val length: StrictlyPositiveInt
        get() = value.length.toStrictlyPositiveInt()
            .getOrThrow()

    init {
        require(value.isNotBlank()) { NotBlankStringException.message }
    }

    /**
     * Compares this string alphabetically with the [other] one for order.
     * Returns zero if this string equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     */
    @Since(KotoolsTypesVersion.V4_1_0)
    override infix fun compareTo(other: NotBlankString): Int =
        "$this".compareTo("$other")

    /** Returns this string as a [String]. */
    override fun toString(): String = value
}

/** Concatenates this string with the [other] character. */
@ExperimentalSince(KotoolsTypesVersion.V4_2_0)
@ExperimentalTextApi
@OptIn(ExperimentalKotoolsTypesApi::class)
public operator fun NotBlankString.plus(other: Char): NotBlankString =
    plus("$other")

/** Concatenates this character with the [other] string. */
@ExperimentalSince(KotoolsTypesVersion.V4_2_0)
@ExperimentalTextApi
public operator fun Char.plus(other: NotBlankString): NotBlankString =
    plus("$other")
        .toNotBlankString()
        .getOrThrow()

internal object NotBlankStringSerializer : KSerializer<NotBlankString> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "${KotoolsTypesPackage.Text}.NotBlankString",
        PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: NotBlankString): Unit =
        encoder.encodeString("$value")

    override fun deserialize(decoder: Decoder): NotBlankString = decoder
        .decodeString()
        .toNotBlankString()
        .getOrNull()
        ?: throw SerializationException(NotBlankStringException)
}

internal object NotBlankStringException : IllegalArgumentException() {
    override val message: String = "Given string shouldn't be blank."
}
