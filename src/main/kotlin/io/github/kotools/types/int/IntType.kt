package io.github.kotools.types.int

import kotools.types.annotations.SinceKotoolsTypes

@SinceKotoolsTypes("1.0")
internal sealed interface IntType {
    val value: Int

    infix operator fun compareTo(other: IntType): Int =
        value.compareTo(other.value)

    infix operator fun div(other: IntType): Int = value / other.value

    infix operator fun minus(other: IntType): Int = value - other.value

    infix operator fun plus(other: IntType): Int = value + other.value

    infix operator fun times(other: IntType): Int = value * other.value
}
