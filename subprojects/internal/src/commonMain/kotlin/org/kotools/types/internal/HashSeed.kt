package org.kotools.types.internal

/** Enumeration that provides a hash seed for a given type. */
@InternalKotoolsTypesApi
public enum class HashSeed {
    /** Hash seed for the `Integer` type. */
    Integer;

    /** Returns a hash seed for this entry. */
    public fun toInt(): Int = this.name.hashCode()

    /** Returns the string representation of this entry. */
    override fun toString(): String = "Hash seed for ${this.name}"
}
