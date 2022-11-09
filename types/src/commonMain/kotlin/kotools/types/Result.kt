package kotools.types

import kotools.types.string.NotBlankString
import kotools.types.string.toNotBlankString

// ---------- Result ----------

internal inline fun <T> KotoolsTypesBuilderResult<T>.onError(
    action: (KotoolsTypesBuilderError) -> T
): T = when (this) {
    is KotoolsTypesBuilderError -> action(this)
    is KotoolsTypesBuilderSuccess -> value
}

internal sealed interface KotoolsTypesBuilderResult<out A>

// ---------- Error ----------

internal inline fun builderError(
    message: () -> NotBlankString
): KotoolsTypesBuilderError {
    val msg: NotBlankString = message()
    return KotoolsTypesBuilderError(msg)
}

internal inline fun <A> A.shouldBe(
    description: () -> NotBlankString
): KotoolsTypesBuilderError = builderError {
    val expectedType: NotBlankString = description()
    "Given value should be $expectedType (tried with $this).".toNotBlankString()
}

internal class KotoolsTypesBuilderError(message: NotBlankString) :
    IllegalArgumentException(message.value),
    KotoolsTypesBuilderResult<Nothing>

// ---------- Success ----------

internal inline fun <A, B> A.toSuccessfulResult(
    lazyValue: (A) -> B
): KotoolsTypesBuilderSuccess<B> {
    val value: B = lazyValue(this)
    return KotoolsTypesBuilderSuccess(value)
}

internal data class KotoolsTypesBuilderSuccess<out T>(val value: T) :
    KotoolsTypesBuilderResult<T>
