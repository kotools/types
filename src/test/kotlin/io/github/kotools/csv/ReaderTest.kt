package io.github.kotools.csv

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Nested
import kotlin.test.Test

class ReaderTest {
    private val configuration: ReaderScope.() -> Unit by lazy {
        {
            file = "test"
            folder = "folder"
        }
    }

    @Nested
    inner class CsvReader {
        @Test
        fun `should pass`(): Unit = runBlocking {
            csvReader<TypeExample>(configuration).size assertNotEquals 0
        }

        @Test
        fun `should fail with blank file`(): Unit = assertFails {
            csvReader<TypeExample> { file = " " }
        }

        @Test
        fun `should fail with invalid type`(): Unit = assertFails {
            csvReader<PrivateTypeExample>(configuration)
        }

        @Test
        fun `should fail with nonexistent file`(): Unit = assertFails {
            csvReader<TypeExample> { file = "unknown" }
        }
    }

    @Nested
    inner class CsvReaderOrNull {
        @Test
        fun `should pass`(): Unit =
            assertNotNull { csvReaderOrNull<TypeExample>(configuration) }

        @Test
        fun `should fail with blank file`(): Unit = assertNull {
            csvReaderOrNull<TypeExample> { file = " " }
        }

        @Test
        fun `should fail with invalid type`(): Unit =
            assertNull { csvReaderOrNull<PrivateTypeExample>(configuration) }

        @Test
        fun `should fail with nonexistent file`(): Unit = assertNull {
            csvReaderOrNull<TypeExample> { file = "unknown" }
        }
    }

    data class TypeExample(
        val first: String,
        val second: Int,
        val third: Boolean
    )

    private data class PrivateTypeExample(val a: String)
}
