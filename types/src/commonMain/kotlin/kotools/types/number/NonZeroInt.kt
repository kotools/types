@file:Suppress("DEPRECATION")

package kotools.types.number

import kotlinx.serialization.Serializable
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.Package
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns the [value] as a [NonZeroInt], or returns `null` if the [value]
 * equals zero.
 */
@Deprecated(
    "Use the Int.toNonZeroInt function from the ${Package.root} package " +
            "instead.",
    ReplaceWith(
        "value.toNonZeroInt().getOrNull()",
        "${Package.root}.toNonZeroInt"
    )
)
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun nonZeroIntOrNull(value: Int): NonZeroInt? = value.takeIf { it != 0 }
    ?.let(::NonZeroIntImplementation)

/**
 * Returns the [value] as a [NonZeroInt], or throws an
 * [IllegalArgumentException] if the [value] equals zero.
 */
@Deprecated(
    "Use the Int.toNonZeroInt function from the ${Package.root} package " +
            "instead.",
    ReplaceWith(
        "value.toNonZeroInt().getOrThrow()",
        "${Package.root}.toNonZeroInt"
    )
)
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun nonZeroIntOrThrow(value: Int): NonZeroInt = nonZeroIntOrNull(value)
    ?: throw value shouldBe otherThanZero

/**
 * Returns the [value] as a [NonZeroInt], or throws an
 * [NonZeroInt.ConstructionError] if the [value] equals zero.
 */
@Deprecated(
    "Use the Int.toNonZeroInt function from the ${Package.root} package " +
            "instead.",
    ReplaceWith(
        "value.toNonZeroInt().getOrThrow()",
        "${Package.root}.toNonZeroInt"
    )
)
@SinceKotools(Types, "1.1")
@Suppress("DEPRECATION")
@Throws(NonZeroInt.ConstructionError::class)
public fun NonZeroInt(value: Int): NonZeroInt = nonZeroIntOrNull(value)
    ?: throw NonZeroInt.ConstructionError

/**
 * Returns the [value] as a [NonZeroInt], or returns `null` if the [value]
 * equals zero.
 */
@Deprecated(
    "Use the Int.toNonZeroInt function from the ${Package.root} package " +
            "instead.",
    ReplaceWith(
        "value.toNonZeroInt().getOrNull()",
        "${Package.root}.toNonZeroInt"
    )
)
@SinceKotools(Types, "3.0")
@Suppress("FunctionName")
public fun NonZeroIntOrNull(value: Int): NonZeroInt? = nonZeroIntOrNull(value)

/**
 * Returns this value as a [NonZeroInt], or throws an
 * [NonZeroInt.ConstructionError] if this value equals zero.
 */
@Deprecated(
    "Use the Int.toNonZeroInt function from the ${Package.root} package " +
            "instead.",
    ReplaceWith(
        "this.toNonZeroInt().getOrThrow()",
        "${Package.root}.toNonZeroInt"
    )
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
    "Use the Int.toNonZeroInt function from the ${Package.root} package " +
            "instead.",
    ReplaceWith(
        "this.toNonZeroInt().getOrNull()",
        "${Package.root}.toNonZeroInt"
    )
)
@SinceKotools(Types, "1.1")
public fun Int.toNonZeroIntOrNull(): NonZeroInt? = nonZeroIntOrNull(this)

/**
 * Returns this value as a [NonZeroInt], or throws a [IllegalArgumentException]
 * if this value equals zero.
 */
@Deprecated(
    "Use the Int.toNonZeroInt function from the ${Package.root} package " +
            "instead.",
    ReplaceWith(
        "value.toNonZeroInt().getOrThrow()",
        "${Package.root}.toNonZeroInt"
    )
)
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun Int.toNonZeroIntOrThrow(): NonZeroInt = nonZeroIntOrThrow(this)

/** Returns a random [NonZeroInt]. */
@Deprecated(
    "Use the NonZeroInt.Companion.random function from the ${Package.root} " +
            "package instead.",
    ReplaceWith("NonZeroInt.random()", "${Package.root}.NonZeroInt")
)
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun randomNonZeroInt(): NonZeroInt = NonZeroInt.ranges.random()
    .random()
    .toNonZeroIntOrThrow()

// ---------- Binary operations ----------

/**
 * Divides this value by the [other] value, truncating the result to an integer
 * that is closer to zero.
 */
@Deprecated(
    "Use the Int.div(NonZeroInt) function from the ${Package.root} package " +
            "instead.",
    ReplaceWith(
        "this / other.value.toNonZeroInt().getOrThrow()",
        "${Package.root}.div",
        "${Package.root}.toNonZeroInt"
    )
)
@SinceKotools(Types, "1.1")
public operator fun Int.div(other: NonZeroInt): Int = div(other.value)

/** Representation of integers other than zero. */
@Deprecated(
    "Use the ${Package.root}.NonZeroInt type instead.",
    ReplaceWith("NonZeroInt", "${Package.root}.NonZeroInt")
)
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
        -1 -> nonZeroIntOrThrow(1)
        max.value -> min
        else -> nonZeroIntOrThrow(value + 1)
    }

    /**
     * Returns this [value] decremented by one.
     * If this [value] equals `1`, it returns `-1` instead.
     * If this [value] is the [minimum][NonZeroInt.min], it returns the
     * [maximum][NonZeroInt.max] value instead.
     */
    override fun dec(): NonZeroInt = when (value) {
        1 -> nonZeroIntOrThrow(-1)
        min.value -> max
        else -> nonZeroIntOrThrow(value - 1)
    }

    override fun unaryMinus(): NonZeroInt = nonZeroIntOrThrow(-value)

    // ---------- Binary operations ----------

    /** Multiplies this [value] by the [other] value. */
    public operator fun times(other: NonZeroInt): NonZeroInt =
        nonZeroIntOrThrow(value * other.value)

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
        public val min: NonZeroInt by lazy {
            nonZeroIntOrThrow(negativeRange.first)
        }

        /** The maximum value of a [NonZeroInt]. */
        public val max: NonZeroInt by lazy {
            nonZeroIntOrThrow(positiveRange.last)
        }

        /**
         * Returns the [value] as a [NonZeroInt], or returns `null` if the
         * [value] equals 0.
         */
        @Deprecated(
            "Use the Int.toNonZeroInt function from the ${Package.root} " +
                    "package instead.",
            ReplaceWith(
                "value.toNonZeroInt().getOrNull()",
                "${Package.root}.toNonZeroInt"
            )
        )
        public infix fun orNull(value: Int): NonZeroInt? =
            nonZeroIntOrNull(value)

        /** Returns a random [NonZeroInt]. */
        @Deprecated(
            "Use the NonZeroInt.Companion.random function from the " +
                    "${Package.root} package instead.",
            ReplaceWith("NonZeroInt.random()", "${Package.root}.NonZeroInt")
        )
        @SinceKotools(Types, "3.0")
        public fun random(): NonZeroInt = ranges.random()
            .random()
            .toNonZeroIntOrThrow()
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
    IntHolder.Serializer<NonZeroInt>(::nonZeroIntOrThrow)

@JvmInline
private value class NonZeroIntImplementation(override val value: Int) :
    NonZeroInt {
    override fun toString(): String = value.toString()
}
