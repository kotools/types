package org.kotools.types.internal

/**
 * Contains compilation warnings that can be suppressed using the [Suppress]
 * annotation.
 */
@InternalKotoolsTypesApi
public object Warning {
    /**
     * Warning indicating that the Java test class name is invalid.
     *
     * This warning can be suppressed when the class contains only samples.
     */
    public const val TEST_JAVA_CLASS_NAME: String = "NewClassNamingConvention"
}
