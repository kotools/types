package io.github.kotools.types.int

import io.github.kotools.assert.assertEquals
import io.github.kotools.assert.assertFails
import io.github.kotools.assert.assertNotNull
import io.github.kotools.assert.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test

class StrictlyPositiveIntTest {
    @Nested
    inner class Create {
        @Test
        fun `should pass`(): Unit = assertDoesNotThrow { 1.strictlyPositive }

        @Test
        fun `should fail`(): Unit = assertFails { 0.strictlyPositive }
    }

    @Nested
    inner class CreateOrNull {
        @Test
        fun `should pass`() {
            1.strictlyPositiveOrNull.assertNotNull()
        }

        @Test
        fun `should fail`(): Unit = 0.strictlyPositiveOrNull.assertNull()
    }

    @Nested
    inner class Div {
        @Test
        fun `should pass with a non-zero int`(): Unit =
            (2.strictlyPositive / 2.nonZero) assertEquals 1.nonZero

        @Test
        fun `should pass with a strictly negative int`(): Unit =
            (2.strictlyPositive / (-2).strictlyNegative)
                .assertEquals((-1).strictlyNegative)

        @Test
        fun `should pass with a strictly positive int`(): Unit =
            (2.strictlyPositive / 2.strictlyPositive)
                .assertEquals(1.strictlyPositive)
    }

    @Nested
    inner class Times {
        @Test
        fun `should pass with a non-zero int`(): Unit =
            (2.strictlyPositive * 2.nonZero) assertEquals 4.nonZero

        @Test
        fun `should pass with a strictly negative int`(): Unit =
            (2.strictlyPositive * (-2).strictlyNegative)
                .assertEquals((-4).strictlyNegative)

        @Test
        fun `should pass with a strictly positive int`(): Unit =
            (2.strictlyPositive * 2.strictlyPositive)
                .assertEquals(4.strictlyPositive)
    }

    @Nested
    inner class ToString {
        @Test
        fun `should pass`(): Unit = 2.strictlyPositive.toString()
            .assertEquals("2")
    }

    @Nested
    inner class UnaryMinus {
        @Test
        fun `should pass`(): Unit =
            -(1.strictlyPositive) assertEquals (-1).strictlyNegative
    }
}
