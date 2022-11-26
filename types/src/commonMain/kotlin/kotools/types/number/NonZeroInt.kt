package kotools.types.number

import kotlinx.serialization.Serializable
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.Package
import kotools.types.failureOf
import kotools.types.toSuccessfulResult
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns this value as a [NonZeroInt], or an [IllegalArgumentException] if
 * this value equals zero.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public val Int.nonZero: Result<NonZeroInt>
    get() = takeIf { it != 0 }
        ?.toSuccessfulResult(::NonZeroIntImplementation)
        ?: failureOf { this shouldBe otherThanZero }

/**
 * Returns the [value] as a [NonZeroInt], or returns `null` if the [value]
 * equals zero.
 */
@Deprecated(
    "Use the Int.nonZero property instead.",
    ReplaceWith("value.nonZero.getOrNull()", "${Package.number}.nonZero"),
    DeprecationLevel.ERROR
)
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun nonZeroIntOrNull(value: Int): NonZeroInt? = value.takeIf { it != 0 }
    ?.let(::NonZeroIntImplementation)

/**
 * Returns the [value] as a [NonZeroInt], or throws an
 * [IllegalArgumentException] if the [value] equals zero.
 */
@Deprecated(
    "Use the Int.nonZero property instead.",
    ReplaceWith("value.nonZero.getOrThrow()", "${Package.number}.nonZero"),
    DeprecationLevel.ERROR
)
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun nonZeroIntOrThrow(value: Int): NonZeroInt =
    value.nonZero.getOrThrow()

/**
 * Returns the [value] as a [NonZeroInt], or throws an
 * [NonZeroInt.ConstructionError] if the [value] equals zero.
 */
@Deprecated(
    "Use the Int.nonZero property instead.",
    ReplaceWith("value.nonZero.getOrThrow()", "${Package.number}.nonZero")
)
@SinceKotools(Types, "1.1")
@Suppress("DEPRECATION")
@Throws(NonZeroInt.ConstructionError::class)
public fun NonZeroInt(value: Int): NonZeroInt = value.nonZero.getOrNull()
    ?: throw NonZeroInt.ConstructionError

/**
 * Returns the [value] as a [NonZeroInt], or returns `null` if the [value]
 * equals zero.
 */
@Deprecated(
    "Use the Int.nonZero property instead.",
    ReplaceWith("value.nonZero.getOrNull()", "${Package.number}.nonZero")
)
@SinceKotools(Types, "3.0")
@Suppress("FunctionName")
public fun NonZeroIntOrNull(value: Int): NonZeroInt? = value.nonZero.getOrNull()

/**
 * Returns this value as a [NonZeroInt], or throws an
 * [NonZeroInt.ConstructionError] if this value equals zero.
 */
@Deprecated(
    "Use the Int.nonZero property instead.",
    ReplaceWith("this.nonZero.getOrThrow()", "${Package.number}.nonZero")
)
@SinceKotools(Types, "1.1")
@Suppress("DEPRECATION")
@Throws(NonZeroInt.ConstructionError::class)
public fun Int.toNonZeroInt(): NonZeroInt = NonZeroInt(this)

/**
 * Returns this value as a [NonZeroInt], or returns `null` if this value equals
 * zero.
 */
@Deprecated(
    "Use the Int.nonZero property instead.",
    ReplaceWith("this.nonZero.getOrNull()", "${Package.number}.nonZero")
)
@SinceKotools(Types, "1.1")
public fun Int.toNonZeroIntOrNull(): NonZeroInt? = this.nonZero.getOrNull()

/**
 * Returns this value as a [NonZeroInt], or throws a [IllegalArgumentException]
 * if this value equals zero.
 */
@Deprecated(
    "Use the Int.nonZero property instead.",
    ReplaceWith("this.nonZero.getOrThrow()", "${Package.number}.nonZero"),
    DeprecationLevel.ERROR
)
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun Int.toNonZeroIntOrThrow(): NonZeroInt = nonZero.getOrThrow()

