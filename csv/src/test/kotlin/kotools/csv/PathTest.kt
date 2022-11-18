package kotools.csv

import kotools.assert.*
import kotools.types.string.NotBlankString
import kotools.types.string.notBlankStringOrThrow
import kotlin.test.Test
import kotlin.test.fail

class CsvPathTest {
    // ---------- String.csv() ----------

    @Test
    fun csv_should_pass_with_a_not_blank_String_not_suffixed_by_the_CSV_extension() {
        val fileName = "file"
        fileName.csv()
            .onException { fail(it.message, it) }
            .path.value assertEquals "$fileName$csvExtension"
    }

    @Test
    fun csv_should_pass_with_a_not_blank_String_suffixed_by_the_CSV_extension() {
        val fileName = "file.csv"
        fileName.csv()
            .onException { fail(it.message, it) }
            .path.value assertEquals fileName
    }

    @Test
    fun csv_should_fail_with_a_blank_String(): Unit =
        when (val result: CsvPathResult.FromString = " ".csv()) {
            is CsvPathResult.Exception.BlankString -> result.message
                .assertNotNull()
                .isNotBlank()
                .assertTrue()
            is CsvPathResult.Exception -> fail(result.message, result)
            is CsvPathResult.Success -> fail()
        }

    @Test
    fun csv_should_fail_with_a_not_blank_String_that_equals_the_CSV_extension() {
        val file: String = csvExtension.toString()
        when (val result: CsvPathResult.FromString = file.csv()) {
            is CsvPathResult.Exception.CsvExtensionAsPath -> result.message
                .assertNotNull()
                .isNotBlank()
                .assertTrue()
            is CsvPathResult.Exception -> fail(result.message, result)
            is CsvPathResult.Success -> fail()
        }
    }

    // ---------- NotBlankString.csv() ----------

    @Test
    fun csv_should_pass_with_a_NotBlankString_not_suffixed_by_the_CSV_extension() {
        val fileName: NotBlankString = notBlankStringOrThrow("file")
        fileName.csv()
            .onException { fail(it.message, it) }
            .path.value assertEquals "$fileName$csvExtension"
    }

    @Test
    fun csv_should_pass_with_a_NotBlankString_suffixed_by_the_CSV_extension() {
        val fileName: NotBlankString =
            notBlankStringOrThrow("file$csvExtension")
        fileName.csv()
            .onException { fail(it.message, it) }
            .path.value assertEquals fileName.value
    }

    @Test
    fun csv_should_fail_with_a_NotBlankString_that_equals_the_CSV_extension() {
        val fileName: NotBlankString = csvExtension.toNotBlankString()
        when (val result: CsvPathResult.FromNotBlankString = fileName.csv()) {
            is CsvPathResult.Exception.CsvExtensionAsPath -> result.message
                .assertNotNull()
                .isNotBlank()
                .assertTrue()
            is CsvPathResult.Exception -> fail(result.message, result)
            else -> fail()
        }
    }

    // ---------- String.csvOrNull() ----------

    @Test
    fun csvOrNull_should_pass_with_a_not_blank_String_not_suffixed_by_the_CSV_extension() {
        val file = "file"
        file.csvOrNull()
            .assertNotNull()
            .path.value assertEquals "$file$csvExtension"
    }

    @Test
    fun csvOrNull_should_pass_with_a_not_blank_String_suffixed_by_the_CSV_extension() {
        val file = "file$csvExtension"
        file.csvOrNull()
            .assertNotNull()
            .path.value assertEquals file
    }

    @Test
    fun csvOrNull_should_fail_with_a_blank_String(): Unit = " ".csvOrNull()
        .assertNull()

    @Test
    fun csvOrNull_should_fail_with_a_not_blank_String_that_equals_the_CSV_extension(): Unit =
        csvExtension.toString()
            .csvOrNull()
            .assertNull()

    // ---------- NotBlankString.csvOrNull() ----------

    @Test
    fun csvOrNull_should_pass_with_a_NotBlankString_not_suffixed_by_the_CSV_extension() {
        val file: NotBlankString = notBlankStringOrThrow("file")
        file.csvOrNull()
            .assertNotNull()
            .path.value assertEquals "${file.value}$csvExtension"
    }

    @Test
    fun csvOrNull_should_pass_with_a_NotBlankString_suffixed_by_the_CSV_extension() {
        val file: NotBlankString = notBlankStringOrThrow("file$csvExtension")
        file.csvOrNull()
            .assertNotNull()
            .path.value assertEquals file.value
    }

    @Test
    fun csvOrNull_should_fail_with_a_NotBlankString_that_equals_the_CSV_extension(): Unit =
        csvExtension.toNotBlankString()
            .csvOrNull()
            .assertNull()

    // ---------- String.csvOrThrow() ----------

    @Test
    fun csvOrThrow_should_pass_with_a_not_blank_String_not_suffixed_by_the_CSV_extension() {
        val file = "file"
        file.csvOrThrow().path.value assertEquals "$file$csvExtension"
    }

    @Test
    fun csvOrThrow_should_pass_with_a_not_blank_String_suffixed_by_the_CSV_extension() {
        val file = "file$csvExtension"
        file.csvOrThrow().path.value assertEquals file
    }

    @Test
    fun csvOrThrow_should_fail_with_a_blank_String(): Unit =
        assertFailsWith<CsvPathResult.Exception.BlankString>(" "::csvOrThrow)
            .message.assertNotNull()
            .isNotBlank()
            .assertTrue()

    @Test
    fun csvOrThrow_should_fail_with_a_not_blank_String_that_equals_the_CSV_extension() {
        val file: String = csvExtension.toString()
        val result: CsvPathResult.Exception.CsvExtensionAsPath =
            assertFailsWith(file::csvOrThrow)
        result.message.assertNotNull()
            .isNotBlank()
            .assertTrue()
    }

    // ---------- NotBlankString.csvOrThrow() ----------

    @Test
    fun csvOrThrow_should_pass_with_a_NotBlankString_not_suffixed_by_the_CSV_extension() {
        val file: NotBlankString = notBlankStringOrThrow("file")
        file.csvOrThrow().path.value assertEquals "$file$csvExtension"
    }

    @Test
    fun csvOrThrow_should_pass_with_a_NotBlankString_suffixed_by_the_CSV_extension() {
        val file: NotBlankString = notBlankStringOrThrow("file$csvExtension")
        file.csvOrThrow().path.value assertEquals file.value
    }

    @Test
    fun csvOrThrow_should_fail_with_a_NotBlankString_that_equals_the_CSV_extension() {
        val file: NotBlankString = csvExtension.toNotBlankString()
        val result: CsvPathResult.Exception.CsvExtensionAsPath =
            assertFailsWith(file::csvOrThrow)
        result.message.assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
