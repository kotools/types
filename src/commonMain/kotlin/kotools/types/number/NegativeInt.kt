package kotools.types.number

import kotlinx.serialization.Serializable
import kotools.types.SinceKotoolsTypes
import kotools.types.StabilityLevel
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns the [value] as a [NegativeInt], or throws an
 * [NegativeInt.ConstructionError] if the [value] is strictly positive.
 */
@SinceKotoolsTypes("1.1")
@Throws(NegativeInt.ConstructionError::class)
public fun NegativeInt(value: Int): NegativeInt = value.toNegativeInt()

/**
 * Returns the [value] as a [NegativeInt], or returns `null` if the [value] is
 * strictly positive.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun NegativeIntOrNull(value: Int): NegativeInt? =
    value.toNegativeIntOrNull()

/**
 * Returns this value as a [NegativeInt], or throws an
 * [NegativeInt.ConstructionError] if this value is strictly positive.
 */
@SinceKotoolsTypes("1.1")
@Throws(NegativeInt.ConstructionError::class)
public fun Int.toNegativeInt(): NegativeInt = toNegativeIntOrNull()
    ?: throw NegativeInt.ConstructionError(this)

/**
 * Returns this value as a [NegativeInt], or returns `null` if this value is
 * strictly positive.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toNegativeIntOrNull(): NegativeInt? = takeIf { it <= 0 }
    ?.let(::NegativeIntImplementation)

/** Representation of negative integers, including zero. */
@Serializable(NegativeIntSerializer::class)
@SinceKotoolsTypes("1.1")
public sealed interface NegativeInt : IntHolder {
    // ---------- Unary operations ----------

    override fun inc(): NegativeInt = if (value == max.value) min
    else NegativeInt(value + 1)

    override fun dec(): NegativeInt = if (value == min.value) max
    else NegativeInt(value - 1)

    override fun unaryMinus(): PositiveInt = PositiveInt(-value)

    // ---------- Binary operations ----------

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyPositiveInt): NegativeInt =
        NegativeInt(value / other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyNegativeInt): PositiveInt =
        PositiveInt(value / other.value)

    /** Contains declarations for holding or building a [NegativeInt]. */
    public companion object {
        private val range: IntRange by lazy { Int.MIN_VALUE..0 }

        /** The minimum value of a [NegativeInt]. */
        public val min: NegativeInt by lazy { NegativeInt(range.first) }

        /** The maximum value of a [NegativeInt]. */
        public val max: NegativeInt by lazy { NegativeInt(range.last) }

        /**
         * Returns the [value] as a [NegativeInt], or returns `null` if the
         * [value] equals 0.
         */
        @Deprecated(
            "Use the NegativeIntOrNull function instead.",
            ReplaceWith(
                "NegativeIntOrNull(value)",
                "kotools.types.number.NegativeIntOrNull"
            ),
            DeprecationLevel.ERROR
        )
        public infix fun orNull(value: Int): NegativeInt? =
            value.toNegativeIntOrNull()

        /** Returns a random [NegativeInt]. */
        @SinceKotoolsTypes("3.0", StabilityLevel.Beta)
        public fun random(): NegativeInt = range.random()
            .toNegativeInt()
    }

    /** Error thrown when creating a [NegativeInt] fails. */
    @SinceKotoolsTypes("3.0")
    public class ConstructionError(value: Int) : IllegalArgumentException(
        "NegativeInt doesn't accept strictly positive values (tried with " +
                "$value)."
    )
}

internal object NegativeIntSerializer :
    IntHolder.Serializer<NegativeInt>(::NegativeInt)

@JvmInline
private value class NegativeIntImplementation(override val value: Int) :
    NegativeInt {
    override fun toString(): String = value.toString()
}
