package org.kotools.types.internal

/** Creates a platform-specific integer from the specified [decimal]. */
@InternalKotoolsTypesApi
public expect fun PlatformInteger(decimal: String): PlatformInteger

/** Implementations of this interface represent a platform-specific integer. */
@InternalKotoolsTypesApi
public interface PlatformInteger {
    // ------------------------- Arithmetic operations -------------------------

    /** Adds the [other] integer to this one. */
    public operator fun plus(other: PlatformInteger): PlatformInteger

    /** Subtracts the [other] integer from this one. */
    public operator fun minus(other: PlatformInteger): PlatformInteger

    /** Multiplies this integer by the [other] one. */
    public operator fun times(other: PlatformInteger): PlatformInteger

    /**
     * Returns the quotient of dividing this integer by the [other] one, or
     * throws an [ArithmeticException] if the [other] integer is zero.
     */
    public operator fun div(other: PlatformInteger): PlatformInteger

    /**
     * Returns the remainder of dividing this integer by the [other] one, or
     * throws an [ArithmeticException] if the [other] integer is zero.
     */
    public operator fun rem(other: PlatformInteger): PlatformInteger

    // ------------------------------ Conversions ------------------------------

    /** Returns the decimal string representation of this integer. */
    override fun toString(): String
}
