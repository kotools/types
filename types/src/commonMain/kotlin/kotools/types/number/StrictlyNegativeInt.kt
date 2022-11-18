package kotools.types.number

import kotlinx.serialization.Serializable
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.Package
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns the [value] as a [StrictlyNegativeInt], or returns `null` if the
 * [value] is positive.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun strictlyNegativeIntOrNull(value: Int): StrictlyNegativeInt? = value
    .takeIf { it < 0 }
    ?.let(::StrictlyNegativeIntImplementation)

/**
 * Returns the [value] as a [StrictlyNegativeInt], or throws an
 * [IllegalArgumentException] if the [value] is positive.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun strictlyNegativeIntOrThrow(value: Int): StrictlyNegativeInt =
    strictlyNegativeIntOrNull(value)
        ?: throw value shouldBe aStrictlyNegativeNumber

/**
 * Returns the [value] as a [StrictlyNegativeInt], or throws an
 * [StrictlyNegativeInt.ConstructionError] if the [value] is positive.
 */
@Deprecated(
    "Use the strictlyNegativeIntOrThrow function instead.",
    ReplaceWith(
        "strictlyNegativeIntOrThrow(value)",
        "${Package.number}.strictlyNegativeIntOrThrow"
    )
)
@SinceKotools(Types, "1.1")
@Suppress("DEPRECATION")
@Throws(StrictlyNegativeInt.ConstructionError::class)
public fun StrictlyNegativeInt(value: Int): StrictlyNegativeInt =
    strictlyNegativeIntOrNull(value)
        ?: throw StrictlyNegativeInt.ConstructionError(value)

/**
 * Returns the [value] as a [StrictlyNegativeInt], or returns `null` if the
 * [value] is positive.
 */
@Deprecated(
    "Use the strictlyNegativeIntOrNull function instead.",
    ReplaceWith(
        "strictlyNegativeIntOrNull(value)",
        "${Package.number}.strictlyNegativeIntOrNull"
    )
)
@SinceKotools(Types, "3.0")
@Suppress("FunctionName")
public fun StrictlyNegativeIntOrNull(value: Int): StrictlyNegativeInt? =
    strictlyNegativeIntOrNull(value)

/**
 * Returns this value as a [StrictlyNegativeInt], or throws an
 * [StrictlyNegativeInt.ConstructionError] if this value is positive.
 */
@Deprecated(
    "Use the Int.toStrictlyNegativeIntOrThrow function instead.",
    ReplaceWith(
        "this.toStrictlyNegativeIntOrThrow()",
        "${Package.number}.toStrictlyNegativeIntOrThrow"
    )
)
@SinceKotools(Types, "1.1")
@Suppress("DEPRECATION")
@Throws(StrictlyNegativeInt.ConstructionError::class)
public fun Int.toStrictlyNegativeInt(): StrictlyNegativeInt =
    StrictlyNegativeInt(this)

/**
 * Returns this value as a [StrictlyNegativeInt], or returns `null` if this
 * value is positive.
 */
@SinceKotools(Types, "1.1")
public fun Int.toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
    strictlyNegativeIntOrNull(this)

/**
 * Returns this value as a [StrictlyNegativeInt], or throws an
 * [IllegalArgumentException] if this value is positive.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun Int.toStrictlyNegativeIntOrThrow(): StrictlyNegativeInt =
    strictlyNegativeIntOrThrow(this)

/** Returns a random [StrictlyNegativeInt]. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun randomStrictlyNegativeInt(): StrictlyNegativeInt =
    StrictlyNegativeInt.range.random()
        .toStrictlyNegativeIntOrThrow()

/** Representation of strictly negative integers, excluding zero. */
@Serializable(StrictlyNegativeIntSerializer::class)
@SinceKotools(Types, "1.1")
public sealed interface StrictlyNegativeInt : NonZeroInt,
    NegativeInt {
    // ---------- Unary operations ----------

    override fun inc(): StrictlyNegativeInt = if (value == max.value) min
    else strictlyNegativeIntOrThrow(value + 1)

    override fun dec(): StrictlyNegativeInt = if (value == min.value) max
    else strictlyNegativeIntOrThrow(value - 1)

    override fun unaryMinus(): StrictlyPositiveInt =
        strictlyPositiveIntOrThrow(-value)

    /**
     * Contains declarations for holding or building a [StrictlyPositiveInt].
     */
    public companion object {
        internal val range: IntRange by lazy { Int.MIN_VALUE..-1 }

        /** The minimum value of a [StrictlyNegativeInt]. */
        public val min: StrictlyNegativeInt by lazy {
            strictlyNegativeIntOrThrow(range.first)
        }

        /** The maximum value of a [StrictlyNegativeInt]. */
        public val max: StrictlyNegativeInt by lazy {
            strictlyNegativeIntOrThrow(range.last)
        }

        /**
         * Returns the [value] as a [StrictlyPositiveInt], or returns `null` if the
         * [value] equals 0.
         */
        @Deprecated(
            "Use the StrictlyNegativeIntOrNull function instead.",
            ReplaceWith(
                "StrictlyNegativeIntOrNull(value)",
                "${Package.number}.StrictlyNegativeIntOrNull"
            )
        )
        public infix fun orNull(value: Int): StrictlyPositiveInt? =
            value.toStrictlyPositiveIntOrNull()

        /** Returns a random [StrictlyNegativeInt]. */
        @Deprecated(
            "Use the randomStrictlyNegativeInt function instead.",
            ReplaceWith(
                "randomStrictlyNegativeInt()",
                "${Package.number}.randomStrictlyNegativeInt"
            )
        )
        @SinceKotools(Types, "3.0")
        public fun random(): StrictlyNegativeInt = range.random()
            .toStrictlyNegativeIntOrThrow()
    }

    /** Error thrown when creating a [StrictlyNegativeInt] fails. */
    @Deprecated(
        "Use the IllegalArgumentException type instead.",
        ReplaceWith("IllegalArgumentException")
    )
    @SinceKotools(Types, "3.0")
    public class ConstructionError(value: Int) : IllegalArgumentException(
        "StrictlyNegativeInt doesn't accept positive values (tried with " +
                "$value)."
    )
}

internal object StrictlyNegativeIntSerializer :
    IntHolder.Serializer<StrictlyNegativeInt>(::strictlyNegativeIntOrThrow)

@JvmInline
private value class StrictlyNegativeIntImplementation(override val value: Int) :
    StrictlyNegativeInt {
    override fun toString(): String = value.toString()
}
