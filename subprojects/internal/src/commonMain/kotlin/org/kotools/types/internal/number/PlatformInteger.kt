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
public interface PlatformInteger
