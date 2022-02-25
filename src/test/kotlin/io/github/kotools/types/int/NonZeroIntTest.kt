package io.github.kotools.types.int

import io.github.kotools.assert.assertEquals
import io.github.kotools.assert.assertFails
import io.github.kotools.assert.assertNotNull
import io.github.kotools.assert.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NonZeroIntTest {
    @Nested
    inner class CompareTo {
        @Test
        fun `should pass`(): Unit = assertTrue { 1.nonZero < 2.nonZero }

        @Test
        fun `should fail`(): Unit = assertFalse { 1.nonZero > 2.nonZero }
    }

    @Nested
    inner class Create {
        @Test
        fun `should pass`(): Unit = assertDoesNotThrow { NonZeroInt create 1 }

        @Test
        fun `should fail`(): Unit = assertFails(NonZeroInt.ERROR_MESSAGE) {
            NonZeroInt create 0
        }
    }

    @Nested
    inner class CreateOrNull {
        @Test
        fun `should pass`() {
            NonZeroInt.createOrNull(1)
                .assertNotNull()
        }

        @Test
        fun `should fail`(): Unit = NonZeroInt.createOrNull(0)
            .assertNull(NonZeroInt.ERROR_MESSAGE)
    }

    @Nested
    inner class Div {
        @Test
        fun `should pass`(): Unit =
            (6.nonZero / 3.nonZero) assertEquals 2.nonZero
    }

    @Nested
    inner class Times {
        @Test
        fun `should pass`(): Unit =
            (2.nonZero * 4.nonZero) assertEquals 8.nonZero
    }

    @Nested
    inner class UnaryMinus {
        @Test
        fun `should pass`(): Unit = -(1.nonZero) assertEquals (-1).nonZero
    }
}
