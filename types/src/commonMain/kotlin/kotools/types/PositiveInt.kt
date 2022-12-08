package kotools.types

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.number.aPositiveNumber
import kotools.types.number.shouldBe
import kotlin.jvm.JvmInline

/**
 * Returns this integer as a [PositiveInt], or [IllegalArgumentException] if
 * this integer is strictly negative.
 */
@SinceKotools(Types, "3.2")
public fun Int.toPositiveInt(): Result<PositiveInt> = PositiveInt of this

/** Representation of positive integers, including zero. */
@JvmInline
@SinceKotools(Types, "3.2")
public value class PositiveInt
private constructor(private val value: Int) : ExplicitInt {
    override fun toInt(): Int = value

    override fun toString(): String = "$value"

    internal companion object {
        infix fun of(value: Int): Result<PositiveInt> = value.takeIf { it >= 0 }
            ?.toSuccessfulResult(::PositiveInt)
            ?: Result.failure(value shouldBe aPositiveNumber)
    }
}
