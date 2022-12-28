package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.text.NotBlankString
import kotools.types.text.asNotBlankString

/**
 * Representation of positive integers including [zero][ZeroInt].
 *
 * See the [asPositiveInt] for building a [PositiveInt].
 */
@Serializable(PositiveIntSerializer::class)
@SinceKotoolsTypes("1.1")
public sealed interface PositiveInt : AnyInt

/**
 * Returns this integer as a [PositiveInt], or returns an
 * [IllegalArgumentException] if this integer is
 * [strictly negative][StrictlyNegativeInt].
 */
@SinceKotoolsTypes("4.0")
public val Int.asPositiveInt: Result<PositiveInt>
    get() = when {
        this == ZeroInt.asInt -> Result.success(ZeroInt)
        this > ZeroInt.asInt -> toStrictlyPositiveInt()
        else -> Result.failure(this shouldBe aPositiveNumber)
    }

internal object PositiveIntSerializer : AnyIntSerializer<PositiveInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.PositiveInt"::asNotBlankString
    )

    override fun deserialize(value: Int): PositiveInt = value.asPositiveInt
        .getOrNull()
        ?: throw SerializationException(value shouldBe aPositiveNumber)
}
