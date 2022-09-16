package kotools.types.number

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotlin.random.Random
import kotlin.test.Test

class NonZeroNumberTest {
    // ---------- Companion.invoke ----------

    @Test
    fun companionInvoke_should_pass_with_a_Number_other_than_0() {
        var value: Int = Random.nextInt()
        while (value == 0) value = Random.nextInt()
        val result: NonZeroNumber<Int> = NonZeroNumber(value)
        result.value assertEquals value
    }

    @Test
    fun companionInvoke_should_throw_an_error_with_0() {
        assertFailsWith<IllegalArgumentException> { NonZeroNumber(0) }
    }

    // ---------- Companion.orNull ----------

    @Test
    fun companionOrNull_should_pass_with_a_Number_other_than_0() {
        var value: Int = Random.nextInt()
        while (value == 0) value = Random.nextInt()
        val result: NonZeroNumber<Int>? = NonZeroNumber orNull value
        result.assertNotNull().value assertEquals value
    }

    @Test
    fun companionOrNull_should_return_null_with_0(): Unit =
        (NonZeroNumber orNull 0).assertNull()
}
