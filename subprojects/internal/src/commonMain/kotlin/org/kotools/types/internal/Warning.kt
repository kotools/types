package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi

/**
 * Contains compilation warnings that can be suppressed using the [Suppress]
 * annotation.
 */
@InternalKotoolsTypesApi
public object Warning {
    /**
     * Warning indicating that the `final` modifier is redundant.
     *
     * This warning should be suppressed when the `final` modifier is used on an
     * overridden declaration for documentation purposes.
     */
    public const val FINAL: String = "RedundantModalityModifier"

    /**
     * Warning indicating that the function name is invalid.
     *
     * This warning can be suppressed when the function name starts with an
     * uppercase letter or contains a special character, but on purpose.
     */
    public const val FUNCTION_NAME: String = "FunctionName"
}
