package io.github.kotools.csv

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Nested
import kotlin.test.Test

class ReaderTest {
    @Nested
    inner class CsvReaderOrNull {
        @Test
        fun `should pass`(): Unit = runBlocking {
            val result: List<Map<String, String>>? = csvReaderOrNull {
                file = "test"
                folder = "folder"
                separator = comma
            }
            result.assertNotNull()
            result?.let { it.size assertNotEquals 0 }
        }

        @Test
        fun `should fail with blank file name`(): Unit = runBlocking {
            csvReaderOrNull {}.assertNull()
        }

        @Test
        fun `should fail with unknown file`(): Unit = runBlocking {
            csvReaderOrNull { file = "unknown" }.assertNull()
        }
    }
}
