package org.kotools.types.internal.number

import org.kotools.types.internal.InternalKotoolsTypesApi

/** Returns a [PlatformInteger] representing exactly the specified [value]. */
@InternalKotoolsTypesApi
public expect fun PlatformInteger(value: Long): PlatformInteger

/** Implementations of this interface represent a platform-specific integer. */
@InternalKotoolsTypesApi
public interface PlatformInteger {
    // ------------------------------ Comparisons ------------------------------

    /**
     * Compares this integer with the [other] one for order.
     *
     * Returns a negative number, zero, or a positive number as this integer is
     * less than, equal to, or greater than the [other] one.
     */
    public operator fun compareTo(other: PlatformInteger): Int
}
