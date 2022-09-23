package kotools.types.core

@Throws(IndexOutOfBoundsException::class)
internal fun indexOutOfBounds(index: Int, size: Int): Nothing {
    val message: String = indexOutOfBoundsMessage(index, size)
    throw IndexOutOfBoundsException(message)
}

internal fun indexOutOfBoundsMessage(index: Int, size: Int): String =
    "Index: $index, Size: $size"

/**
 * Returns the result of calling the [block] function, or returns `null` if the
 * [block] function throws a [RuntimeException].
 */
internal inline fun <T> tryOrNull(block: () -> T): T? = try {
    block()
} catch (_: RuntimeException) {
    null
}
