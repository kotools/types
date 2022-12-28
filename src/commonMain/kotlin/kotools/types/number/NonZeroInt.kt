package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.text.NotBlankString
import kotools.types.text.asNotBlankString

/**
 * Representation of integers other than [zero][ZeroInt].
 *
 * See the [toNonZeroInt] function for building a [NonZeroInt].
 */
@Serializable(NonZeroIntSerializer::class)
@SinceKotoolsTypes("1.1")
public sealed interface NonZeroInt : AnyInt

/**
 * Returns this integer as a [NonZeroInt], or returns an
 * [IllegalArgumentException] if this integer equals [zero][ZeroInt].
 */
@SinceKotoolsTypes("1.1")
public fun Int.toNonZeroInt(): Result<NonZeroInt> = when {
    this > ZeroInt.asInt -> toStrictlyPositiveInt()
    this < ZeroInt.asInt -> toStrictlyNegativeInt()
    else -> Result.failure(this shouldBe otherThanZero)
}

internal object NonZeroIntSerializer : AnyIntSerializer<NonZeroInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.NonZeroInt"::asNotBlankString
    )

    override fun deserialize(value: Int): NonZeroInt = value.toNonZeroInt()
        .getOrNull()
        ?: throw SerializationException(value shouldBe otherThanZero)
}
