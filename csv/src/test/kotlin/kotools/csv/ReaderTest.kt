package kotools.csv

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.test.Test
import kotlin.test.fail

@ExperimentalCoroutinesApi
class CsvReaderTest {
    @Test
    fun read_should_pass_with_an_existing_resource_file_containing_records(): Unit =
        runBlocking {
            "reader/valid".csvOrThrow()
                .read()
                .onException { fail(it.message, it) }
                .records.forEach(::println)
        }

    // TODO: read should pass with an existing system file containing records

    @Test
    fun read_should_fail_with_a_non_existing_file(): Unit = runBlocking {
        val result: CsvReaderResult = "reader/not-found".csvOrThrow()
            .read()
        when (result) {
            is CsvReaderResult.Success -> fail()
            is CsvFileNotFoundException -> result.message.assertNotNull()
                .isNotBlank()
                .assertTrue()
            is CsvReaderResult.Exception -> fail(result.message, result)
        }
    }

    @Test
    fun read_should_fail_with_an_existing_file_has_a_header_with_a_blank_field(): Unit =
        runBlocking {
            val result: CsvReaderResult = "reader/header-with-blank-field"
                .csvOrThrow()
                .read()
            when (result) {
                is CsvReaderResult.Success -> fail()
                is CsvFileHeaderWithBlankFieldException -> result.message
                    .assertNotNull()
                    .isNotBlank()
                    .assertTrue()
                is CsvReaderResult.Exception -> fail(result.message, result)
            }
        }

    @Test
    fun read_should_fail_with_an_existing_file_that_does_not_contain_a_record(): Unit =
        runBlocking {
            val result: CsvReaderResult = "reader/no-record".csvOrThrow()
                .read()
            when (result) {
                is CsvReaderResult.Success -> fail()
                is CsvFileWithoutRecordException -> result.message
                    .assertNotNull()
                    .isNotBlank()
                    .assertTrue()
                is CsvReaderResult.Exception -> fail(result.message, result)
            }
        }
}
