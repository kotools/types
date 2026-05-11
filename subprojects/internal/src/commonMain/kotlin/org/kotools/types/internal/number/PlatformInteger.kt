package org.kotools.types.internal.number

import org.kotools.types.internal.InternalKotoolsTypesApi

@OptIn(InternalKotoolsTypesApi::class)
internal expect fun PlatformInteger(value: Long): PlatformInteger

/**
 * Represents a mathematical integer provided by the underlying platform.
 *
 * This type abstracts platform-specific integer implementations used internally
 * by Kotools Types for supporting arbitrary-precision arithmetic in Kotlin
 * Multiplatform projects.
 */
@InternalKotoolsTypesApi
public interface PlatformInteger {
    /** Contains type-level declarations for [PlatformInteger]. */
    public companion object {
        /** Returns a [PlatformInteger] representing the specified [value]. */
        public fun of(value: Long): PlatformInteger = PlatformInteger(value)
    }
}
