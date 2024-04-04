package kotools.types.number

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotools.types.internal.InternalKotoolsTypesApi
import kotools.types.internal.KotoolsTypesPackage
import kotools.types.internal.intSerializer
import kotools.types.internal.serializationError
import kotools.types.internal.simpleNameOf
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Since

/**
 * Represents an integer number of type [Int] that equals zero.
 *
 * <br>
 * <details>
 * <summary>
 *     <b>Serialization and deserialization</b>
 * </summary>
 *
 * The [serialization and deserialization processes](https://kotlinlang.org/docs/serialization.html)
 * of this type behave like for the [Int] type.
 *
 * Here's an example of Kotlin code that encodes and decodes this type using the
 * [JavaScript Object Notation (JSON) format from kotlinx.serialization](https://kotlinlang.org/api/kotlinx.serialization/kotlinx-serialization-json/kotlinx.serialization.json/-json):
 *
 * ```kotlin
 * val encoded: String = Json.encodeToString(ZeroInt)
 * println(encoded) // 0
 * val decoded: ZeroInt = Json.decodeFromString(encoded)
 * println(decoded === ZeroInt) // true
 * ```
 * </details>
 */
@OptIn(InternalKotoolsTypesApi::class)
@Serializable(ZeroIntSerializer::class)
@Since(KotoolsTypesVersion.V4_0_0)
public object ZeroInt : PositiveInt, NegativeInt {
    override fun toInt(): Int = 0
    override fun toString(): String = "${toInt()}"
}

@InternalKotoolsTypesApi
internal object ZeroIntSerializer : KSerializer<ZeroInt> by intSerializer(
    ZeroIntDeserializationStrategy,
    intConverter = { it.toInt() }
)

@InternalKotoolsTypesApi
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
