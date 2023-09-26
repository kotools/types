package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.ExperimentalSinceKotoolsTypes
import kotools.types.NUMBER_PACKAGE
import kotools.types.SinceKotoolsTypes
import kotools.types.experimental.ExperimentalNumberApi
import kotools.types.experimental.ExperimentalRangeApi
import kotools.types.range.NotEmptyRange
import kotools.types.range.notEmptyRangeOf
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
internal fun Int.isStrictlyNegative(): Boolean = this < 0

/**
 * Returns this number as an encapsulated [StrictlyNegativeInt], which may
 * involve rounding or truncation, or returns an encapsulated
 * [IllegalArgumentException] if this number is [positive][PositiveInt].
 */
@SinceKotoolsTypes("4.1")
public fun Number.toStrictlyNegativeInt(): Result<StrictlyNegativeInt> =
    runCatching {
        val value: Int = toInt()
        StrictlyNegativeInt.of(value)
    }

/**
 * Returns this number as a [StrictlyNegativeInt], which may involve rounding or
 * truncation, or returns `null` if this number is [positive][PositiveInt].
 *
 * Here's some usage examples:
 *
 * ```kotlin
 * var result: StrictlyNegativeInt? = (-1).toStrictlyNegativeIntOrNull()
 * println(result) // -1
 *
 * result = 0.toStrictlyNegativeIntOrNull()
 * println(result) // null
 *
 * result = 1.toStrictlyNegativeIntOrNull()
 * println(result) // null
 * ```
 *
 * You can use the [toStrictlyNegativeIntOrThrow] function for throwing an
 * [IllegalArgumentException] instead of returning `null` when this number is
 * [positive][PositiveInt].
 */
@ExperimentalNumberApi
@ExperimentalSinceKotoolsTypes("4.3.1")
public fun Number.toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? = toInt()
    .takeIf { it.isStrictlyNegative() }
    ?.toStrictlyNegativeIntOrThrow()

/**
 * Returns this number as a [StrictlyNegativeInt], which may involve rounding or
 * truncation, or throws an [IllegalArgumentException] if this number is
 * [positive][PositiveInt].
 *
 * Here's some usage examples:
 *
 * ```kotlin
 * val result: StrictlyNegativeInt = (-1).toStrictlyNegativeIntOrThrow()
 * println(result) // 1
 *
 * 0.toStrictlyNegativeIntOrThrow() // IllegalArgumentException
 * 1.toStrictlyNegativeIntOrThrow() // IllegalArgumentException
 * ```
 *
 * You can use the [toStrictlyNegativeIntOrNull] function for returning `null`
 * instead of throwing an [IllegalArgumentException] when this number is
 * [positive][PositiveInt].
 */
@ExperimentalNumberApi
@ExperimentalSinceKotoolsTypes("4.3.1")
public fun Number.toStrictlyNegativeIntOrThrow(): StrictlyNegativeInt = toInt()
    .toStrictlyNegativeIntOrThrow()

private fun Int.toStrictlyNegativeIntOrThrow(): StrictlyNegativeInt =
    StrictlyNegativeInt.of(this)

/** Representation of negative integers excluding [zero][ZeroInt]. */
@JvmInline
@Serializable(StrictlyNegativeIntSerializer::class)
@SinceKotoolsTypes("1.1")
public value class StrictlyNegativeInt
private constructor(private val value: Int) : NonZeroInt, NegativeInt {
    init {
        val isValid: Boolean = value.isStrictlyNegative()
        require(isValid) { value.shouldBeStrictlyNegativeMessage() }
    }

    @SinceKotoolsTypes("4.0")
    override fun toInt(): Int = value

    @SinceKotoolsTypes("4.0")
    override fun toString(): String = "$value"

    /**
     * Contains declarations for holding or building a [StrictlyNegativeInt].
     */
    public companion object {
        /** The minimum value a [StrictlyNegativeInt] can have. */
        public val min: StrictlyNegativeInt by lazy(
            Int.MIN_VALUE.toStrictlyNegativeInt()::getOrThrow
        )

        /** The maximum value a [StrictlyNegativeInt] can have. */
        public val max: StrictlyNegativeInt by lazy(
            (-1).toStrictlyNegativeInt()::getOrThrow
        )

        /** The range of values a [StrictlyNegativeInt] can have. */
        @ExperimentalRangeApi
        @ExperimentalSinceKotoolsTypes("4.2")
        public val range: NotEmptyRange<StrictlyNegativeInt> by lazy {
            val start: StrictlyNegativeInt = Int.MIN_VALUE
                .toStrictlyNegativeInt()
                .getOrThrow()
            val end: StrictlyNegativeInt = (-1).toStrictlyNegativeInt()
                .getOrThrow()
            notEmptyRangeOf { start.inclusive to end.inclusive }
        }

        @JvmSynthetic
        internal fun of(value: Int): StrictlyNegativeInt =
            StrictlyNegativeInt(value)

        /** Returns a random [StrictlyNegativeInt]. */
        @SinceKotoolsTypes("3.0")
        public fun random(): StrictlyNegativeInt = (min.value..max.value)
            .random()
            .toStrictlyNegativeInt()
            .getOrThrow()
    }
}

/** Returns the negative of this integer. */
@ExperimentalNumberApi
@ExperimentalSinceKotoolsTypes("4.2")
public operator fun StrictlyNegativeInt.unaryMinus(): StrictlyPositiveInt =
    toInt()
        .unaryMinus()
        .toStrictlyPositiveIntOrThrow()

internal object StrictlyNegativeIntSerializer :
    AnyIntSerializer<StrictlyNegativeInt> {
    override val serialName: Result<NotBlankString> by lazy {
        "$NUMBER_PACKAGE.StrictlyNegativeInt".toNotBlankString()
    }

    override fun deserialize(value: Int): StrictlyNegativeInt = value
        .toStrictlyNegativeInt()
        .getOrNull()
        ?: value.let {
            val message: String = value.shouldBeStrictlyNegativeMessage()
            throw SerializationException(message)
        }
}

@JvmSynthetic
internal fun Number.shouldBeStrictlyNegativeMessage(): String =
    "Number should be strictly negative (tried with $this)."
