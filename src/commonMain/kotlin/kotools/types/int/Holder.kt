package kotools.types.int

import kotools.types.annotations.SinceKotoolsTypes

/** Parent of classes responsible for holding integers. */
@SinceKotoolsTypes("3.0")
public sealed interface IntHolder : Comparable<IntHolder> {
    /** The value to hold. */
    public val value: Int

    /** Optional property responsible for checking the [value]'s validity. */
    public val validator: IntValidator?

    /**
     * Compares this [value] with the [other] value for order.
     * Returns `0` if this [value] equals the [other] value, a negative number
     * if this [value] is less than the [other] value, or a positive number if
     * this [value] is greater than the [other] value.
     */
    override fun compareTo(other: IntHolder): Int
}

@SinceKotoolsTypes("3.0")
internal class IntHolderImplementation(
    override val value: Int,
    override val validator: IntValidator? = null
) : IntHolder {
    init {
        validator?.let { require(it isValid value) }
    }

    override fun compareTo(other: IntHolder): Int = value.compareTo(other.value)
}

/** Parent of classes responsible for validating integers. */
@SinceKotoolsTypes("3.0")
public fun interface IntValidator {
    /** Returns `true` if the [value] is valid, or returns `false` if not. */
    public infix fun isValid(value: Int): Boolean

    /** Returns `true` if the [value] is invalid, or returns `false` if not. */
    public infix fun isNotValid(value: Int): Boolean = !isValid(value)
}
