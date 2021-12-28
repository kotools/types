package io.github.kotools.csv

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File
import kotlin.reflect.KClass

@Throws(CsvConfigurationException::class)
internal inline fun delegateCsvReader(configuration: Reader.() -> Unit):
        List<Map<String, String>> = (ReaderImpl create configuration).process()

@Throws(CsvConfigurationException::class)
internal inline fun <T : Any> delegateCsvReaderAs(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): List<T> {
    val t: DataType<T> = type.toDataTypeOrNull() ?: invalidTypeException(type)
    return delegateCsvReader(configuration).mapNotNull(t::createTypeOrNullFrom)
}

internal inline fun delegateCsvReaderOrNull(configuration: Reader.() -> Unit):
        List<Map<String, String>>? =
    (ReaderImpl create configuration).processOrNull()

internal inline fun <T : Any> delegateCsvReaderOrNullAs(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): List<T>? {
    val dataType: DataType<T> = type.toDataTypeOrNull() ?: return null
    return delegateCsvReaderOrNull(configuration)
        ?.mapNotNull(dataType::createTypeOrNullFrom)
}

internal class ReaderImpl :
    ManagerImpl(),
    Processable<List<Map<String, String>>>,
    Reader {
    private val csv
        get() = csvReader {
            delimiter = separator.value
            skipEmptyLine = true
        }

    @Throws(CsvConfigurationException::class)
    override fun process(): List<Map<String, String>> {
        if (isInvalid()) invalidConfigurationException()
        return readResource()
            ?: readSystemFile()
            ?: fileNotFoundException("$folder$file")
    }

    private fun readResource(): List<Map<String, String>>? =
        (loader getStream "$folder$file")?.let(csv::readAllWithHeader)

    private fun readSystemFile(): List<Map<String, String>>? = loader.baseUrl
        ?.let { File("${it.path}$folder$file") }
        ?.takeIf(File::exists)
        ?.let(csv::readAllWithHeader)

    companion object : Factory<ReaderImpl>
}
