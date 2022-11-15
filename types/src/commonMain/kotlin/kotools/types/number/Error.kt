package kotools.types.number

import kotools.types.string.NotBlankString
import kotools.types.string.toNotBlankStringOrNull

internal val otherThanZero = NumberErrorDescription.NonZero

// Could be renamed "positive" starting from v3.4.
internal val aPositiveNumber = NumberErrorDescription.Positive

// Could be renamed "strictlyPositive" starting from v3.4.
internal val aStrictlyPositiveNumber = NumberErrorDescription.StrictlyPositive

// Could be renamed "negative" starting from v3.4.
internal val aNegativeNumber = NumberErrorDescription.Negative

// Could be renamed "strictlyNegative" starting from v3.4.
internal val aStrictlyNegativeNumber = NumberErrorDescription.StrictlyNegative

/**
 * Returns an [IllegalArgumentException] with a message computed from this value
 * and the [description], or without a message if the [description] is blank.
 */
internal infix fun <T : Number> T.shouldBe(
    description: NumberErrorDescription
): IllegalArgumentException = description.value
    ?.let { "Given number should be $it (tried with $this)." }
    .let(::IllegalArgumentException)

internal sealed class NumberErrorDescription(value: String) {
    val value: NotBlankString? = value.toNotBlankStringOrNull()

    object NonZero : NumberErrorDescription("other than zero")
    object Positive : NumberErrorDescription("positive")
    object StrictlyPositive : NumberErrorDescription("strictly positive")
    object Negative : NumberErrorDescription("negative")
    object StrictlyNegative : NumberErrorDescription("strictly negative")
}
