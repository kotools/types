package io.github.kotools.csv

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Nested
import kotlin.reflect.jvm.jvmName
import kotlin.test.Test
import kotlin.test.assertFailsWith

class WriterTest {
    private val header: Set<String> by lazy { setOf("h1", "h2") }
    private val rows: List<Map<String, String>> by lazy {
        listOf(mapOf(header.first() to "a", header.last() to "b"))
    }
    private val validConfiguration: Writer.() -> Unit by lazy {
        {
            file = "test"
            folder = "folder"
            overwrite = true
            separator = semicolon
            header = this@WriterTest.header
            records { +this@WriterTest.rows.first().values }
        }
    }

    @Nested
    inner class CsvWriter {
        @Test
        fun `should pass`(): Unit =
            runBlocking { csvWriter(validConfiguration) }

        @Test
        fun `should fail with blank file name`(): Unit = runBlocking {
            assertFailsWith<CsvException> {
                csvWriter {
                    header = this@WriterTest.header
                    records { +this@WriterTest.rows.first().values }
                }
            }
        }

        @Test
        fun `should fail with empty header`(): Unit = runBlocking {
            assertFailsWith<CsvException> {
                csvWriter {
                    file = "test"
                    folder = "folder"
                    records { +this@WriterTest.rows.first().values }
                }
            }
        }

        @Test
        fun `should fail with empty records`(): Unit = runBlocking {
            assertFailsWith<CsvException> {
                csvWriter {
                    file = "test"
                    folder = "folder"
                    header = this@WriterTest.header
                }
            }
        }
    }

    @Nested
    inner class CsvWriterAs {
        private val className: String = this::class.jvmName

        @Test
        fun `should pass`(): Unit = runBlocking {
            csvWriterAs<Example> {
                file = "examples"
                records { +Example(className, 1, false) }
            }
        }

        @Test
        fun `should fail with blank file name`(): Unit = runBlocking {
            assertFailsWith<CsvException> {
                csvWriterAs<Example> {
                    records { +Example(className, 1, false) }
                }
            }
        }

        @Test
        fun `should fail with empty records`(): Unit = runBlocking {
            assertFailsWith<CsvException> {
                csvWriterAs<Example> { file = "examples" }
            }
        }

        @Test
        fun `should fail with invalid type`(): Unit = runBlocking {
            val target = "examples"
            assertFailsWith<CsvException> {
                csvWriterAs<InvalidExample> {
                    file = target
                    records { +InvalidExample(className, 1, false) }
                }
            }
            assertFailsWith<CsvException> {
                csvWriterAs<PrivateExample2> {
                    file = target
                    records { +PrivateExample2(className) }
                }
            }
        }
    }

    @Nested
    inner class CsvWriterAsAsync {
        private val className: String = this::class.jvmName

        @Test
        fun `should pass`(): Unit = runBlocking {
            csvWriterAsAsync<Example> {
                file = "examples"
                records { +Example(className, 1, false) }
            }.await()
        }
    }

    @Nested
    inner class CsvWriterAsOrNull {
        @Test
        fun `should pass`(): Unit = runBlocking {
            csvWriterAsOrNull<Example> {
                file = "examples"
                records {
                    +Example(this@CsvWriterAsOrNull::class.jvmName, 1, false)
                }
            }.assertNotNull()
        }

        @Test
        fun `should fail with blank file name`(): Unit = runBlocking {
            csvWriterAsOrNull<Example> {
                records {
                    +Example(this@CsvWriterAsOrNull::class.jvmName, 1, false)
                }
            }.assertNull()
        }

        @Test
        fun `should fail with empty records`(): Unit = runBlocking {
            csvWriterAsOrNull<Example> { file = "examples" }.assertNull()
        }

        @Test
        fun `should fail with invalid type`(): Unit = runBlocking {
            val className: String = this@CsvWriterAsOrNull::class.jvmName
            val target = "examples"
            csvWriterAsOrNull<InvalidExample> {
                file = target
                records { +InvalidExample(className, 1, false) }
            }.assertNull()
            csvWriterAsOrNull<PrivateExample2> {
                file = target
                records { +PrivateExample2(className) }
            }.assertNull()
        }
    }

    @Nested
    inner class CsvWriterAsOrNullAsync {
        @Test
        fun `should pass`(): Unit = runBlocking {
            csvWriterAsOrNullAsync<Example> {
                file = "examples"
                records {
                    +Example(
                        this@CsvWriterAsOrNullAsync::class.jvmName,
                        1,
                        false
                    )
                }
            }.await().assertNotNull()
        }
    }

    @Nested
    inner class CsvWriterAsync {
        @Test
        fun `should pass`(): Unit = runBlocking {
            csvWriterAsync(validConfiguration)
                .await()
        }
    }

    @Nested
    inner class CsvWriterOrNull {
        @Test
        fun `should pass`(): Unit = runBlocking {
            csvWriterOrNull(validConfiguration)
                .assertNotNull()
        }

        @Test
        fun `should fail with blank file name`(): Unit = runBlocking {
            csvWriterOrNull {
                header = this@WriterTest.header
                records { +this@WriterTest.rows.first().values }
            }.assertNull()
        }

        @Test
        fun `should fail with empty header`(): Unit = runBlocking {
            csvWriterOrNull {
                file = "test"
                folder = "folder"
                records { +this@WriterTest.rows.first().values }
            }.assertNull()
        }

        @Test
        fun `should fail with empty records`(): Unit = runBlocking {
            csvWriterOrNull {
                file = "test"
                folder = "folder"
                header = this@WriterTest.header
            }.assertNull()
        }
    }

    @Nested
    inner class CsvWriterOrNullAsync {
        @Test
        fun `should pass`(): Unit = runBlocking {
            csvWriterOrNullAsync(validConfiguration)
                .await()
                .assertNotNull()
        }
    }
}

private data class PrivateExample2(val first: String)
