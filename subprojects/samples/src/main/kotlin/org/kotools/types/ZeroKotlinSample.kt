package org.kotools.types

import kotools.types.experimental.ExperimentalKotoolsTypesApi

@OptIn(ExperimentalKotoolsTypesApi::class)
internal object ZeroKotlinSample {
    fun equalsOverride() {
        val first = Zero()
        val second = Zero()
        val result: Boolean = first == second // or first.equals(second)
        println(result) // true
    } // END

    fun hashCodeOverride() {
        val first = Zero()
        val second = Zero()
        val result: Boolean = first.hashCode() == second.hashCode()
        println(result) // true
    } // END

    fun toByte() {
        val number: Byte = Zero()
            .toByte() // TABS: 1
        println(number) // 0
    } // END

    fun toShort() {
        val number: Short = Zero()
            .toShort() // TABS: 1
        val expected: Short = 0
        println(number == expected) // true
    } // END

    fun toInt() {
        val number: Int = Zero()
            .toInt() // TABS: 1
        val expected = 0
        println(number == expected) // true
    } // END

    fun toLong() {
        val number: Long = Zero()
            .toLong() // TABS: 1
        val expected = 0L
        println(number == expected) // true
    } // END

    fun toFloat() {
        val number: Float = Zero()
            .toFloat() // TABS: 1
        val expected = 0f
        println(number == expected) // true
    } // END

    fun toDouble() {
        val number: Double = Zero()
            .toDouble() // TABS: 1
        val expected = 0.0
        println(number == expected) // true
    } // END

    fun toStringSample() {
        val message: String = Zero()
            .toString() // TABS: 2
        println(message) // 0
    } // END
}
