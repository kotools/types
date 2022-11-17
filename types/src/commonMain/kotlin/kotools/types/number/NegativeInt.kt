package kotools.types.number

import kotlinx.serialization.Serializable
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.Package
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns the [value] as a [NegativeInt], or returns `null` if the [value] is
 * strictly positive.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun negativeIntOrNull(value: Int): NegativeInt? = value
    .takeIf { it <= 0 }
    ?.let(::NegativeIntImplementation)

/**
 * Returns the [value] as a [NegativeInt], or throws an
 * [IllegalArgumentException] if the [value] is strictly positive.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun negativeIntOrThrow(value: Int): NegativeInt =
    negativeIntOrNull(value) ?: throw value shouldBe aNegativeNumber

/**
 * Returns the [value] as a [NegativeInt], or throws an
 * [NegativeInt.ConstructionError] if the [value] is strictly positive.
 */
@Deprecated(
    "Use the negativeIntOrThrow function instead.",
    ReplaceWith(
        "negativeIntOrThrow(value)",
        "${Package.number}.negativeIntOrThrow"
    )
)
@SinceKotools(Types, "1.1")
@Suppress("DEPRECATION")
@Throws(NegativeInt.ConstructionError::class)
public fun NegativeInt(value: Int): NegativeInt = negativeIntOrNull(value)
    ?: throw NegativeInt.ConstructionError(value)

/**
 * Returns the [value] as a [NegativeInt], or returns `null` if the [value] is
 * strictly positive.
 */
@Deprecated(
    "Use the negativeIntOrNull function instead.",
    ReplaceWith(
        "negativeIntOrNull(value)",
        "${Package.number}.negativeIntOrNull"
    )
)
@SinceKotools(Types, "3.0")
@Suppress("FunctionName")
public fun NegativeIntOrNull(value: Int): NegativeInt? =
    negativeIntOrNull(value)

/**
 * Returns this value as a [NegativeInt], or throws an
 * [NegativeInt.ConstructionError] if this value is strictly positive.
 */
@Deprecated(
    "Use the Int.toNegativeIntOrThrow function instead.",
    ReplaceWith(
        "this.toNegativeIntOrThrow()",
        "${Package.number}.toNegativeIntOrThrow"
    )
)
@SinceKotools(Types, "1.1")
@Suppress("DEPRECATION")
@Throws(NegativeInt.ConstructionError::class)
public fun Int.toNegativeInt(): NegativeInt = NegativeInt(this)

/**
 * Returns this value as a [NegativeInt], or returns `null` if this value is
 * strictly positive.
 */
@SinceKotools(Types, "1.1")
public fun Int.toNegativeIntOrNull(): NegativeInt? = negativeIntOrNull(this)

/**
 * Returns this value as a [NegativeInt], or throws an
 * [IllegalArgumentException] if this value is strictly positive.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun Int.toNegativeIntOrThrow(): NegativeInt = negativeIntOrThrow(this)

/** Returns a random [NegativeInt]. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun randomNegativeInt(): NegativeInt = NegativeInt.range.random()
    .toNegativeIntOrThrow()

/** Representation of negative integers, including zero. */
@Serializable(NegativeIntSerializer::class)
@SinceKotools(Types, "1.1")
public sealed interface NegativeInt : IntHolder {
    // ---------- Unary operations ----------

    override fun inc(): NegativeInt = if (value == max.value) min
    else negativeIntOrThrow(value + 1)

    override fun dec(): NegativeInt = if (value == min.value) max
    else negativeIntOrThrow(value - 1)

    override fun unaryMinus(): PositiveInt = positiveIntOrThrow(-value)

    // ---------- Binary operations ----------

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyPositiveInt): NegativeInt =
        negativeIntOrThrow(value / other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyNegativeInt): PositiveInt =
        positiveIntOrThrow(value / other.value)

    /** Contains declarations for holding or building a [NegativeInt]. */
    public companion object {
        internal val range: IntRange by lazy { Int.MIN_VALUE..0 }

        /** The minimum value of a [NegativeInt]. */
        public val min: NegativeInt by lazy { negativeIntOrThrow(range.first) }

        /** The maximum value of a [NegativeInt]. */
        public val max: NegativeInt by lazy { negativeIntOrThrow(range.last) }

        /**
         * Returns the [value] as a [NegativeInt], or returns `null` if the
         * [value] equals 0.
         */
        @Deprecated(
            "Use the NegativeIntOrNull function instead.",
            ReplaceWith(
                "NegativeIntOrNull(value)",
                "${Package.number}.NegativeIntOrNull"
            ),
            DeprecationLevel.ERROR
        )
        public infix fun orNull(value: Int): NegativeInt? =
            negativeIntOrNull(value)

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
            .toNegativeIntOrThrow()
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
    IntHolder.Serializer<NegativeInt>(::negativeIntOrThrow)

@JvmInline
private value class NegativeIntImplementation(override val value: Int) :
    NegativeInt {
    override fun toString(): String = value.toString()
}
