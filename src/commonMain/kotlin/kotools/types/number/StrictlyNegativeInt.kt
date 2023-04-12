package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.range.InclusiveBound
import kotools.types.range.NotEmptyRange
import kotools.types.range.toInclusiveBound
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString
import kotlin.jvm.JvmInline

/** Representation of negative integers excluding [zero][ZeroInt]. */
@JvmInline
@Serializable(StrictlyNegativeIntSerializer::class)
@SinceKotoolsTypes("1.1")
public value class StrictlyNegativeInt
private constructor(private val value: Int) : NonZeroInt, NegativeInt {
    /**
     * Contains declarations for holding or building a [StrictlyNegativeInt].
     */
    public companion object {
        /** The minimum value a [StrictlyNegativeInt] can have. */
        @Deprecated(
            "Use the range property instead.",
            ReplaceWith("StrictlyNegativeInt.range.start.value")
        )
        public val min: StrictlyNegativeInt by lazy(
            Int.MIN_VALUE.toStrictlyNegativeInt()::getOrThrow
        )

        /** The maximum value a [StrictlyNegativeInt] can have. */
        @Deprecated(
            "Use the range property instead.",
            ReplaceWith("StrictlyNegativeInt.range.end.value")
        )
        public val max: StrictlyNegativeInt by lazy(
            (-1).toStrictlyNegativeInt()::getOrThrow
        )

        /** The range of values a [StrictlyNegativeInt] can have. */
        @SinceKotoolsTypes("4.2")
        public val range: NotEmptyRange<StrictlyNegativeInt> by lazy {
            val start: InclusiveBound<StrictlyNegativeInt> = Int.MIN_VALUE
                .toStrictlyNegativeInt()
                .getOrThrow()
                .toInclusiveBound()
            val end: InclusiveBound<StrictlyNegativeInt> = (-1)
                .toStrictlyNegativeInt()
                .getOrThrow()
                .toInclusiveBound()
            start..end
        }

        internal infix fun of(value: Int): Result<StrictlyNegativeInt> = value
            .takeIf { it < ZeroInt.toInt() }
            ?.runCatching { StrictlyNegativeInt(this) }
            ?: Result.failure(value shouldBe aStrictlyNegativeNumber)

        /** Returns a random [StrictlyNegativeInt]. */
        @SinceKotoolsTypes("3.0")
        public fun random(): StrictlyNegativeInt = range.toIntRange()
            .random()
            .toStrictlyNegativeInt()
            .getOrThrow()
    }

    @SinceKotoolsTypes("4.0")
    override fun toInt(): Int = value

    @SinceKotoolsTypes("4.0")
    override fun toString(): String = "$value"
}

/**
 * Returns this number as an encapsulated [StrictlyNegativeInt], which may
 * involve rounding or truncation, or returns an encapsulated
 * [IllegalArgumentException] if this number is [positive][PositiveInt].
 */
@SinceKotoolsTypes("4.1")
public fun Number.toStrictlyNegativeInt(): Result<StrictlyNegativeInt> =
    StrictlyNegativeInt of toInt()

internal object StrictlyNegativeIntSerializer :
    AnyIntSerializer<StrictlyNegativeInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.StrictlyNegativeInt"::toNotBlankString
    )

    override fun deserialize(value: Int): StrictlyNegativeInt = value
        .toStrictlyNegativeInt()
        .getOrNull()
        ?: throw SerializationException(value shouldBe aStrictlyNegativeNumber)
}
