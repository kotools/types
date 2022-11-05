package kotools.types.number

import arrow.core.Either
import arrow.core.getOrHandle
import arrow.core.left
import arrow.core.right
import kotlinx.serialization.Serializable
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/*
For API compatibility purpose, this function will be available publicly only
when the PositiveInt(Int) function is going to be removed (maybe in v3.4).
Also, because of the signature of this function, make sure to export the API of
Arrow Core with this library within the Gradle build script.
 */
private fun positiveInt(
    value: Int
): Either<PositiveNumberDslError, PositiveInt> = value.takeIf { it >= 0 }
    ?.let(::PositiveIntImplementation)
    ?.right()
    ?: PositiveNumberDslErrorImplementation(value).left()

/**
 * Returns the [value] as a [PositiveInt], or returns `null` if the [value] is
 * strictly negative.
 */
@SinceKotoolsTypes("3.2")
public fun positiveIntOrNull(value: Int): PositiveInt? =
    positiveInt(value).orNull()

/**
 * Returns the [value] as a [PositiveInt], or throws an [PositiveNumberDslError]
 * if the [value] is strictly negative.
 */
@SinceKotoolsTypes("3.2")
@Throws(PositiveNumberDslError::class)
public fun positiveIntOrThrow(value: Int): PositiveInt =
    positiveInt(value).getOrHandle { throw it }

/**
 * Returns the [value] as a [PositiveInt], or throws an
 * [PositiveInt.ConstructionError] if the [value] is strictly negative.
 */
@Deprecated(
    "Use the positiveIntOrThrow function instead. Will be an error in v3.3.",
    ReplaceWith(
        "positiveIntOrThrow(value)",
        "${Package.number}.positiveIntOrThrow"
    )
)
@SinceKotoolsTypes("1.1")
@Suppress("DEPRECATION")
@Throws(PositiveInt.ConstructionError::class)
public fun PositiveInt(value: Int): PositiveInt = positive int value

/**
 * Returns the [value] as a [PositiveInt], or returns `null` if the [value] is
 * strictly negative.
 */
@Deprecated(
    "Use the positiveIntOrNull function instead. Will be an error in v3.3.",
    ReplaceWith(
        "positiveIntOrNull(value)",
        "${Package.number}.positiveIntOrNull"
    )
)
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun PositiveIntOrNull(value: Int): PositiveInt? =
    positive intOrNull value

/**
 * Returns this value as a [PositiveInt], or throws an
 * [PositiveInt.ConstructionError] if this value is strictly negative.
 */
@SinceKotoolsTypes("1.1")
@Suppress("DEPRECATION")
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

/** Returns a random [PositiveInt]. */
@SinceKotoolsTypes("3.2")
public fun randomPositiveInt(): PositiveInt = PositiveInt.range.random()
    .toPositiveInt()

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
            """
                Use the randomPositiveInt function instead.
                Will be an error in v3.3.
            """,
            ReplaceWith(
                "randomPositiveInt()",
                "${Package.number}.randomPositiveInt"
            )
        )
        @SinceKotoolsTypes("3.0")
        public fun random(): PositiveInt = range.random()
            .toPositiveInt()
    }

    /** Error thrown when creating a [PositiveInt] fails. */
    @Deprecated(
        """
            Use the PositiveNumberDslError type instead.
            Will be an error in v3.3.
        """,
        ReplaceWith(
            "PositiveNumberDslError",
            "${Package.number}.PositiveNumberDslError"
        )
    )
    @SinceKotoolsTypes("3.0")
    public class ConstructionError(value: Int) : PositiveNumberDslError(value)
}

internal object PositiveIntSerializer :
    IntHolder.Serializer<PositiveInt>(positive::int)

@JvmInline
private value class PositiveIntImplementation(override val value: Int) :
    PositiveInt {
    override fun toString(): String = value.toString()
}
