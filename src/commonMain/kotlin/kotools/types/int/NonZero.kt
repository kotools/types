package kotools.types.int

import kotools.types.annotations.SinceKotoolsTypes

// ---------- Builders ----------

/**
 * Returns the [value] as a [NonZeroInt], or throws an
 * [IllegalArgumentException] if the [value] equals zero.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class)
public fun NonZeroInt(value: Int): NonZeroInt = NonZeroIntImplementation(value)

/**
 * Returns the [value] as a [NonZeroInt], or returns `null` if the [value]
 * equals zero.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun NonZeroIntOrNull(value: Int): NonZeroInt? = try {
    NonZeroInt(value)
} catch (_: IllegalArgumentException) {
    null
}

/**
 * Returns this value as a [NonZeroInt], or throws an [IllegalArgumentException]
 * if this value equals zero.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class)
public fun Int.toNonZeroInt(): NonZeroInt = NonZeroInt(this)

/**
 * Returns this value as a [NonZeroInt].
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws an [IllegalArgumentException] if it represents zero.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toNonZeroInt(): NonZeroInt = toInt().toNonZeroInt()

/**
 * Returns this value as a [NonZeroInt], or returns `null` if this value equals
 * zero.
 */
@SinceKotoolsTypes("3.0")
public fun Int.toNonZeroIntOrNull(): NonZeroInt? = NonZeroIntOrNull(this)

/**
 * Returns this value as a [NonZeroInt], or returns `null` if this value is not
 * a valid representation of a number or if it represents zero.
 */
@SinceKotoolsTypes("3.0")
public fun String.toNonZeroIntOrNull(): NonZeroInt? =
    toIntOrNull()?.toNonZeroIntOrNull()

/** Parent of classes responsible for holding integers other than zero. */
@SinceKotoolsTypes("3.0")
public sealed interface NonZeroInt : IntHolder

@SinceKotoolsTypes("3.0")
internal data class NonZeroIntImplementation(override val value: Int) :
    NonZeroInt,
    IntHolder by IntHolder(value, { it != 0 })
