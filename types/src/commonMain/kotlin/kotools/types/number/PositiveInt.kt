package kotools.types.number

import kotlinx.serialization.Serializable
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.StabilityLevel
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns the [value] as a [PositiveInt], or returns `null` if the [value] is
 * strictly negative.
 */
@SinceKotoolsTypes("3.2", StabilityLevel.Alpha)
public fun positiveIntOrNull(value: Int): PositiveInt? = value
    .takeIf { it >= 0 }
    ?.let(::PositiveIntImplementation)

/**
 * Returns the [value] as a [PositiveInt], or throws an
 * [IllegalArgumentException] if the [value] is strictly negative.
 */
@SinceKotoolsTypes("3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun positiveIntOrThrow(value: Int): PositiveInt =
    positiveIntOrNull(value) ?: throw value shouldBe aPositiveNumber

/**
 * Returns the [value] as a [PositiveInt], or throws an
 * [PositiveInt.ConstructionError] if the [value] is strictly negative.
 */
@Deprecated(
    "Use the positiveIntOrThrow function instead.",
    ReplaceWith(
        "positiveIntOrThrow(value)",
        "${Package.number}.positiveIntOrThrow"
    )
)
@SinceKotoolsTypes("1.1")
@Suppress("DEPRECATION")
@Throws(PositiveInt.ConstructionError::class)
public fun PositiveInt(value: Int): PositiveInt = positiveIntOrNull(value)
    ?: throw PositiveInt.ConstructionError(value)

/**
 * Returns the [value] as a [PositiveInt], or returns `null` if the [value] is
 * strictly negative.
 */
@Deprecated(
    "Use the positiveIntOrNull function instead.",
    ReplaceWith(
        "positiveIntOrNull(value)",
        "${Package.number}.positiveIntOrNull"
    )
)
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun PositiveIntOrNull(value: Int): PositiveInt? =
    positiveIntOrNull(value)

/**
 * Returns this value as a [PositiveInt], or throws an
 * [PositiveInt.ConstructionError] if this value is strictly negative.
 */
@Deprecated(
    "Use the Int.toPositiveIntOrThrow function instead.",
    ReplaceWith(
        "this.toPositiveIntOrThrow()",
        "${Package.number}.toPositiveIntOrThrow"
    )
)
@SinceKotoolsTypes("1.1")
@Suppress("DEPRECATION")
@Throws(PositiveInt.ConstructionError::class)
public fun Int.toPositiveInt(): PositiveInt = PositiveInt(this)

/**
 * Returns this value as a [PositiveInt], or returns `null` if this value is
 * strictly negative.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toPositiveIntOrNull(): PositiveInt? = positiveIntOrNull(this)

/**
 * Returns this value as a [PositiveInt], or throws an
 * [IllegalArgumentException] if this value is strictly negative.
 */
@SinceKotoolsTypes("3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun Int.toPositiveIntOrThrow(): PositiveInt = positiveIntOrThrow(this)

/** Returns a random [PositiveInt]. */
@SinceKotoolsTypes("3.2", StabilityLevel.Alpha)
public fun randomPositiveInt(): PositiveInt = PositiveInt.range.random()
    .toPositiveIntOrThrow()

/** Representation of positive integers, including zero. */
@Serializable(PositiveIntSerializer::class)
@SinceKotoolsTypes("1.1")
public sealed interface PositiveInt : IntHolder {
    // ---------- Unary operations ----------

    override fun inc(): PositiveInt = if (value == max.value) min
    else positive int value + 1

    override fun dec(): PositiveInt = if (value == min.value) max
    else positive int value - 1

    override fun unaryMinus(): NegativeInt = negative int -value

    // ---------- Binary operations ----------

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyPositiveInt): PositiveInt =
        positive int value / other.value

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyNegativeInt): NegativeInt =
        negative int value / other.value

    /** Contains declarations for holding or building a [PositiveInt]. */
    public companion object {
        internal val range: IntRange by lazy { 0..Int.MAX_VALUE }

        /** The minimum value of a [PositiveInt]. */
        public val min: PositiveInt by lazy { positive int range.first }

        /** The maximum value of a [PositiveInt]. */
        public val max: PositiveInt by lazy { positive int range.last }

        /** Returns a random [PositiveInt]. */
        @Deprecated(
            "Use the randomPositiveInt function instead.",
            ReplaceWith(
                "randomPositiveInt()",
                "${Package.number}.randomPositiveInt"
            )
        )
        @SinceKotoolsTypes("3.0")
        public fun random(): PositiveInt = range.random()
            .toPositiveIntOrThrow()
    }

    /** Error thrown when creating a [PositiveInt] fails. */
    @Deprecated(
        "Use the IllegalArgumentException type instead.",
        ReplaceWith("IllegalArgumentException")
    )
    @SinceKotoolsTypes("3.0")
    public class ConstructionError(value: Int) : IllegalArgumentException(
        "Given value should be positive (tried with $value)."
    )
}

internal object PositiveIntSerializer :
    IntHolder.Serializer<PositiveInt>(positive::int)

@JvmInline
private value class PositiveIntImplementation(override val value: Int) :
    PositiveInt {
    override fun toString(): String = value.toString()
}
