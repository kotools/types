package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString
import kotools.types.toSuccessfulResult
import kotlin.jvm.JvmInline

/** Representation of positive integers excluding [zero][ZeroInt]. */
@JvmInline
@Serializable(StrictlyPositiveIntSerializer::class)
@SinceKotoolsTypes("1.1")
public value class StrictlyPositiveInt
private constructor(override val value: Int) : NonZeroInt, PositiveInt {
    internal companion object {
        infix fun of(value: Int): Result<StrictlyPositiveInt> = value
            .takeIf { it > ZeroInt.value }
            ?.toSuccessfulResult(::StrictlyPositiveInt)
            ?: Result.failure(value shouldBe aStrictlyPositiveNumber)
    }

    override fun toString(): String = "$value"
}

/**
 * Returns this integer as a [StrictlyPositiveInt], or returns an
 * [IllegalArgumentException] if this integer is [negative][NegativeInt].
 */
@SinceKotoolsTypes("1.1")
public fun Int.toStrictlyPositiveInt(): Result<StrictlyPositiveInt> =
    StrictlyPositiveInt of this

internal object StrictlyPositiveIntSerializer :
    AnyIntSerializer<StrictlyPositiveInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.StrictlyPositiveInt"::toNotBlankString
    )

    override fun deserialize(value: Int): StrictlyPositiveInt = value
        .toStrictlyPositiveInt()
        .getOrNull()
        ?: throw SerializationException(value shouldBe aStrictlyPositiveNumber)
}
