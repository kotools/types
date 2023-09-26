package kotools.types.number

import kotlin.jvm.JvmSynthetic

@JvmSynthetic
internal const val ZERO_ERROR_MESSAGE: String =
    "Number should be other than zero."

@JvmSynthetic
internal fun Number.shouldBePositiveMessage(): String =
    "Number should be positive (tried with $this)."

@JvmSynthetic
internal fun Number.shouldBeNegativeMessage(): String =
    "Number should be negative (tried with $this)."

@JvmSynthetic
internal fun Number.shouldBeStrictlyPositiveMessage(): String =
    "Number should be strictly positive (tried with $this)."

@JvmSynthetic
internal fun Number.shouldBeStrictlyNegativeMessage(): String =
    "Number should be strictly negative (tried with $this)."
