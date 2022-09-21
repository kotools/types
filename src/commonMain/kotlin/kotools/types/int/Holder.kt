package kotools.types.int

import kotools.types.annotations.SinceKotoolsTypes

@SinceKotoolsTypes("3.0")
internal fun IntHolder(value: Int, validator: IntValidator? = null): IntHolder =
    IntHolderImplementation(value, validator)

// ---------- Binary operations ----------

/** Adds the [other] value to this value. */
@SinceKotoolsTypes("3.0")
public infix operator fun Int.plus(other: IntHolder): Int = plus(other.value)

/** Subtracts the [other] value from this value. */
@SinceKotoolsTypes("3.0")
public infix operator fun Int.minus(other: IntHolder): Int = minus(other.value)

// TODO: Add the times operation between an Int and an IntHolder
// TODO: Add the div operation between an Int and an IntHolder

// ---------- Comparisons ----------

/**
 * Compares this value with the [other] value for order.
 * Returns `0` if this value equals the [other] value, a negative number if this
 * value is less than the [other] value, or a positive number if this value is
 * greater than the [other] value.
 */
@SinceKotoolsTypes("3.0")
public infix operator fun Int.compareTo(other: IntHolder): Int =
    compareTo(other.value)

/** Parent of classes responsible for holding integers. */
@SinceKotoolsTypes("3.0")
public sealed interface IntHolder : Comparable<IntHolder> {
    /** The value to hold. */
    public val value: Int

    // ---------- Binary operations ----------

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: Int): Int = value + other

    /** Adds the [other] value to this [value]. */
    public infix operator fun plus(other: IntHolder): Int = plus(other.value)

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: Int): Int = value - other

    /** Subtracts the [other] value from this [value]. */
    public infix operator fun minus(other: IntHolder): Int = minus(other.value)

    // TODO: Add the times operation with an Int
    // TODO: Add the times operation with an IntHolder
    // TODO: Add the div operation with an Int
    // TODO: Add the div operation with an IntHolder

    // ---------- Comparisons ----------

    /**
     * Compares this [value] with the [other] value for order.
     * Returns `0` if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    public infix operator fun compareTo(other: Int): Int =
        value.compareTo(other)

    /**
     * Compares this [value] with the [other] value for order.
     * Returns `0` if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override infix fun compareTo(other: IntHolder): Int = compareTo(other.value)

    // ---------- Conversions ----------

    // TODO: Add the conversion to a NotBlankString
}

@SinceKotoolsTypes("3.0")
private class IntHolderImplementation(
    override val value: Int,
    validator: IntValidator? = null
) : IntHolder {
    init {
        validator?.let { require(it isValid value) }
    }
}

@SinceKotoolsTypes("3.0")
internal fun interface IntValidator {
    infix fun isValid(value: Int): Boolean
}
