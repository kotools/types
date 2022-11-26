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
 * Returns this value as a [NegativeInt], or an [IllegalArgumentException] if
 * this value is strictly positive.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public val Int.negative: Result<NegativeInt>
    get() = takeIf { it <= 0 }
        ?.toSuccessfulResult(::NegativeIntImplementation)
        ?: failureOf { this shouldBe aNegativeNumber }

/**
 * Returns the [value] as a [NegativeInt], or returns `null` if the [value] is
 * strictly positive.
 */
@Deprecated(
    "Use the Int.negative property instead.",
    ReplaceWith("value.negative.getOrNull()", "${Package.number}.negative"),
    DeprecationLevel.ERROR
)
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun negativeIntOrNull(value: Int): NegativeInt? = value
    .takeIf { it <= 0 }
    ?.let(::NegativeIntImplementation)

/**
 * Returns the [value] as a [NegativeInt], or throws an
 * [IllegalArgumentException] if the [value] is strictly positive.
 */
@Deprecated(
    "Use the Int.negative property instead.",
    ReplaceWith("value.negative.getOrThrow()", "${Package.number}.negative"),
    DeprecationLevel.ERROR
)
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun negativeIntOrThrow(value: Int): NegativeInt =
    value.negative.getOrThrow()

/**
 * Returns the [value] as a [NegativeInt], or throws an
 * [NegativeInt.ConstructionError] if the [value] is strictly positive.
 */
@Deprecated(
    "Use the Int.negative property instead.",
    ReplaceWith("value.negative.getOrThrow()", "${Package.number}.negative")
)
@SinceKotools(Types, "1.1")
@Suppress("DEPRECATION")
@Throws(NegativeInt.ConstructionError::class)
public fun NegativeInt(value: Int): NegativeInt = value.negative.getOrNull()
    ?: throw NegativeInt.ConstructionError(value)

/**
 * Returns the [value] as a [NegativeInt], or returns `null` if the [value] is
 * strictly positive.
 */
@Deprecated(
    "Use the Int.negative property instead.",
    ReplaceWith("value.negative.getOrNull()", "${Package.number}.negative")
)
@SinceKotools(Types, "3.0")
@Suppress("FunctionName")
public fun NegativeIntOrNull(value: Int): NegativeInt? =
    value.negative.getOrNull()

/**
 * Returns this value as a [NegativeInt], or throws an
 * [NegativeInt.ConstructionError] if this value is strictly positive.
 */
@Deprecated(
    "Use the Int.negative property instead.",
    ReplaceWith("this.negative.getOrThrow()", "${Package.number}.negative")
)
@SinceKotools(Types, "1.1")
@Suppress("DEPRECATION")
@Throws(NegativeInt.ConstructionError::class)
public fun Int.toNegativeInt(): NegativeInt = NegativeInt(this)

/**
 * Returns this value as a [NegativeInt], or returns `null` if this value is
 * strictly positive.
 */
@Deprecated(
    "Use the Int.negative property instead.",
    ReplaceWith("this.negative.getOrNull()", "${Package.number}.negative")
)
@SinceKotools(Types, "1.1")
public fun Int.toNegativeIntOrNull(): NegativeInt? = this.negative.getOrNull()

/**
 * Returns this value as a [NegativeInt], or throws an
 * [IllegalArgumentException] if this value is strictly positive.
 */
@Deprecated(
    "Use the Int.negative property instead.",
    ReplaceWith("this.negative.getOrThrow()", "${Package.number}.negative"),
    DeprecationLevel.ERROR
)
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun Int.toNegativeIntOrThrow(): NegativeInt = negative.getOrThrow()

/** Returns a random [NegativeInt]. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun randomNegativeInt(): NegativeInt = NegativeInt.range.random()
    .negative
    .getOrThrow()

/** Representation of negative integers, including zero. */
@Serializable(NegativeIntSerializer::class)
@SinceKotools(Types, "1.1")
public sealed interface NegativeInt : IntHolder {
    // ---------- Unary operations ----------

    override fun inc(): NegativeInt = if (value == max.value) min
    else (value + 1).negative.getOrThrow()

    override fun dec(): NegativeInt = if (value == min.value) max
    else (value - 1).negative.getOrThrow()

    override fun unaryMinus(): PositiveInt = (-value).positive.getOrThrow()

    // ---------- Binary operations ----------

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyPositiveInt): NegativeInt =
        (value / other.value).negative.getOrThrow()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyNegativeInt): PositiveInt =
        (value / other.value).positive.getOrThrow()

    /** Contains declarations for holding or building a [NegativeInt]. */
    public companion object {
        internal val range: IntRange by lazy { Int.MIN_VALUE..0 }

        /** The minimum value of a [NegativeInt]. */
        public val min: NegativeInt by lazy(range.first.negative::getOrThrow)

        /** The maximum value of a [NegativeInt]. */
        public val max: NegativeInt by lazy(range.last.negative::getOrThrow)

        /**
         * Returns the [value] as a [NegativeInt], or returns `null` if the
         * [value] equals 0.
         */
        @Deprecated(
            "Use the NegativeIntOrNull function instead.",
            ReplaceWith(
                "NegativeIntOrNull(value)",
                "${Package.number}.NegativeIntOrNull"
            )
        )
        public infix fun orNull(value: Int): NegativeInt? =
            value.negative.getOrNull()

        /** Returns a random [NegativeInt]. */
        @Deprecated(
            "Use the randomNegativeInt function instead.",
            ReplaceWith(
                "randomNegativeInt()",
                "${Package.number}.randomNegativeInt"
            )
        )
        @SinceKotools(Types, "3.0")
        public fun random(): NegativeInt = range.random()
            .negative
            .getOrThrow()
    }

    /** Error thrown when creating a [NegativeInt] fails. */
    @Deprecated(
        "Use the IllegalArgumentException type instead.",
        ReplaceWith("IllegalArgumentException")
    )
    @SinceKotools(Types, "3.0")
    public class ConstructionError(value: Int) : IllegalArgumentException(
        "NegativeInt doesn't accept strictly positive values (tried with " +
                "$value)."
    )
}

internal object NegativeIntSerializer :
    IntHolder.Serializer<NegativeInt>({ it.negative.getOrThrow() })

@JvmInline
private value class NegativeIntImplementation(override val value: Int) :
    NegativeInt {
    override fun toString(): String = value.toString()
}
