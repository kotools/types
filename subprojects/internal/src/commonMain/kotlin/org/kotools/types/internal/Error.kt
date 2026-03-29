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
}
