package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

/** Representation of negative integers including [zero][ZeroInt]. */
@Serializable(NegativeIntSerializer::class)
@SinceKotoolsTypes("1.1")
public sealed interface NegativeInt : AnyInt {
    /** Contains declarations for holding or building a [NegativeInt]. */
    public companion object {
        /** The minimum value a [NegativeInt] can have. */
        public val min: StrictlyNegativeInt by lazy(
            StrictlyNegativeInt.Companion::min
        )

        /** The maximum value a [NegativeInt] can have. */
        public val max: ZeroInt = ZeroInt

        /** Returns a random [NegativeInt]. */
        @SinceKotoolsTypes("3.0")
        public fun random(): NegativeInt {
            val range: IntRange = min.toInt()..max.toInt()
            return range.random()
                .toNegativeInt()
                .getOrThrow()
        }
    }

    @SinceKotoolsTypes("4.0")
    override fun toInt(): Int

    @SinceKotoolsTypes("4.0")
    override fun toString(): String
}

/**
 * Returns this integer as an encapsulated [NegativeInt], or returns an
 * encapsulated [IllegalArgumentException] if this integer is
 * [strictly positive][StrictlyPositiveInt].
 */
@SinceKotoolsTypes("1.1")
public fun Int.toNegativeInt(): Result<NegativeInt> = when {
    this == ZeroInt.toInt() -> Result.success(ZeroInt)
    this < ZeroInt.toInt() -> toStrictlyNegativeInt()
    else -> Result.failure(this shouldBe aNegativeNumber)
}

internal object NegativeIntSerializer : AnyIntSerializer<NegativeInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.NegativeInt"::toNotBlankString
    )

    override fun deserialize(value: Int): NegativeInt = value.toNegativeInt()
        .getOrNull()
        ?: throw SerializationException(value shouldBe aNegativeNumber)
}
