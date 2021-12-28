package io.github.kotools.csv

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File
import kotlin.reflect.KClass

@Throws(CsvException::class)
internal inline fun delegateCsvReader(configuration: Reader.() -> Unit):
        List<Map<String, String>> = (ReaderImpl create configuration).process()

@Throws(CsvException::class)
internal inline fun <T : Any> delegateCsvReaderAs(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): List<T> = type.toDataType().let {
    delegateCsvReader(configuration).map(it::createType)
}

internal inline fun delegateCsvReaderOrNull(configuration: Reader.() -> Unit):
        List<Map<String, String>>? =
    (ReaderImpl createOrNull configuration)?.processOrNull()

internal inline fun <T : Any> delegateCsvReaderOrNullAs(
    type: KClass<T>,
    configuration: Reader.() -> Unit
): List<T>? = type.toDataTypeOrNull()?.let {
    delegateCsvReaderOrNull(configuration)?.mapNotNull(it::createTypeOrNull)
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

    @Throws(CsvException::class)
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
