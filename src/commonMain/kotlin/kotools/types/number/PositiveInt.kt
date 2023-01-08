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
public sealed interface PositiveInt : AnyInt {
    /** Contains declarations for holding or building a [PositiveInt]. */
    public companion object {
        /** The minimum value a [PositiveInt] can have. */
        public val min: ZeroInt = ZeroInt

        /** The maximum value a [PositiveInt] can have. */
        public val max: StrictlyPositiveInt by lazy(
            StrictlyPositiveInt.Companion::max
        )

        /** Returns a random [PositiveInt]. */
        @SinceKotoolsTypes("3.0")
        public fun random(): PositiveInt = (min.toInt()..max.toInt()).random()
            .toPositiveInt()
            .getOrThrow()
    }
}

/**
 * Returns this integer as an encapsulated [PositiveInt], or returns an
 * encapsulated [IllegalArgumentException] if this integer is
 * [strictly negative][StrictlyNegativeInt].
 */
@SinceKotoolsTypes("4.0")
public fun Int.toPositiveInt(): Result<PositiveInt> = when {
    this == ZeroInt.toInt() -> Result.success(ZeroInt)
    this > ZeroInt.toInt() -> toStrictlyPositiveInt()
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
