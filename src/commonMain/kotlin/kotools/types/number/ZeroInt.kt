package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.text.NotBlankString
import kotools.types.text.asNotBlankString

/** Representation of the zero integer. */
@Serializable(ZeroIntSerializer::class)
@SinceKotoolsTypes("4.0")
public object ZeroInt : PositiveInt, NegativeInt {
    override val asInt: Int = 0

    /**
     * Returns `true` if the [other] value is a [ZeroInt] or `false` otherwise.
     */
    override fun equals(other: Any?): Boolean = other is ZeroInt

    /** Returns the hash code of this integer. */
    override fun hashCode(): Int = asInt.hashCode()

    override fun toString(): String = "$asInt"
}

internal object ZeroIntSerializer : AnyIntSerializer<ZeroInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.ZeroInt"::asNotBlankString
    )

    override fun deserialize(value: Int): ZeroInt = if (value == 0) ZeroInt
    else throw SerializationException(
        "Unable to deserialize $value to ZeroInt."
    )
}
