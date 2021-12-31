package io.github.kotools.csv.deprecated

import io.github.kotools.csv.test.assertNotNull
import io.github.kotools.csv.test.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Nested
import kotlin.test.Test
import kotlin.test.assertFailsWith

class WriterTest {
    @Nested
    inner class CsvWriter {
        private val className: String = this::class.simpleName!!.lowercase()

        @Test
        fun `should pass`(): Unit = runBlocking {
            csvWriter<Example> {
                file = "examples-$className"
                records { +Example(className, 1, false) }
            }
        }

        @Test
        fun `should fail with blank file name`(): Unit = runBlocking {
            assertFailsWith<CsvException> {
                csvWriter<Example> {
                    records { +Example(className, 1, false) }
                }
            }
        }

        @Test
        fun `should fail with empty records`(): Unit = runBlocking {
            assertFailsWith<CsvException> {
                csvWriter<Example> { file = "examples-$className" }
            }
        }

        @Test
        fun `should fail with invalid type`(): Unit = runBlocking {
            val target = "examples-$className"
            assertFailsWith<CsvException> {
                csvWriter<InvalidExample> {
                    file = target
                    records { +InvalidExample(className, 1, false) }
                }
            }
            assertFailsWith<CsvException> {
                csvWriter<PrivateExample2> {
                    file = target
                    records { +PrivateExample2(className) }
                }
            }
        }
    }

    @Nested
    inner class CsvWriterAsync {
        private val className: String = this::class.simpleName!!.lowercase()

        @Test
        fun `should pass`(): Unit = runBlocking {
            csvWriterAsync<Example> {
                file = "examples-$className"
                records { +Example(className, 1, false) }
            }.await()
        }
    }

    @Nested
    inner class CsvWriterOrNull {
        private val className: String = this::class.simpleName!!.lowercase()

        @Test
        fun `should pass`(): Unit = runBlocking {
            csvWriterOrNull<Example> {
                file = "examples-$className"
                records { +Example(className, 1, false) }
            }.assertNotNull()
        }

        @Test
        fun `should fail with blank file name`(): Unit = runBlocking {
            csvWriterOrNull<Example> {
                records { +Example(className, 1, false) }
            }.assertNull()
        }

        @Test
        fun `should fail with empty records`(): Unit = runBlocking {
            csvWriterOrNull<Example> { file = "examples-$className" }
                .assertNull()
        }

        @Test
        fun `should fail with invalid type`(): Unit = runBlocking {
            val target = "examples-$className"
            csvWriterOrNull<InvalidExample> {
                file = target
                records { +InvalidExample(className, 1, false) }
            }.assertNull()
            csvWriterOrNull<PrivateExample2> {
                file = target
                records { +PrivateExample2(className) }
            }.assertNull()
        }
    }

    @Nested
    inner class CsvWriterOrNullAsync {
        private val className: String = this::class.simpleName!!.lowercase()

        @Test
        fun `should pass`(): Unit = runBlocking {
            csvWriterOrNullAsync<Example> {
                file = "examples-$className"
                records { +Example(className, 1, false) }
            }.await().assertNotNull()
        }
    }
}

private data class PrivateExample2(val first: String)
