package io.github.kotools.csv.writer

import io.github.kotools.assert.assertFails
import io.github.kotools.assert.assertNotNull
import io.github.kotools.assert.assertNull
import io.github.kotools.csv.test.ClassTypeExample
import io.github.kotools.csv.test.TypeExample
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test

class WriterProcessTest {
    private fun getConfiguration(file: String = "test"):
            Writer<TypeExample>.() -> Unit = {
        this.file = file
        folder = "folder"
        overwrite = false
        records { +TypeExample(first = "d", second = 4, third = false) }
    }

    @Nested
    inner class ProcessWriter {
        @Test
        fun `should pass with existent file`(): Unit = assertDoesNotThrow {
            TypeExample::class processWriter getConfiguration()
        }

        @Test
        fun `should pass with nonexistent file`(): Unit = assertDoesNotThrow {
            TypeExample::class processWriter getConfiguration("another-test")
        }

        @Test
        fun `should fail with non data class type`(): Unit = assertFails {
            ClassTypeExample::class processWriter {
                file = "test"
                records { +ClassTypeExample() }
            }
        }

        @Test
        fun `should fail with private type`(): Unit = assertFails {
            PrivateTypeExample::class processWriter {
                file = "test"
                records { +PrivateTypeExample("") }
            }
        }

        @Test
        fun `should fail without records`(): Unit = assertFails {
            TypeExample::class processWriter { file = "test" }
        }
    }

    @Nested
    inner class ProcessWriterOrNull {
        @Test
        fun `should pass with existent file`() {
            TypeExample::class.processWriterOrNull(getConfiguration())
                .assertNotNull()
        }

        @Test
        fun `should pass with nonexistent file`() {
            TypeExample::class.processWriterOrNull(getConfiguration("again"))
                .assertNotNull()
        }

        @Test
        fun `should fail with non data class type`(): Unit =
            ClassTypeExample::class.processWriterOrNull {
                file = "test"
                records { +ClassTypeExample() }
            }.assertNull()

        @Test
        fun `should fail with private type`(): Unit = PrivateTypeExample::class
            .processWriterOrNull {
                file = "test"
                records { +PrivateTypeExample("") }
            }.assertNull()

        @Test
        fun `should fail without records`(): Unit = TypeExample::class
            .processWriterOrNull { file = "test" }
            .assertNull()
    }

    private data class PrivateTypeExample(val a: String)
}
