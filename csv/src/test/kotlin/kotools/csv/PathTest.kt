package kotools.csv

import kotools.assert.assertEquals
import kotools.types.string.NotBlankString
import kotlin.test.Test

class CsvPathTest {
    @Test
    fun notBlankString_csv_should_pass_with_a_string_not_suffixed_by_the_CSV_extension() {
        // GIVEN
        val fileName = NotBlankString("file")
        // WHEN
        val result: CsvPath = fileName.csv()
        // THEN
        result.path.value assertEquals "$fileName.csv"
    }

    @Test
    fun notBlankString_csv_should_pass_with_a_string_suffixed_by_the_CSV_extension() {
        // GIVEN
        val fileName = NotBlankString("file.csv")
        // WHEN
        val result: CsvPath = fileName.csv()
        // THEN
        result.path.value assertEquals fileName.value
    }
}
