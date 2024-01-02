package kotools.types.internal.text

/** Represents a special character. */
public enum class SpecialChar(private val value: Char) {
    AtSign('@');

    /** Returns this special character as [Char]. */
    public fun toChar(): Char = value

    /** Returns the string representation of this special character. */
    override fun toString(): String = "$value"
}
