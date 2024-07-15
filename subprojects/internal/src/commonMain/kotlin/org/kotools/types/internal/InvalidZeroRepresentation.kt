package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi

/**
 * Error indicating that the specified [number] is an invalid representation of
 * the [zero](https://en.wikipedia.org/wiki/0) number.
 *
 * @constructor Creates an instance of [InvalidZeroRepresentation] from the
 * specified [number].
 */
@InternalKotoolsTypesApi
public class InvalidZeroRepresentation(private val number: Any) {
    /** Returns the string representation of this error. */
    override fun toString(): String =
        "'${this.number}' is not a valid representation of zero."
}
