package kotools.types.internal

/** Represents a package of Kotools Types. */
@InternalKotoolsTypesApi
public enum class KotoolsTypesPackage {
    /** The package containing declarations for manipulating texts. */
    Text;

    /** Returns the string representation of this package. */
    override fun toString(): String = "kotools.types.${this.name.lowercase()}"
}
