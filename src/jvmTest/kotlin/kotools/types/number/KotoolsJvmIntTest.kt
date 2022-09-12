package kotools.types.number

import kotools.assert.Nested
import kotools.assert.Test
import kotools.assert.assertEquals
import kotools.types.string.NotBlankString

class KotoolsJvmIntTest {
    @Nested
    inner class ToNotBlankString {
        @Test
        fun `should return its value as a not blank string`() {
            // GIVEN
            val value = 1
            val x: KotoolsJvmInt = NonZeroInt(value)
            // WHEN
            val result: NotBlankString = x.toNotBlankString()
            // THEN
            result.value assertEquals value.toString()
        }
    }
}
