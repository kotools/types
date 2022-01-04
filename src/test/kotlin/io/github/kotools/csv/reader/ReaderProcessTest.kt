package io.github.kotools.csv.reader

import io.github.kotools.csv.test.assertNotEquals
import io.github.kotools.csv.test.assertNotNull
import io.github.kotools.csv.test.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertFails

class ReaderProcessTest {
    private val configuration: Reader.() -> Unit by lazy {
        {
            file = "test"
            folder = "folder"
        }
    }

    @Nested
    inner class ProcessReader {
        @Test
        fun `should pass`(): Unit = assertDoesNotThrow {
            TypeExample::class.processReader(configuration)
                .size assertNotEquals 0
        }

        @Test
        fun `should fail with blank file`() {
            assertFails {
                TypeExample::class processReader {}
            }
        }

        @Test
        fun `should fail with non data class type`() {
            assertFails { ClassTypeExample::class processReader configuration }
        }

        @Test
        fun `should fail with private type`() {
            assertFails {
                PrivateTypeExample::class processReader configuration
            }
        }
    }

    @Nested
    inner class ProcessReaderOrNull {
        @Test
        fun `should pass`(): Unit = assertNotNull {
            TypeExample::class processReaderOrNull configuration
        }

        @Test
        fun `should fail with blank file`(): Unit = assertNull {
            TypeExample::class processReaderOrNull {}
        }

        @Test
        fun `should fail with non data class type`(): Unit = assertNull {
            ClassTypeExample::class processReaderOrNull configuration
        }

        @Test
        fun `should fail with private type`(): Unit = assertNull {
            PrivateTypeExample::class processReaderOrNull configuration
        }
    }

    class ClassTypeExample

    data class TypeExample(
        val first: String,
        val second: Int,
        val third: Boolean
    )

    private data class PrivateTypeExample(val a: String)
}
