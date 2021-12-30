package io.github.kotools.csv

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File
import kotlin.reflect.KClass

internal class ReaderImplementation<T : Any> : ManagerImplementation(),
    Reader {
    private val Reader.csv: CsvReader
        get() = csvReader {
            delimiter = separator.value
            skipEmptyLine = true
        }

    @Throws(CsvException::class)
    private fun read(dataType: DataType<T>): List<T> =
        readOrNull(dataType) ?: fileNotFoundException("$folder$file")

    private infix fun readOrNull(dataType: DataType<T>): List<T>? =
        (readResourceFile() ?: readSystemFile())
            ?.mapNotNull(dataType::createTypeOrNull)

    private fun readResourceFile(): List<Map<String, String>>? = loader
        .getStream("$folder$file")
        ?.let(csv::readAllWithHeader)

    private fun readSystemFile(): List<Map<String, String>>? = loader.baseUrl
        ?.let { File("${it.path}$folder$file") }
        ?.takeIf(File::exists)
        ?.let(csv::readAllWithHeader)

    companion object {
        @Throws(CsvException::class)
        inline fun <T : Any> process(
            type: KClass<T>,
            configuration: Reader.() -> Unit
        ): List<T> = Factory.create<ReaderImplementation<T>>(configuration)
            .read(type.toDataType())

        inline fun <T : Any> processOrNull(
            type: KClass<T>,
            configuration: Reader.() -> Unit
        ): List<T>? {
            val reader: ReaderImplementation<T> =
                (Factory createOrNull configuration) ?: return null
            return type.toDataTypeOrNull()
                ?.let(reader::readOrNull)
        }
    }
}
