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
 * Returns this value as a [PositiveInt], or an [IllegalArgumentException] if
 * this value is strictly negative.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public val Int.positive: Result<PositiveInt>
    get() = takeIf { it >= 0 }
        ?.toSuccessfulResult(::PositiveIntImplementation)
        ?: failureOf { this shouldBe aPositiveNumber }

/**
 * Returns the [value] as a [PositiveInt], or returns `null` if the [value] is
 * strictly negative.
 */
@Deprecated(
    "Use the Int.positive property instead.",
    ReplaceWith("value.positive.getOrNull()", "${Package.number}.positive"),
    DeprecationLevel.ERROR
)
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun positiveIntOrNull(value: Int): PositiveInt? = value
    .takeIf { it >= 0 }
    ?.let(::PositiveIntImplementation)

/**
 * Returns the [value] as a [PositiveInt], or throws an
 * [IllegalArgumentException] if the [value] is strictly negative.
 */
@Deprecated(
    "Use the Int.positive property instead.",
    ReplaceWith("value.positive.getOrThrow()", "${Package.number}.positive"),
    DeprecationLevel.ERROR
)
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun positiveIntOrThrow(value: Int): PositiveInt =
    value.positive.getOrNull() ?: throw value shouldBe aPositiveNumber

/**
 * Returns the [value] as a [PositiveInt], or throws an
 * [PositiveInt.ConstructionError] if the [value] is strictly negative.
 */
@Deprecated(
    "Use the Int.positive property instead.",
    ReplaceWith("value.positive.getOrThrow()", "${Package.number}.positive")
)
@SinceKotools(Types, "1.1")
@Suppress("DEPRECATION")
@Throws(PositiveInt.ConstructionError::class)
public fun PositiveInt(value: Int): PositiveInt = value.positive.getOrNull()
    ?: throw PositiveInt.ConstructionError(value)

/**
 * Returns the [value] as a [PositiveInt], or returns `null` if the [value] is
 * strictly negative.
 */
@Deprecated(
    "Use the Int.positive property instead.",
    ReplaceWith("value.positive.getOrNull()", "${Package.number}.positive")
)
@SinceKotools(Types, "3.0")
@Suppress("FunctionName")
public fun PositiveIntOrNull(value: Int): PositiveInt? =
    value.positive.getOrNull()

/**
 * Returns this value as a [PositiveInt], or throws an
 * [PositiveInt.ConstructionError] if this value is strictly negative.
 */
@Deprecated(
    "Use the Int.positive property instead.",
    ReplaceWith("this.positive.getOrThrow()", "${Package.number}.positive")
)
@SinceKotools(Types, "1.1")
@Suppress("DEPRECATION")
@Throws(PositiveInt.ConstructionError::class)
public fun Int.toPositiveInt(): PositiveInt = PositiveInt(this)

/**
 * Returns this value as a [PositiveInt], or returns `null` if this value is
 * strictly negative.
 */
@Deprecated(
    "Use the Int.positive property instead.",
    ReplaceWith("this.positive.getOrNull()", "${Package.number}.positive")
)
@SinceKotools(Types, "1.1")
public fun Int.toPositiveIntOrNull(): PositiveInt? = positive.getOrNull()

/**
 * Returns this value as a [PositiveInt], or throws an
 * [IllegalArgumentException] if this value is strictly negative.
 */
@Deprecated(
    "Use the Int.positive property instead.",
    ReplaceWith("this.positive.getOrThrow()", "${Package.number}.positive"),
    DeprecationLevel.ERROR
)
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun Int.toPositiveIntOrThrow(): PositiveInt = positive.getOrThrow()

/** Returns a random [PositiveInt]. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun randomPositiveInt(): PositiveInt = PositiveInt.range.random()
    .positive
    .getOrThrow()

/** Representation of positive integers, including zero. */
@Serializable(PositiveIntSerializer::class)
@SinceKotools(Types, "1.1")
public sealed interface PositiveInt : IntHolder {
    // ---------- Unary operations ----------

    override fun inc(): PositiveInt = if (value == max.value) min
    else (value + 1).positive.getOrThrow()

    override fun dec(): PositiveInt = if (value == min.value) max
    else (value - 1).positive.getOrThrow()

    override fun unaryMinus(): NegativeInt = negativeIntOrThrow(-value)

    // ---------- Binary operations ----------

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyPositiveInt): PositiveInt =
        (value / other.value).positive.getOrThrow()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyNegativeInt): NegativeInt =
        negativeIntOrThrow(value / other.value)

    /** Contains declarations for holding or building a [PositiveInt]. */
    public companion object {
        internal val range: IntRange by lazy { 0..Int.MAX_VALUE }

        /** The minimum value of a [PositiveInt]. */
        public val min: PositiveInt by lazy(range.first.positive::getOrThrow)

        /** The maximum value of a [PositiveInt]. */
        public val max: PositiveInt by lazy(range.last.positive::getOrThrow)

        /**
         * Returns the [value] as a [PositiveInt], or returns `null` if the
         * [value] equals 0.
         */
        @Deprecated(
            "Use the PositiveIntOrNull function instead.",
            ReplaceWith(
                "PositiveIntOrNull(value)",
                "${Package.number}.PositiveIntOrNull"
            )
        )
        public infix fun orNull(value: Int): PositiveInt? =
            value.positive.getOrNull()

        /** Returns a random [PositiveInt]. */
        @Deprecated(
            "Use the randomPositiveInt function instead.",
            ReplaceWith(
                "randomPositiveInt()",
                "${Package.number}.randomPositiveInt"
            )
        )
        @SinceKotools(Types, "3.0")
        public fun random(): PositiveInt = range.random()
            .positive
            .getOrThrow()
    }

    /** Error thrown when creating a [PositiveInt] fails. */
    @Deprecated(
        "Use the IllegalArgumentException type instead.",
        ReplaceWith("IllegalArgumentException")
    )
    @SinceKotools(Types, "3.0")
    public class ConstructionError(value: Int) : IllegalArgumentException(
        "Given value should be positive (tried with $value)."
    )
}

internal object PositiveIntSerializer :
    IntHolder.Serializer<PositiveInt>({ it.positive.getOrThrow() })

@JvmInline
private value class PositiveIntImplementation(override val value: Int) :
    PositiveInt {
    override fun toString(): String = value.toString()
}
