package org.kotools.types.internal.number

import org.kotools.types.internal.InternalKotoolsTypesApi

/** Returns a [PlatformInteger] representing the specified [value]. */
@InternalKotoolsTypesApi
public expect fun PlatformInteger(value: Long): PlatformInteger

/** Returns a [PlatformInteger] representing the number described by [value]. */
@InternalKotoolsTypesApi
public expect fun PlatformInteger(value: String): PlatformInteger

/**
 * Represents an integer, with operations that can use platform-specific APIs.
 */
@InternalKotoolsTypesApi
public interface PlatformInteger {
    /** Compares this integer with the [other] one for order. */
    public operator fun compareTo(other: PlatformInteger): Int

    /** Returns the negative of this integer. */
    public operator fun unaryMinus(): PlatformInteger

    /** Returns the sum of this integer and the [other] one. */
    public operator fun plus(other: PlatformInteger): PlatformInteger

    /** Returns the difference of this integer and the [other] one. */
    public operator fun minus(other: PlatformInteger): PlatformInteger
}
