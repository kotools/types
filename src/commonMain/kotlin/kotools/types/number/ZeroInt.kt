package kotools.types.number

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.KotoolsTypesVersion
import kotools.types.internal.Since
import kotools.types.internal.intSerializer
import kotools.types.internal.serializationError
import kotools.types.internal.simpleNameOf

/** Represents an integer number of type [Int] that equals zero. */
@Serializable(ZeroIntSerializer::class)
@Since(KotoolsTypesVersion.V4_0_0)
public object ZeroInt : PositiveInt, NegativeInt {
    override fun toInt(): Int = 0
    override fun toString(): String = "${toInt()}"
}

internal object ZeroIntSerializer : KSerializer<ZeroInt> by intSerializer(
    ZeroIntDeserializationStrategy,
    intConverter = { it.toInt() }
)

private object ZeroIntDeserializationStrategy :
    DeserializationStrategy<ZeroInt> {
    override val descriptor: SerialDescriptor by lazy {
        val simpleName: String = simpleNameOf<ZeroInt>()
        val serialName = "${KotoolsTypesPackage.Number}.$simpleName"
        PrimitiveSerialDescriptor(serialName, PrimitiveKind.INT)
    }

    override fun deserialize(decoder: Decoder): ZeroInt {
        val value: Int = decoder.decodeInt()
        return if (value == 0) ZeroInt
        else serializationError("Unable to deserialize $value to ZeroInt.")
    }
}
