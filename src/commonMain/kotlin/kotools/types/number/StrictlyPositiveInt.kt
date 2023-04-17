package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.range.InclusiveBound
import kotools.types.range.NotEmptyRange
import kotools.types.range.rangeTo
import kotools.types.range.toInclusiveBound
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString
import kotlin.jvm.JvmInline

/** Representation of positive integers excluding [zero][ZeroInt]. */
@JvmInline
@Serializable(StrictlyPositiveIntSerializer::class)
@SinceKotoolsTypes("1.1")
public value class StrictlyPositiveInt
private constructor(private val value: Int) : NonZeroInt, PositiveInt {
    /**
     * Contains declarations for holding or building a [StrictlyPositiveInt].
     */
    public companion object {
        /** The minimum value a [StrictlyPositiveInt] can have. */
        @Deprecated(
            "Use the range property instead.",
            ReplaceWith("StrictlyPositiveInt.range.start.value")
        )
        public val min: StrictlyPositiveInt by lazy(
            1.toStrictlyPositiveInt()::getOrThrow
        )

        /** The maximum value a [StrictlyPositiveInt] can have. */
        @Deprecated(
            "Use the range property instead.",
            ReplaceWith("StrictlyPositiveInt.range.end.value")
        )
        public val max: StrictlyPositiveInt by lazy(
            Int.MAX_VALUE.toStrictlyPositiveInt()::getOrThrow
        )

        /** The range of values a [StrictlyPositiveInt] can have. */
        @SinceKotoolsTypes("4.2")
        public val range: NotEmptyRange<StrictlyPositiveInt> by lazy {
            val start: InclusiveBound<StrictlyPositiveInt> = 1
                .toStrictlyPositiveInt()
                .getOrThrow()
                .toInclusiveBound()
            val end: InclusiveBound<StrictlyPositiveInt> = Int.MAX_VALUE
                .toStrictlyPositiveInt()
                .getOrThrow()
                .toInclusiveBound()
            start..end
        }

        internal infix fun of(value: Int): Result<StrictlyPositiveInt> = value
            .takeIf { it > ZeroInt.toInt() }
            ?.runCatching { StrictlyPositiveInt(this) }
            ?: Result.failure(value shouldBe aStrictlyPositiveNumber)

        /** Returns a random [StrictlyPositiveInt]. */
        @SinceKotoolsTypes("3.0")
        public fun random(): StrictlyPositiveInt = range.toIntRange()
            .random()
            .toStrictlyPositiveInt()
            .getOrThrow()
    }

    @SinceKotoolsTypes("4.0")
    override fun toInt(): Int = value

    @SinceKotoolsTypes("4.0")
    override fun toString(): String = "$value"
}

/**
 * Returns this number as an encapsulated [StrictlyPositiveInt], which may
 * involve rounding or truncation, or returns an encapsulated
 * [IllegalArgumentException] if this number is [negative][NegativeInt].
 */
@SinceKotoolsTypes("4.1")
public fun Number.toStrictlyPositiveInt(): Result<StrictlyPositiveInt> =
    StrictlyPositiveInt of toInt()

internal object StrictlyPositiveIntSerializer :
    AnyIntSerializer<StrictlyPositiveInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.StrictlyPositiveInt"::toNotBlankString
    )

    override fun deserialize(value: Int): StrictlyPositiveInt = value
        .toStrictlyPositiveInt()
        .getOrNull()
        ?: throw SerializationException(value shouldBe aStrictlyPositiveNumber)
}
