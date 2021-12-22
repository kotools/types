package io.github.kotools.csv

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Nested
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ReaderTest {
    private val validConfiguration: Reader.() -> Unit by lazy {
        {
            file = "test"
            folder = "folder"
            separator = comma
        }
    }

    private inline fun assertIsValid(block: () -> List<Map<String, String>>?) {
        val result: List<Map<String, String>>? = block()
        result.assertNotNull()
        result?.let { it.size assertNotEquals 0 }
    }

    @Nested
    inner class CsvReader {
        @Test
        fun `should pass`(): Unit = runBlocking {
            assertIsValid { csvReader(validConfiguration) }
        }

        @Test
        fun `should fail with blank file name`(): Unit = runBlocking {
            assertFailsWith<CsvConfigurationException> {
                csvReader { }
            }
        }

        @Test
        fun `should fail with unknown file`(): Unit = runBlocking {
            assertFailsWith<CsvConfigurationException> {
                csvReader { file = "unknown" }
            }
        }
    }

    @Nested
    inner class CsvReaderOrNull {
        @Test
        fun `should pass`(): Unit = runBlocking {
            assertIsValid { csvReaderOrNull(validConfiguration) }
        }

        @Test
        fun `should fail with blank file name`(): Unit = runBlocking {
            assertNull {
                csvReaderOrNull { }
            }
        }

        @Test
        fun `should fail with unknown file`(): Unit = runBlocking {
            assertNull {
                csvReaderOrNull { file = "unknown" }
            }
        }
    }

    @Nested
    inner class CsvReaderOrNullAsync {
        @Test
        fun `should pass`(): Unit = runBlocking {
            assertIsValid {
                csvReaderOrNullAsync(validConfiguration)
                    .await()
            }
        }
    }
}
