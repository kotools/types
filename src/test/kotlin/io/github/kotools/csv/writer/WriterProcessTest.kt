package io.github.kotools.csv.writer

import io.github.kotools.csv.test.*
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
        fun `should pass with existent file`(): Unit = assertNotNull {
            TypeExample::class processWriterOrNull getConfiguration()
        }

        @Test
        fun `should pass with nonexistent file`(): Unit = assertNotNull {
            TypeExample::class processWriterOrNull getConfiguration("again")
        }

        @Test
        fun `should fail with non data class type`(): Unit = assertNull {
            ClassTypeExample::class processWriterOrNull {
                file = "test"
                records { +ClassTypeExample() }
            }
        }

        @Test
        fun `should fail with private type`(): Unit = assertNull {
            PrivateTypeExample::class processWriterOrNull {
                file = "test"
                records { +PrivateTypeExample("") }
            }
        }

        @Test
        fun `should fail without records`(): Unit = assertNull {
            TypeExample::class processWriterOrNull { file = "test" }
        }
    }

    private data class PrivateTypeExample(val a: String)
}
