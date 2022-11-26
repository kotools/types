package kotools.types

// ---------- Success ----------

internal inline fun <A, B> A.toSuccessfulResult(builder: (A) -> B): Result<B> {
    val value: B = builder(this)
    return Result.success(value)
}

// ---------- Failure ----------

internal inline fun <A : Throwable> failureOf(
    builder: () -> A
): Result<Nothing> {
    val exception: A = builder()
    return Result.failure(exception)
}
