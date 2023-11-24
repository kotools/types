package kotools.types.internal

/** Represents a version of Kotools Types. */
public enum class KotoolsTypesVersion {
    V4_3_2;

    /** Returns the string representation of this version. */
    override fun toString(): String = name.drop(1)
        .replace('_', '.')
}
