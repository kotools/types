package kotools.types

internal inline fun <A, B> A.toSuccessfulResult(block: (A) -> B): Result<B> {
    val value: B = block(this)
    return Result.success(value)
}
