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
private constructor(private val value: Int) : NonZeroInt, PositiveInt {
    /**
     * Contains declarations for holding or building a [StrictlyPositiveInt].
     */
    public companion object {
        /** The minimum value a [StrictlyPositiveInt] can have. */
        public val min: StrictlyPositiveInt by lazy(
            1.toStrictlyPositiveInt()::getOrThrow
        )

        /** The maximum value a [StrictlyPositiveInt] can have. */
        public val max: StrictlyPositiveInt by lazy(
            Int.MAX_VALUE.toStrictlyPositiveInt()::getOrThrow
        )

        internal infix fun of(value: Int): Result<StrictlyPositiveInt> = value
            .takeIf { it > ZeroInt.toInt() }
            ?.toSuccessfulResult(::StrictlyPositiveInt)
            ?: Result.failure(value shouldBe aStrictlyPositiveNumber)

        /** Returns a random [StrictlyPositiveInt]. */
        @SinceKotoolsTypes("3.0")
        public fun random(): StrictlyPositiveInt = (min.value..max.value)
            .random()
            .toStrictlyPositiveInt()
            .getOrThrow()
    }

    override fun toInt(): Int = value
    override fun toString(): String = "$value"
}

/**
 * Returns this integer as a [StrictlyPositiveInt], or returns an
 * [IllegalArgumentException] if this integer is [negative][NegativeInt].
 */
@SinceKotoolsTypes("4.0")
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
