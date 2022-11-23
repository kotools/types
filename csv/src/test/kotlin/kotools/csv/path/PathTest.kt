package kotools.csv.path

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotools.types.string.NotBlankString
import kotools.types.string.notBlankStringOrThrow
import kotlin.test.Test

class CsvPathTest {
    // ---------- NotBlankString.csv() ----------

    @Test
    fun csv_should_pass_with_a_NotBlankString_not_suffixed_by_the_CSV_extension() {
        val file: NotBlankString = notBlankStringOrThrow("file")
        file.csv.getOrThrow()
            .toString() assertEquals "$file$csvExtension"
    }

    @Test
    fun csv_should_pass_with_a_NotBlankString_suffixed_by_the_CSV_extension() {
        val file: NotBlankString = notBlankStringOrThrow("file$csvExtension")
        file.csv.getOrThrow()
            .toString() assertEquals file.value
    }

    @Test
    fun csv_should_fail_with_a_NotBlankString_that_equals_the_CSV_extension() {
        val file: NotBlankString = csvExtension.toNotBlankString()
        assertFailsWith<CsvExtensionAsPathException>(file.csv::getOrThrow)
            .message.assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
