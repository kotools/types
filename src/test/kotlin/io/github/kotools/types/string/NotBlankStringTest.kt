package io.github.kotools.types.string

import io.github.kotools.assert.assertFails
import io.github.kotools.assert.assertNotNull
import io.github.kotools.assert.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test

class NotBlankStringTest {
    @Nested
    inner class Create {
        @Test
        fun `should pass`(): Unit = assertDoesNotThrow { "hi".notBlank }

        @Test
        fun `should fail`(): Unit = assertFails { " ".notBlank }
    }

    @Nested
    inner class CreateOrNull {
        @Test
        fun `should pass`() {
            "hi".notBlankOrNull.assertNotNull()
        }

        @Test
        fun `should fail`(): Unit = " ".notBlankOrNull.assertNull()
    }
}
