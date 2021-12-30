package io.github.kotools.csv

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Nested
import kotlin.reflect.jvm.jvmName
import kotlin.test.Test
import kotlin.test.assertFailsWith

class WriterTest {
    @Nested
    inner class CsvWriter {
        private val className: String = this::class.jvmName

        @Test
        fun `should pass`(): Unit = runBlocking {
            csvWriter<Example> {
                file = "examples"
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
                csvWriter<Example> { file = "examples" }
            }
        }

        @Test
        fun `should fail with invalid type`(): Unit = runBlocking {
            val target = "examples"
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
        private val className: String = this::class.jvmName

        @Test
        fun `should pass`(): Unit = runBlocking {
            csvWriterAsync<Example> {
                file = "examples"
                records { +Example(className, 1, false) }
            }.await()
        }
    }

    @Nested
    inner class CsvWriterOrNull {
        @Test
        fun `should pass`(): Unit = runBlocking {
            csvWriterOrNull<Example> {
                file = "examples"
                records {
                    +Example(this@CsvWriterOrNull::class.jvmName, 1, false)
                }
            }.assertNotNull()
        }

        @Test
        fun `should fail with blank file name`(): Unit = runBlocking {
            csvWriterOrNull<Example> {
                records {
                    +Example(this@CsvWriterOrNull::class.jvmName, 1, false)
                }
            }.assertNull()
        }

        @Test
        fun `should fail with empty records`(): Unit = runBlocking {
            csvWriterOrNull<Example> { file = "examples" }.assertNull()
        }

        @Test
        fun `should fail with invalid type`(): Unit = runBlocking {
            val className: String = this@CsvWriterOrNull::class.jvmName
            val target = "examples"
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
        @Test
        fun `should pass`(): Unit = runBlocking {
            csvWriterOrNullAsync<Example> {
                file = "examples"
                records {
                    +Example(
                        this@CsvWriterOrNullAsync::class.jvmName,
                        1,
                        false
                    )
                }
            }.await().assertNotNull()
        }
    }
}

private data class PrivateExample2(val first: String)
