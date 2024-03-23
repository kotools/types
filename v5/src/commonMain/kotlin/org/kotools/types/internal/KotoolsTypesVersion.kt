package org.kotools.types.internal

/** Represents a version of Kotools Types. */
internal enum class KotoolsTypesVersion {
    Unreleased;

    /** Returns the string representation of this version. */
    override fun toString(): String {
        val nameValue: String = this.name
        return if (this == Unreleased) nameValue.lowercase()
        else nameValue.drop(1)
            .replace('_', '.')
    }
}
