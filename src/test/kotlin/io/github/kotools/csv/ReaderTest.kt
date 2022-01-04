package io.github.kotools.csv

import org.junit.jupiter.api.Nested
import kotlin.test.Test

class ReaderTest {
    @Nested
    inner class CsvReaderOrNull {
        @Test
        fun `should pass`(): Unit = assertNotNull {
            csvReaderOrNull<TypeExample> {
                file = "test"
                folder = "folder"
            }
        }

        @Test
        fun `should fail with blank file`(): Unit = assertNull {
            csvReaderOrNull<TypeExample> { file = " " }
        }

        @Test
        fun `should fail with invalid type`(): Unit = assertNull {
            csvReaderOrNull<PrivateTypeExample> {
                file = "test"
                folder = "folder"
            }
        }

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
