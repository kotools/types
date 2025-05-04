package org.kotools.types.internal

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

    /**
     * Warning indicating that the test function name is invalid.
     *
     * This warning can be suppressed when the test function name starts with an
     * uppercase letter on purpose.
     */
    public const val TEST_FUNCTION_NAME: String = "TestFunctionName"

    /**
     * Warning indicating that the Java test class name is invalid.
     *
     * This warning can be suppressed when the class contains only samples.
     */
    public const val TEST_JAVA_CLASS_NAME: String = "NewClassNamingConvention"

    /**
     * Warning indicating that the declaration is never used.
     *
     * This warning can be suppressed when a generic parameter within a function
     * declared with the `expect` keyword is not used in its arguments and
     * return type.
     */
    public const val UNUSED: String = "unused"
}
