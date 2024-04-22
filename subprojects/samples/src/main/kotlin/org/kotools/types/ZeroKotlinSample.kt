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

    fun toStringSample() {
        val message: String = Zero()
            .toString() // TABS: 2
        println(message) // 0
    } // END
}
