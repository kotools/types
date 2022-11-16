package kotools.csv

import kotools.assert.assertEquals
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotools.types.string.NotBlankString
import kotlin.test.Test
import kotlin.test.fail

private inline fun CsvPathResult.onException(
    action: (CsvPathResult.Exception) -> CsvPathResult.Success
): CsvPathResult.Success = when (this) {
    is CsvPathResult.Success -> this
    is CsvPathResult.Exception -> action(this)
}

class CsvPathTest {
    @Test
    fun csv_should_pass_with_a_NotBlankString_not_suffixed_by_the_CSV_extension() {
        // GIVEN
        val fileName = NotBlankString("file")
        // WHEN & THEN
        fileName.csv()
            .onException { fail(it.message, it) }
            .path.value assertEquals "$fileName$CSV_EXTENSION"
    }

    @Test
    fun csv_should_pass_with_a_NotBlankString_suffixed_by_the_CSV_extension() {
        // GIVEN
        val fileName = NotBlankString("file$CSV_EXTENSION")
        // WHEN & THEN
        fileName.csv()
            .onException { fail(it.message, it) }
            .path.value assertEquals fileName.value
    }

    @Test
    fun csv_should_fail_with_a_NotBlankString_that_equals_the_CSV_extension() {
        // GIVEN
        val fileName = NotBlankString(CSV_EXTENSION)
        // WHEN & THEN
        when (val result: CsvPathResult.FromNotBlankString = fileName.csv()) {
            is CsvPathResult.Exception.CsvExtensionAsPath -> result.message
                .assertNotNull()
                .isNotBlank()
                .assertTrue()
            is CsvPathResult.Exception -> fail(result.message, result)
            else -> fail()
        }
    }
}
