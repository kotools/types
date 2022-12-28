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
 * Representation of positive integers excluding [zero][ZeroInt].
 *
 * See the [asStrictlyPositiveInt] property for building a
 * [StrictlyPositiveInt].
 */
@JvmInline
@Serializable(StrictlyPositiveIntSerializer::class)
@SinceKotoolsTypes("1.1")
public value class StrictlyPositiveInt
private constructor(override val asInt: Int) : NonZeroInt, PositiveInt {
    internal companion object {
        infix fun of(value: Int): Result<StrictlyPositiveInt> = value
            .takeIf { it > ZeroInt.asInt }
            ?.toSuccessfulResult(::StrictlyPositiveInt)
            ?: Result.failure(value shouldBe aStrictlyPositiveNumber)
    }

    override fun toString(): String = "$asInt"
}

/**
 * Returns this integer as a [StrictlyPositiveInt], or returns an
 * [IllegalArgumentException] if this integer is [negative][NegativeInt].
 */
@SinceKotoolsTypes("4.0")
public val Int.asStrictlyPositiveInt: Result<StrictlyPositiveInt>
    get() = StrictlyPositiveInt of this

internal object StrictlyPositiveIntSerializer :
    AnyIntSerializer<StrictlyPositiveInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.StrictlyPositiveInt"::asNotBlankString
    )

    override fun deserialize(value: Int): StrictlyPositiveInt = value
        .asStrictlyPositiveInt
        .getOrNull()
        ?: throw SerializationException(value shouldBe aStrictlyPositiveNumber)
}
