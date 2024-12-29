package kotools.types.experimental

import kotools.types.number.AnyInt
import org.kotools.types.ExperimentalKotoolsTypesApi
import kotlin.test.Test
import kotlin.test.assertEquals

internal class AnyIntCommonSample {
    @OptIn(ExperimentalKotoolsTypesApi::class)
    @Test
    fun unaryMinusOperator() {
        val number = AnyInt(1)
        val result: AnyInt = -number // or number.unaryMinus()
        assertEquals(expected = "-1", actual = "$result")
    }
}
