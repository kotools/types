package kotools.types.number

import kotlinx.serialization.Serializable
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.Package
import kotlin.jvm.JvmInline

// ---------- Builders ----------

/**
 * Returns the [value] as a [StrictlyPositiveInt], or returns `null` if the
 * [value] is negative.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun strictlyPositiveIntOrNull(value: Int): StrictlyPositiveInt? = value
    .takeIf { it > 0 }
    ?.let(::StrictlyPositiveIntImplementation)

/**
 * Returns the [value] as a [StrictlyPositiveInt], or throws an
 * [IllegalArgumentException] if the [value] is negative.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun strictlyPositiveIntOrThrow(value: Int): StrictlyPositiveInt =
    strictlyPositiveIntOrNull(value)
        ?: throw value shouldBe aStrictlyPositiveNumber

/**
 * Returns the [value] as a [StrictlyPositiveInt], or throws an
 * [StrictlyPositiveInt.ConstructionError] if the [value] is negative.
 */
@Deprecated(
    "Use the strictlyPositiveIntOrThrow function instead.",
    ReplaceWith(
        "strictlyPositiveIntOrThrow(value)",
        "${Package.number}.strictlyPositiveIntOrThrow"
    )
)
@SinceKotools(Types, "1.1")
@Suppress("DEPRECATION")
@Throws(StrictlyPositiveInt.ConstructionError::class)
public fun StrictlyPositiveInt(value: Int): StrictlyPositiveInt =
    strictlyPositiveIntOrNull(value)
        ?: throw StrictlyPositiveInt.ConstructionError(value)

/**
 * Returns the [value] as a [StrictlyPositiveInt], or returns `null` if the
 * [value] is negative.
 */
@Deprecated(
    "Use the strictlyPositiveIntOrNull function instead.",
    ReplaceWith(
        "strictlyPositiveIntOrNull(value)",
        "${Package.number}.strictlyPositiveIntOrNull"
    )
)
@SinceKotools(Types, "3.0")
@Suppress("FunctionName")
public fun StrictlyPositiveIntOrNull(value: Int): StrictlyPositiveInt? =
    strictlyPositiveIntOrNull(value)

/**
 * Returns this value as a [StrictlyPositiveInt], or throws an
 * [StrictlyPositiveInt.ConstructionError] if this value is negative.
 */
@Deprecated(
    "Use the Int.toStrictlyPositiveIntOrThrow function instead.",
    ReplaceWith(
        "this.toStrictlyPositiveIntOrThrow()",
        "${Package.number}.toStrictlyPositiveIntOrThrow"
    )
)
@SinceKotools(Types, "1.1")
@Suppress("DEPRECATION")
@Throws(StrictlyPositiveInt.ConstructionError::class)
public fun Int.toStrictlyPositiveInt(): StrictlyPositiveInt =
    StrictlyPositiveInt(this)

/**
 * Returns this value as a [StrictlyPositiveInt], or returns `null` if this
 * value is negative.
 */
@SinceKotools(Types, "1.1")
public fun Int.toStrictlyPositiveIntOrNull(): StrictlyPositiveInt? =
    strictlyPositiveIntOrNull(this)

/**
 * Returns this value as a [StrictlyPositiveInt], or throws an
 * [IllegalArgumentException] if this value is negative.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun Int.toStrictlyPositiveIntOrThrow(): StrictlyPositiveInt =
    strictlyPositiveIntOrThrow(this)

/** Returns a random [StrictlyPositiveInt]. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun randomStrictlyPositiveInt(): StrictlyPositiveInt =
    StrictlyPositiveInt.range.random()
        .toStrictlyPositiveIntOrThrow()

/** Representation of strictly positive integers, excluding zero. */
@Serializable(StrictlyPositiveIntSerializer::class)
@SinceKotools(Types, "1.1")
public sealed interface StrictlyPositiveInt : NonZeroInt,
    PositiveInt {
    // ---------- Unary operations ----------

    override fun inc(): StrictlyPositiveInt = if (value == max.value) min
    else strictlyPositiveIntOrThrow(value + 1)

    override fun dec(): StrictlyPositiveInt = if (value == min.value) max
    else strictlyPositiveIntOrThrow(value - 1)

    override fun unaryMinus(): StrictlyNegativeInt =
        strictlyNegativeIntOrThrow(-value)

    /**
     * Contains declarations for holding or building a [StrictlyPositiveInt].
     */
    public companion object {
        internal val range: IntRange by lazy { 1..Int.MAX_VALUE }

        /** The minimum value of a [StrictlyPositiveInt]. */
        public val min: StrictlyPositiveInt by lazy {
            strictlyPositiveIntOrThrow(range.first)
        }

        /** The maximum value of a [StrictlyPositiveInt]. */
        public val max: StrictlyPositiveInt by lazy {
            strictlyPositiveIntOrThrow(range.last)
        }

        /**
         * Returns the [value] as a [StrictlyPositiveInt], or returns `null` if
         * the [value] equals 0.
         */
        @Deprecated(
            "Use the StrictlyPositiveIntOrNull function instead.",
            ReplaceWith(
                "StrictlyPositiveIntOrNull(value)",
                "${Package.number}.StrictlyPositiveIntOrNull"
            ),
            DeprecationLevel.ERROR
        )
        public infix fun orNull(value: Int): StrictlyPositiveInt? =
            value.toStrictlyPositiveIntOrNull()

        /** Returns a random [StrictlyPositiveInt]. */
        @Deprecated(
            "Use the randomStrictlyPositiveInt function instead.",
            ReplaceWith(
                "randomStrictlyPositiveInt()",
                "${Package.number}.randomStrictlyPositiveInt"
            )
        )
        @SinceKotools(Types, "3.0")
        public fun random(): StrictlyPositiveInt = range.random()
            .toStrictlyPositiveIntOrThrow()
    }

    /** Error thrown when creating a [StrictlyPositiveInt] fails. */
    @Deprecated(
        "Use the IllegalArgumentException type instead.",
        ReplaceWith("IllegalArgumentException")
    )
    @SinceKotools(Types, "3.0")
    public class ConstructionError(value: Int) : IllegalArgumentException(
        "StrictlyPositiveInt doesn't accept negative values (tried with " +
                "$value)."
    )
}

internal object StrictlyPositiveIntSerializer :
    IntHolder.Serializer<StrictlyPositiveInt>(::strictlyPositiveIntOrThrow)

@JvmInline
private value class StrictlyPositiveIntImplementation(override val value: Int) :
    StrictlyPositiveInt {
    override fun toString(): String = value.toString()
}
