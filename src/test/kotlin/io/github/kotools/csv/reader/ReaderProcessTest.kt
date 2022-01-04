package io.github.kotools.csv.reader

import io.github.kotools.csv.test.ClassTypeExample
import io.github.kotools.csv.test.TypeExample
import io.github.kotools.csv.test.assertFails
import io.github.kotools.csv.test.assertNotEquals
import io.github.kotools.csv.test.assertNotNull
import io.github.kotools.csv.test.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test

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
        fun `should fail with blank file`(): Unit = assertFails {
            TypeExample::class processReader {}
        }

        @Test
        fun `should fail with non data class type`(): Unit =
            assertFails { ClassTypeExample::class processReader configuration }

        @Test
        fun `should fail with private type`(): Unit = assertFails {
            PrivateTypeExample::class processReader configuration
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

    private data class PrivateTypeExample(val a: String)
}
