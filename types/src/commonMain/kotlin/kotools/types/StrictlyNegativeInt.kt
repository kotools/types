package kotools.types

import kotlinx.serialization.Serializable
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.number.aStrictlyNegativeNumber
import kotools.types.number.shouldBe
import kotlin.jvm.JvmInline

/** Representation of strictly negative integers, excluding zero. */
@JvmInline
@Serializable(StrictlyNegativeIntSerializer::class)
@SinceKotools(Types, "3.2")
public value class StrictlyNegativeInt
private constructor(private val value: Int) : ExplicitInt {
    internal companion object {
        val range: IntRange by lazy { Int.MIN_VALUE..-1 }

        infix fun of(value: Int): Result<StrictlyNegativeInt> = value
            .takeIf { it < 0 }
            ?.toSuccessfulResult(::StrictlyNegativeInt)
            ?: Result.failure(value shouldBe aStrictlyNegativeNumber)

        fun random(): StrictlyNegativeInt = range.random()
            .toStrictlyNegativeInt()
            .getOrThrow()
    }

    override fun toInt(): Int = value
    override fun toString(): String = "$value"
}

internal object StrictlyNegativeIntSerializer :
    ExplicitIntSerializer<StrictlyNegativeInt>(Int::toStrictlyNegativeInt)

/**
 * Returns this integer as a [StrictlyNegativeInt], or
 * [IllegalArgumentException] if this integer is positive.
 */
@SinceKotools(Types, "3.2")
public fun Int.toStrictlyNegativeInt(): Result<StrictlyNegativeInt> =
    StrictlyNegativeInt of this
