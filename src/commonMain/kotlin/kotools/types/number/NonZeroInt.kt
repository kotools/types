package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.collection.NotEmptySet
import kotools.types.collection.notEmptySetOf
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

/** Representation of integers other than [zero][ZeroInt]. */
@Serializable(NonZeroIntSerializer::class)
@SinceKotoolsTypes("1.1")
public sealed interface NonZeroInt : AnyInt {
    /** Contains declarations for holding or building a [NonZeroInt]. */
    public companion object {
        /** The minimum value a [NonZeroInt] can have. */
        public val min: StrictlyNegativeInt by lazy(
            StrictlyNegativeInt.Companion::min
        )

        /** The maximum value a [NonZeroInt] can have. */
        public val max: StrictlyPositiveInt by lazy(
            StrictlyPositiveInt.Companion::max
        )

        /** Returns a random [NonZeroInt]. */
        @SinceKotoolsTypes("3.0")
        public fun random(): NonZeroInt {
            val ranges: NotEmptySet<IntRange> = notEmptySetOf(
                min.toInt()..StrictlyNegativeInt.max.toInt(),
                StrictlyPositiveInt.min.toInt()..max.toInt()
            )
            return ranges.toSet()
                .random()
                .random()
                .toNonZeroInt()
                .getOrThrow()
        }
    }
}

/**
 * Returns this integer as a [NonZeroInt], or returns an
 * [IllegalArgumentException] if this integer equals [zero][ZeroInt].
 */
@SinceKotoolsTypes("4.0")
public fun Int.toNonZeroInt(): Result<NonZeroInt> = when {
    this > ZeroInt.toInt() -> toStrictlyPositiveInt()
    this < ZeroInt.toInt() -> toStrictlyNegativeInt()
    else -> Result.failure(this shouldBe otherThanZero)
}

internal object NonZeroIntSerializer : AnyIntSerializer<NonZeroInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.NonZeroInt"::toNotBlankString
    )

    override fun deserialize(value: Int): NonZeroInt = value.toNonZeroInt()
        .getOrNull()
        ?: throw SerializationException(value shouldBe otherThanZero)
}
