package org.kotools.types.internal

/**
 * Contains compilation warnings that can be suppressed using the [Suppress]
 * annotation.
 */
public object Warning {
    /**
     * Warning indicating that the `final` modifier is redundant.
     *
     * This warning should be suppressed when the `final` modifier is used on an
     * overridden declaration for documentation purposes.
     */
    public const val FINAL: String = "RedundantModalityModifier"
}
