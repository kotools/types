package io.github.kotools.csv.reader

import io.github.kotools.assert.assertFails
import io.github.kotools.assert.assertNotEquals
import io.github.kotools.assert.assertNotNull
import io.github.kotools.assert.assertNull
import io.github.kotools.csv.test.ClassTypeExample
import io.github.kotools.csv.test.TypeExample
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test

class ReaderProcessTest {
    private val invalidHeaderConfiguration: Reader<*>.() -> Unit by lazy {
        {
            file = "invalid-header"
            folder = FOLDER
        }
    }
    private val testConfiguration: Reader<*>.() -> Unit by lazy {
        {
            file = "test"
            folder = FOLDER
        }
    }

    @Nested
    inner class ProcessReader {
        @Test
        fun `should pass`(): Unit = assertDoesNotThrow {
            TypeExample::class.processReader(testConfiguration)
                .size assertNotEquals 0
        }

        @Test
        fun `should pass ignoring invalid pagination`(): Unit =
            assertDoesNotThrow {
                TypeExample::class.processReader {
                    file = "test"
                    folder = FOLDER
                    pagination {
                        page = 0
                        size = 1
                    }
                }.size assertNotEquals 0
            }

        @Test
        fun `should pass with filter`(): Unit = assertDoesNotThrow {
            TypeExample::class.processReader {
                file = "test"
                folder = FOLDER
                filter { second % 2 == 0 }
            }.size assertNotEquals 0
        }

        @Test
        fun `should pass with pagination`(): Unit = assertDoesNotThrow {
            TypeExample::class.processReader {
                file = "test"
                folder = FOLDER
                pagination { page = 2 }
            }.size assertNotEquals 0
        }

        @Test
        fun `should fail with invalid header in CSV file`(): Unit =
            assertFails {
                TypeExample::class.processReader(invalidHeaderConfiguration)
            }

        @Test
        fun `should fail with blank file`(): Unit = assertFails {
            TypeExample::class processReader {}
        }

        @Test
        fun `should fail with non data class type`(): Unit = assertFails {
            ClassTypeExample::class processReader testConfiguration
        }

        @Test
        fun `should fail with private type`(): Unit = assertFails {
            PrivateTypeExample::class processReader testConfiguration
        }
    }

    @Nested
    inner class ProcessReaderOrNull {
        @Test
        fun `should pass`() {
            TypeExample::class.processReaderOrNull(testConfiguration)
                .assertNotNull()
        }

        @Test
        fun `should pass ignoring invalid pagination`() {
            TypeExample::class.processReaderOrNull {
                file = "test"
                folder = FOLDER
                pagination {
                    page = 0
                    size = 1
                }
            }.assertNotNull()
        }

        @Test
        fun `should pass with filter`() {
            TypeExample::class.processReaderOrNull {
                file = "test"
                folder = FOLDER
                filter { second % 2 == 0 }
            }.assertNotNull()
        }

        @Test
        fun `should pass with pagination`() {
            TypeExample::class.processReaderOrNull {
                file = "test"
                folder = FOLDER
                pagination { page = 2 }
            }.assertNotNull()
        }

        @Test
        fun `should fail with invalid header in CSV file`(): Unit =
            TypeExample::class.processReaderOrNull(invalidHeaderConfiguration)
                .assertNull()

        @Test
        fun `should fail with blank file`(): Unit = TypeExample::class
            .processReaderOrNull {}
            .assertNull()

        @Test
        fun `should fail with non data class type`(): Unit =
            ClassTypeExample::class.processReaderOrNull(testConfiguration)
                .assertNull()

        @Test
        fun `should fail with private type`(): Unit = PrivateTypeExample::class
            .processReaderOrNull(testConfiguration)
            .assertNull()
    }

    private data class PrivateTypeExample(val a: String)

    private companion object {
        const val FOLDER: String = "folder"
    }
}
