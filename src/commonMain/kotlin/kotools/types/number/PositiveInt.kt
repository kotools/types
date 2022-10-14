package kotools.types.number

import kotlinx.serialization.Serializable
import kotools.types.SinceKotoolsTypes
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns the [value] as a [PositiveInt], or throws an
 * [PositiveInt.ConstructionError] if the [value] is strictly negative.
 */
@SinceKotoolsTypes("1.1")
@Throws(PositiveInt.ConstructionError::class)
public fun PositiveInt(value: Int): PositiveInt = value.toPositiveInt()

/**
 * Returns the [value] as a [PositiveInt], or returns `null` if the [value] is
 * strictly negative.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun PositiveIntOrNull(value: Int): PositiveInt? =
    value.toPositiveIntOrNull()

/**
 * Returns this value as a [PositiveInt], or throws an
 * [PositiveInt.ConstructionError] if this value is strictly negative.
 */
@SinceKotoolsTypes("1.1")
@Throws(PositiveInt.ConstructionError::class)
public fun Int.toPositiveInt(): PositiveInt = toPositiveIntOrNull()
    ?: throw PositiveInt.ConstructionError(this)

/**
 * Returns this value as a [PositiveInt], or returns `null` if this value is
 * strictly negative.
 */
@SinceKotoolsTypes("1.1")
public fun Int.toPositiveIntOrNull(): PositiveInt? = takeIf { it >= 0 }
    ?.let(::PositiveIntImplementation)

/** Representation of positive integers, including zero. */
@Serializable(PositiveIntSerializer::class)
@SinceKotoolsTypes("1.1")
public sealed interface PositiveInt : IntHolder {
    // ---------- Unary operations ----------

    override fun unaryMinus(): NegativeInt = NegativeInt(-value)

    // ---------- Binary operations ----------

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyPositiveInt): PositiveInt =
        PositiveInt(value / other.value)

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to zero.
     */
    public operator fun div(other: StrictlyNegativeInt): NegativeInt =
        NegativeInt(value / other.value)

    /** Contains declarations for holding or building a [PositiveInt]. */
    public companion object {
        /**
         * Returns the [value] as a [PositiveInt], or returns `null` if the
         * [value] equals 0.
         */
        @Deprecated(
            "Use the PositiveIntOrNull function instead.",
            ReplaceWith(
                "PositiveIntOrNull(value)",
                "kotools.types.number.PositiveIntOrNull"
            )
        )
        public infix fun orNull(value: Int): PositiveInt? =
            value.toPositiveIntOrNull()
    }

    /** Error thrown when creating a [PositiveInt] fails. */
    @SinceKotoolsTypes("3.0")
    public class ConstructionError(value: Int) : IllegalArgumentException(
        "PositiveInt doesn't accept strictly negative values (tried with " +
                "$value)."
    )
}

internal object PositiveIntSerializer :
    IntHolder.Serializer<PositiveInt>(::PositiveInt)

@JvmInline
private value class PositiveIntImplementation(override val value: Int) :
    PositiveInt {
    override fun toString(): String = value.toString()
}
