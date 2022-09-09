package kotools.types

import kotools.types.annotations.SinceKotoolsTypes

@SinceKotoolsTypes("1.3")
@Throws(IndexOutOfBoundsException::class)
internal fun indexOutOfBounds(index: Int, size: Int): Nothing {
    val message: String = indexOutOfBoundsMessage(index, size)
    throw IndexOutOfBoundsException(message)
}

@SinceKotoolsTypes("1.3")
internal fun indexOutOfBoundsMessage(index: Int, size: Int): String =
    "Index: $index, Size: $size"
