package kotools.types.int

import kotools.types.annotations.SinceKotoolsTypes

internal fun IntHolder(value: Int, validator: IntValidator? = null): IntHolder =
    IntHolderImplementation(value, validator)

// ---------- Binary operations ----------

// TODO: Add the plus operation between an Int and an IntHolder
// TODO: Add the minus operation between an Int and an IntHolder
// TODO: Add the times operation between an Int and an IntHolder
// TODO: Add the div operation between an Int and an IntHolder
// TODO: Add the div operation between an IntHolder and an Int
// TODO: Add the div operation between an IntHolder and an IntHolder

// ---------- Comparisons ----------

// TODO: Add the comparison operation between an Int and an IntHolder

/** Parent of classes responsible for holding integers. */
@SinceKotoolsTypes("3.0")
public sealed interface IntHolder : Comparable<IntHolder> {
    /** The value to hold. */
    public val value: Int

    // ---------- Binary operations ----------

    // TODO: Add the plus operation with an Int
    // TODO: Add the plus operation with an IntHolder
    // TODO: Add the minus operation with an Int
    // TODO: Add the minus operation with an IntHolder
    // TODO: Add the times operation with an Int
    // TODO: Add the times operation with an IntHolder

    // ---------- Comparisons ----------

    // TODO: Add the comparison operation with an Int

    /**
     * Compares this [value] with the [other] value for order.
     * Returns `0` if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override infix fun compareTo(other: IntHolder): Int

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

    override fun compareTo(other: IntHolder): Int = value.compareTo(other.value)
}

@SinceKotoolsTypes("3.0")
internal fun interface IntValidator {
    infix fun isValid(value: Int): Boolean
}
