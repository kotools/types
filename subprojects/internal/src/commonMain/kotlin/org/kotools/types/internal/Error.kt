package org.kotools.types.internal

/** Contains functions that throw an exception for the related error. */
@InternalKotoolsTypesApi
public object Error {
    /**
     * Throws an [IllegalStateException] indicating that the function with the
     * specified [signature] is deprecated with an error level.
     *
     * The function corresponding to the [signature] should be marked by the
     * [Deprecated] annotation with the [DeprecationLevel.ERROR] deprecation
     * level.
     */
    public fun deprecatedFunction(signature: String): Nothing =
        error("'$signature' function is deprecated with an error level.")

    internal fun prohibitedComparison(
        x: PlatformInteger,
        y: PlatformInteger
    ): Nothing {
        val thisClass: String? = x::class.simpleName
        val otherClass: String? = y::class.simpleName
        error("Comparing $thisClass with $otherClass is prohibited.")
    }

    internal fun prohibitedAddition(
        x: PlatformInteger,
        y: PlatformInteger
    ): Nothing {
        val thisClass: String? = x::class.simpleName
        val otherClass: String? = y::class.simpleName
        error("Adding $otherClass to $thisClass is prohibited.")
    }

    internal fun prohibitedSubtraction(
        x: PlatformInteger,
        y: PlatformInteger
    ): Nothing {
        val thisClass: String? = x::class.simpleName
        val otherClass: String? = y::class.simpleName
        error("Subtracting $otherClass from $thisClass is prohibited.")
    }

    internal fun prohibitedMultiplication(
        x: PlatformInteger,
        y: PlatformInteger
    ): Nothing {
        val thisClass: String? = x::class.simpleName
        val otherClass: String? = y::class.simpleName
        error("Multiplying $thisClass by $otherClass prohibited.")
    }

    internal fun prohibitedDivision(
        x: PlatformInteger,
        y: PlatformInteger
    ): Nothing {
        val thisClass: String? = x::class.simpleName
        val otherClass: String? = y::class.simpleName
        error(
            "Performing the quotient of dividing $thisClass by $otherClass " +
                    "is prohibited."
        )
    }

    internal fun prohibitedRemainder(
        x: PlatformInteger,
        y: PlatformInteger
    ): Nothing {
        val thisClass: String? = x::class.simpleName
        val otherClass: String? = y::class.simpleName
        error(
            "Performing the remainder of dividing $thisClass by $otherClass " +
                    "is prohibited."
        )
    }
}