/** Returns a random [NonZeroInt]. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun randomNonZeroInt(): NonZeroInt = NonZeroInt.ranges.random()
    .random()
    .nonZero.getOrThrow()

// ---------- Binary operations ----------

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to zero.
 */
@SinceKotools(Types, "1.1")
public operator fun Int.div(other: NonZeroInt): Int = div(other.value)

/** Representation of integers other than zero. */
@Serializable(NonZeroIntSerializer::class)
@SinceKotools(Types, "1.1")
public sealed interface NonZeroInt : IntHolder {
    // ---------- Unary operations ----------

    /**
     * Returns this [value] incremented by one.
     * If this [value] equals `-1`, it returns `1` instead.
     * If this [value] is the maximum, it returns the minimum value instead.
     */
    override fun inc(): NonZeroInt = when (value) {
        -1 -> 1.nonZero.getOrThrow()
        max.value -> min
        else -> (value + 1).nonZero.getOrThrow()
    }

    /**
     * Returns this [value] decremented by one.
     * If this [value] equals `1`, it returns `-1` instead.
     * If this [value] is the [minimum][NonZeroInt.min], it returns the
     * [maximum][NonZeroInt.max] value instead.
     */
    override fun dec(): NonZeroInt = when (value) {
        1 -> (-1).nonZero.getOrThrow()
        min.value -> max
        else -> (value - 1).nonZero.getOrThrow()
    }

    override fun unaryMinus(): NonZeroInt = (-value).nonZero.getOrThrow()

    // ---------- Binary operations ----------

    /** Multiplies this [value] by the [other] value. */
    public operator fun times(other: NonZeroInt): NonZeroInt =
        (value * other.value).nonZero.getOrThrow()

    /** Contains declarations for holding or building a [NonZeroInt]. */
    public companion object {
        private val negativeRange: IntRange by lazy(
            StrictlyNegativeInt.Companion::range
        )
        private val positiveRange: IntRange by lazy(
            StrictlyPositiveInt.Companion::range
        )
        internal val ranges: Set<IntRange> by lazy {
            setOf(negativeRange, positiveRange)
        }

        /** The minimum value of a [NonZeroInt]. */
        public val min: NonZeroInt by lazy(
            negativeRange.first.nonZero::getOrThrow
        )

        /** The maximum value of a [NonZeroInt]. */
        public val max: NonZeroInt by lazy(
            positiveRange.last.nonZero::getOrThrow
        )

        /**
         * Returns the [value] as a [NonZeroInt], or returns `null` if the
         * [value] equals 0.
         */
        @Deprecated(
            "Use the NonZeroIntOrNull function instead.",
            ReplaceWith(
                "NonZeroIntOrNull(value)",
                "${Package.number}.NonZeroIntOrNull"
            )
        )
        public infix fun orNull(value: Int): NonZeroInt? =
            value.nonZero.getOrNull()

        /** Returns a random [NonZeroInt]. */
        @Deprecated(
            "Use the randomNonZeroInt function instead.",
            ReplaceWith(
                "randomNonZeroInt()",
                "${Package.number}.randomNonZeroInt"
            )
        )
        @SinceKotools(Types, "3.0")
        public fun random(): NonZeroInt = ranges.random()
            .random()
            .nonZero.getOrThrow()
    }

    /** Error thrown when creating a [NonZeroInt] fails. */
    @Deprecated(
        "Use the IllegalArgumentException instead.",
        ReplaceWith("IllegalArgumentException")
    )
    @SinceKotools(Types, "3.0")
    public object ConstructionError :
        IllegalArgumentException("Given value shouldn't equal 0.")
}

internal object NonZeroIntSerializer :
    IntHolder.Serializer<NonZeroInt>({ it.nonZero.getOrThrow() })

@JvmInline
private value class NonZeroIntImplementation(override val value: Int) :
    NonZeroInt {
    override fun toString(): String = value.toString()
}
