package kotools.types.number

import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

internal sealed class NumberErrorDescription(private val value: String) {
    override fun toString(): String = value

    object NonZero : NumberErrorDescription("other than zero")
    object Positive : NumberErrorDescription("positive")
    object Negative : NumberErrorDescription("negative")
}

internal val otherThanZero = NumberErrorDescription.NonZero
internal val aPositiveNumber = NumberErrorDescription.Positive
internal val aNegativeNumber = NumberErrorDescription.Negative

internal infix fun <N : Number> N.shouldBe(
    description: NumberErrorDescription
): IllegalArgumentException = IllegalArgumentException(
    "Number should be $description (tried with $this)."
)

internal class IllegalStrictlyPositiveNumberError(number: Number) {
    val message: NotBlankString by lazy {
        "Number should be strictly positive (tried with $number)."
            .toNotBlankString()
            .getOrThrow()
    }
}
