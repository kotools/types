package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.text.NotBlankString
import kotools.types.text.asNotBlankString
import kotools.types.toSuccessfulResult
import kotlin.jvm.JvmInline

/**
 * Representation of negative integers excluding [zero][ZeroInt].
 *
 * See the [toStrictlyNegativeInt] function for building a
 * [StrictlyNegativeInt].
 */
@JvmInline
@Serializable(StrictlyNegativeIntSerializer::class)
@SinceKotoolsTypes("1.1")
public value class StrictlyNegativeInt
private constructor(override val value: Int) : NonZeroInt, NegativeInt {
    internal companion object {
        infix fun of(value: Int): Result<StrictlyNegativeInt> = value
            .takeIf { it < ZeroInt.value }
            ?.toSuccessfulResult(::StrictlyNegativeInt)
            ?: Result.failure(value shouldBe aStrictlyNegativeNumber)
    }

    override fun toString(): String = "$value"
}

/**
 * Returns this integer as a [StrictlyNegativeInt], or returns an
 * [IllegalArgumentException] if this integer is [positive][PositiveInt].
 */
@SinceKotoolsTypes("1.1")
public fun Int.toStrictlyNegativeInt(): Result<StrictlyNegativeInt> =
    StrictlyNegativeInt of this

internal object StrictlyNegativeIntSerializer :
    AnyIntSerializer<StrictlyNegativeInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.StrictlyNegativeInt"::asNotBlankString
    )

    override fun deserialize(value: Int): StrictlyNegativeInt = value
        .toStrictlyNegativeInt()
        .getOrNull()
        ?: throw SerializationException(value shouldBe aStrictlyNegativeNumber)
}
