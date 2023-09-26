package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.SinceKotoolsTypes
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString
import kotlin.jvm.JvmSynthetic

/** Representation of the zero integer. */
@Serializable(ZeroIntSerializer::class)
@SinceKotoolsTypes("4.0")
public object ZeroInt : PositiveInt, NegativeInt {
    override fun toInt(): Int = 0
    override fun toString(): String = "${toInt()}"
}

internal object ZeroIntSerializer : AnyIntSerializer<ZeroInt> {
    override val serialName: Result<NotBlankString> by lazy {
        val value: String = ZeroInt::class.qualifiedName
            ?: error("Unable to get qualified name of 'ZeroInt'.")
        value.toNotBlankString()
    }

    override fun deserialize(value: Int): ZeroInt = if (value == 0) ZeroInt
    else {
        val message: String = deserializationErrorMessage(value)
        throw SerializationException(message)
    }

    @JvmSynthetic
    internal fun deserializationErrorMessage(value: Int): String =
        "Unable to deserialize '$value' to ${ZeroInt::class.simpleName}."
}
