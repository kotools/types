package io.github.kotools.csv.reader

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import io.github.kotools.csv.common.DataType
import io.github.kotools.csv.common.ManagerImplementation
import io.github.kotools.csv.common.Target
import io.github.kotools.csv.common.findTarget
import io.github.kotools.csv.common.toDataType
import kotlin.reflect.KClass

private val Reader.csv: CsvReader
    get() = csvReader {
        delimiter = separator.value
        skipEmptyLine = true
    }
private val Reader.filePath: String get() = "$folder$file"

internal infix fun <T : Any> KClass<T>.processReader(
    configuration: Reader.() -> Unit
): List<T> {
    val dataType: DataType<T> = toDataType()
    val reader: Reader = ReaderImplementation()
        .apply(configuration)
    if (!reader.isValid()) error("Given configuration is invalid")
    val target: Target = findTarget(reader.filePath)
    return reader.read(target)
        .map(dataType::createType)
}

internal infix fun <T : Any> KClass<T>.processReaderOrNull(
    configuration: Reader.() -> Unit
): List<T>? = try {
    processReader(configuration)
} catch (exception: IllegalStateException) {
    null
}

private fun Reader.isValid(): Boolean = file.isNotBlank()

private infix fun Reader.read(target: Target): List<Map<String, String>> =
    when (target) {
        is Target.File -> csv.readAllWithHeader(target.file)
        is Target.Stream -> csv.readAllWithHeader(target.stream)
    }

private class ReaderImplementation : ManagerImplementation(), Reader {
    override fun equals(other: Any?): Boolean = this === other
    override fun hashCode(): Int = System.identityHashCode(this)
}
