package kotools.types.number

import kotools.assert.assertEquals
import kotlin.test.Test

class NumberTest {
    @Test
    fun companionInvoke_should_pass() {
        val value = 10
        val result: Number<Int> = Number(value)
        result.value assertEquals value
    }
}
