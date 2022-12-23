package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

/** Representation of positive integers including [zero][ZeroInt]. */
@Serializable(PositiveIntSerializer::class)
@SinceKotoolsTypes("1.1")
public sealed interface PositiveInt : AnyInt

/**
 * Returns this integer as a [PositiveInt], or returns an
 * [IllegalArgumentException] if this integer is
 * [strictly negative][StrictlyNegativeInt].
 */
@SinceKotoolsTypes("1.1")
public fun Int.toPositiveInt(): Result<PositiveInt> = when {
    this == ZeroInt.value -> Result.success(ZeroInt)
    this > ZeroInt.value -> toStrictlyPositiveInt()
    else -> Result.failure(this shouldBe aPositiveNumber)
}

internal object PositiveIntSerializer : AnyIntSerializer<PositiveInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.PositiveInt"::toNotBlankString
    )

    override fun deserialize(value: Int): PositiveInt = value.toPositiveInt()
        .getOrNull()
        ?: throw SerializationException(value shouldBe aPositiveNumber)
}
