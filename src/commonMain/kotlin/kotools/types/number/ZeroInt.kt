package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

/** Representation of the zero integer. */
@Serializable(ZeroIntSerializer::class)
@SinceKotoolsTypes("4.0")
public object ZeroInt : PositiveInt, NegativeInt {
    override val value: Int = 0

    /**
     * Returns `true` if the [other] value is a [ZeroInt] or `false` otherwise.
     */
    override fun equals(other: Any?): Boolean = other is ZeroInt

    /** Returns the hash code of this [value]. */
    override fun hashCode(): Int = value.hashCode()

    override fun toString(): String = "$value"
}

internal object ZeroIntSerializer : AnyIntSerializer<ZeroInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.ZeroInt"::toNotBlankString
    )

    override fun deserialize(value: Int): ZeroInt = if (value == 0) ZeroInt
    else throw SerializationException(
        "Unable to deserialize $value to ZeroInt."
    )
}
