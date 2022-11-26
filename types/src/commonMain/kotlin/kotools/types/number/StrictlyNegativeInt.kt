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
 * Returns this value as a [StrictlyNegativeInt], or an
 * [IllegalArgumentException] if this value is positive.
 */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public val Int.strictlyNegative: Result<StrictlyNegativeInt>
    get() = takeIf { it < 0 }
        ?.toSuccessfulResult(::StrictlyNegativeIntImplementation)
        ?: failureOf { this shouldBe aStrictlyNegativeNumber }

/**
 * Returns the [value] as a [StrictlyNegativeInt], or returns `null` if the
 * [value] is positive.
 */
@Deprecated(
    "Use the Int.strictlyNegative property instead.",
    ReplaceWith(
        "value.strictlyNegative.getOrNull()",
        "${Package.number}.strictlyNegative"
    ),
    DeprecationLevel.ERROR
)
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun strictlyNegativeIntOrNull(value: Int): StrictlyNegativeInt? = value
    .takeIf { it < 0 }
    ?.let(::StrictlyNegativeIntImplementation)

/**
 * Returns the [value] as a [StrictlyNegativeInt], or throws an
 * [IllegalArgumentException] if the [value] is positive.
 */
@Deprecated(
    "Use the Int.strictlyNegative property instead.",
    ReplaceWith(
        "value.strictlyNegative.getOrThrow()",
        "${Package.number}.strictlyNegative"
    ),
    DeprecationLevel.ERROR
)
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun strictlyNegativeIntOrThrow(value: Int): StrictlyNegativeInt =
    value.strictlyNegative.getOrThrow()

/**
 * Returns the [value] as a [StrictlyNegativeInt], or throws an
 * [StrictlyNegativeInt.ConstructionError] if the [value] is positive.
 */
@Deprecated(
    "Use the Int.strictlyNegative property instead.",
    ReplaceWith(
        "value.strictlyNegative.getOrThrow()",
        "${Package.number}.strictlyNegative"
    )
)
@SinceKotools(Types, "1.1")
@Suppress("DEPRECATION")
@Throws(StrictlyNegativeInt.ConstructionError::class)
public fun StrictlyNegativeInt(value: Int): StrictlyNegativeInt =
    value.strictlyNegative.getOrNull()
        ?: throw StrictlyNegativeInt.ConstructionError(value)

/**
 * Returns the [value] as a [StrictlyNegativeInt], or returns `null` if the
 * [value] is positive.
 */
@Deprecated(
    "Use the Int.strictlyNegative property instead.",
    ReplaceWith(
        "value.strictlyNegative.getOrNull()",
        "${Package.number}.strictlyNegative"
    )
)
@SinceKotools(Types, "3.0")
@Suppress("FunctionName")
public fun StrictlyNegativeIntOrNull(value: Int): StrictlyNegativeInt? =
    value.strictlyNegative.getOrNull()

/**
 * Returns this value as a [StrictlyNegativeInt], or throws an
 * [StrictlyNegativeInt.ConstructionError] if this value is positive.
 */
@Deprecated(
    "Use the Int.strictlyNegative property instead.",
    ReplaceWith(
        "this.strictlyNegative.getOrThrow()",
        "${Package.number}.strictlyNegative"
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
@Deprecated(
    "Use the Int.strictlyNegative property instead.",
    ReplaceWith(
        "this.strictlyNegative.getOrNull()",
        "${Package.number}.strictlyNegative"
    )
)
@SinceKotools(Types, "1.1")
public fun Int.toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
    strictlyNegative.getOrNull()

/**
 * Returns this value as a [StrictlyNegativeInt], or throws an
 * [IllegalArgumentException] if this value is positive.
 */
@Deprecated(
    "Use the Int.strictlyNegative property instead.",
    ReplaceWith(
        "this.strictlyNegative.getOrThrow()",
        "${Package.number}.strictlyNegative"
    ),
    DeprecationLevel.ERROR
)
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
@Throws(IllegalArgumentException::class)
public fun Int.toStrictlyNegativeIntOrThrow(): StrictlyNegativeInt =
    strictlyNegative.getOrThrow()

/** Returns a random [StrictlyNegativeInt]. */
@SinceKotools(Types, "3.2", StabilityLevel.Alpha)
public fun randomStrictlyNegativeInt(): StrictlyNegativeInt =
    StrictlyNegativeInt.range.random()
        .strictlyNegative
        .getOrThrow()

/** Representation of strictly negative integers, excluding zero. */
@Serializable(StrictlyNegativeIntSerializer::class)
@SinceKotools(Types, "1.1")
public sealed interface StrictlyNegativeInt : NonZeroInt,
    NegativeInt {
    // ---------- Unary operations ----------

    override fun inc(): StrictlyNegativeInt = if (value == max.value) min
    else (value + 1).strictlyNegative.getOrThrow()

    override fun dec(): StrictlyNegativeInt = if (value == min.value) max
    else (value - 1).strictlyNegative.getOrThrow()

    override fun unaryMinus(): StrictlyPositiveInt =
        (-value).strictlyPositive.getOrThrow()

    /**
     * Contains declarations for holding or building a [StrictlyPositiveInt].
     */
    public companion object {
        internal val range: IntRange by lazy { Int.MIN_VALUE..-1 }

        /** The minimum value of a [StrictlyNegativeInt]. */
        public val min: StrictlyNegativeInt by lazy(
            range.first.strictlyNegative::getOrThrow
        )

        /** The maximum value of a [StrictlyNegativeInt]. */
        public val max: StrictlyNegativeInt by lazy(
            range.last.strictlyNegative::getOrThrow
        )

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
            value.strictlyPositive.getOrNull()

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
            .strictlyNegative
            .getOrThrow()
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
    IntHolder.Serializer<StrictlyNegativeInt>({
        it.strictlyNegative.getOrThrow()
    })

@JvmInline
private value class StrictlyNegativeIntImplementation(override val value: Int) :
    StrictlyNegativeInt {
    override fun toString(): String = value.toString()
}
