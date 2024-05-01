package org.kotools.types.internal

import kotools.types.internal.InternalKotoolsTypesApi

/**
 * Error indicating that the specified [number] shouldn't be other than zero.
 *
 * @constructor Creates an instance of [InvalidZero] with the specified
 * [number].
 */
@InternalKotoolsTypesApi
public class InvalidZero(private val number: Number) {
    /** Returns the string representation of this error. */
    override fun toString(): String =
        "'${this.number}' shouldn't be other than zero."
}
