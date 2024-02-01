package kotools.types.internal

/** Represents a version of Kotools Types. */
@InternalKotoolsTypesApi
public enum class KotoolsTypesVersion {
    Unreleased,
    V4_4_0,
    V4_3_1,
    V4_2_0,
    V4_1_0,
    V4_0_0,
    V3_0_0,
    V1_1_0;

    /** Returns the string representation of this version. */
    override fun toString(): String {
        val nameValue: String = this.name
        return if (this == Unreleased) nameValue.lowercase()
        else nameValue.drop(1)
            .replace('_', '.')
    }
}
