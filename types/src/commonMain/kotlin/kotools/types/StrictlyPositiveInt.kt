package kotools.types

import kotlinx.serialization.Serializable
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.number.aStrictlyPositiveNumber
import kotools.types.number.shouldBe
import kotlin.jvm.JvmInline

/** Representation of strictly positive integers, excluding zero. */
@JvmInline
@Serializable(StrictlyPositiveIntSerializer::class)
@SinceKotools(Types, "3.2")
public value class StrictlyPositiveInt
private constructor(private val value: Int) : ExplicitInt {
    internal companion object {
        val range: IntRange by lazy { 1..Int.MAX_VALUE }

        infix fun of(value: Int): Result<StrictlyPositiveInt> = value
            .takeIf { it > 0 }
            ?.toSuccessfulResult(::StrictlyPositiveInt)
            ?: Result.failure(value shouldBe aStrictlyPositiveNumber)

        fun random(): StrictlyPositiveInt = range.random()
            .toStrictlyPositiveInt()
            .getOrThrow()
    }

    override fun toInt(): Int = value
    override fun toString(): String = "$value"
}

internal object StrictlyPositiveIntSerializer :
    ExplicitIntSerializer<StrictlyPositiveInt>(Int::toStrictlyPositiveInt)

/**
 * Returns this integer as a [StrictlyPositiveInt], or
 * [IllegalArgumentException] if this integer is negative.
 */
@SinceKotools(Types, "3.2")
public fun Int.toStrictlyPositiveInt(): Result<StrictlyPositiveInt> =
    StrictlyPositiveInt of this
