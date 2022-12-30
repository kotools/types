package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.collection.NotEmptySet
import kotools.types.collection.notEmptySetOf
import kotools.types.text.NotBlankString
import kotools.types.text.asNotBlankString

/**
 * Representation of integers other than [zero][ZeroInt].
 *
 * See the [asNonZeroInt] property for building a [NonZeroInt].
 */
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
                min.asInt..StrictlyNegativeInt.max.asInt,
                StrictlyPositiveInt.min.asInt..max.asInt
            )
            return ranges.asSet.random()
                .random()
                .asNonZeroInt
                .getOrThrow()
        }
    }
}

/**
 * Returns this integer as a [NonZeroInt], or returns an
 * [IllegalArgumentException] if this integer equals [zero][ZeroInt].
 */
@SinceKotoolsTypes("4.0")
public val Int.asNonZeroInt: Result<NonZeroInt>
    get() = when {
        this > ZeroInt.asInt -> asStrictlyPositiveInt
        this < ZeroInt.asInt -> asStrictlyNegativeInt
        else -> Result.failure(this shouldBe otherThanZero)
    }

internal object NonZeroIntSerializer : AnyIntSerializer<NonZeroInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.NonZeroInt"::asNotBlankString
    )

    override fun deserialize(value: Int): NonZeroInt = value.asNonZeroInt
        .getOrNull()
        ?: throw SerializationException(value shouldBe otherThanZero)
}
