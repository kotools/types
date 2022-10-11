package kotools.types.string

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertNull
import kotools.types.PositiveInt
import kotools.types.StrictlyPositiveInt
import org.junit.jupiter.api.Test
import kotlin.random.Random

class NotBlankStringJvmTest {
    @Test
    fun get_should_throw_an_error_with_an_index_that_is_out_of_bounds() {
        val string = NotBlankString("oops")
        val index: StrictlyPositiveInt = string.length
        assertFailsWith<IndexOutOfBoundsException> { string[index] }
    }

    @Test
    fun getOrNull_should_pass_with_an_index_in_bounds() {
        val value = "hello world"
        val index: PositiveInt = Random.nextInt(from = 0, until = value.length)
            .let(::PositiveInt)
        val string = NotBlankString(value)
        val result: Char? = string getOrNull index
        result.assertNotNull() assertEquals value[index.value]
    }

    @Test
    fun getOrNull_should_return_null_with_an_index_that_is_out_of_bounds() {
        val string = NotBlankString("oops")
        val index: StrictlyPositiveInt = string.length
        val result: Char? = string getOrNull index
        result.assertNull()
    }
}
