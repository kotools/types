package kotools.types

internal fun indexOutOfBounds(index: Int, size: Int): Nothing {
    val message: String = indexOutOfBoundsMessage(index, size)
    throw IndexOutOfBoundsException(message)
}

internal fun indexOutOfBoundsMessage(index: Int, size: Int): String =
    "Index: $index, Size: $size"
