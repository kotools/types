package io.github.kotools.csv.reader

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import io.github.kotools.csv.common.*
import io.github.kotools.csv.common.Target
import kotlin.reflect.KClass

private val Reader.csv: CsvReader
    get() = csvReader {
        delimiter = separator.value
        skipEmptyLine = true
    }

internal infix fun <T : Any> KClass<T>.processReader(
    configuration: Reader.() -> Unit
): List<T> {
    val dataType: DataType<T> = toDataType()
    val reader: ReaderImplementation = ReaderImplementation()
        .apply(configuration)
    if (!reader.isValid()) invalidConfigurationError()
    return findTarget(reader.filePath)
        .let(reader::read)
        .let { reader.pagination?.let(it::getPage) ?: it }
        .map(dataType::createType)
}

internal infix fun <T : Any> KClass<T>.processReaderOrNull(
    configuration: Reader.() -> Unit
): List<T>? = try {
    processReader(configuration)
} catch (exception: IllegalStateException) {
    null
}

private infix fun <T : Any> List<T>.getPage(pagination: Reader.Pagination):
        List<T> = chunked(pagination.size)
    .getOrElse(pagination.page - 1) { emptyList() }

private fun Reader.isValid(): Boolean = file.isNotBlank()

private infix fun Reader.read(target: Target): List<Map<String, String>> =
    when (target) {
        is Target.File -> csv.readAllWithHeader(target.file)
        is Target.Stream -> csv.readAllWithHeader(target.stream)
    }

private fun Reader.Pagination.isValid(): Boolean = page > 0 && size > 1

private class ReaderImplementation : ManagerImplementation(), Reader {
    val pagination: Reader.Pagination? get() = mutablePagination

    private var mutablePagination: Reader.Pagination? = null

    override fun equals(other: Any?): Boolean = this === other
    override fun hashCode(): Int = System.identityHashCode(this)

    override fun pagination(configuration: Reader.Pagination.() -> Unit) {
        mutablePagination = PaginationImplementation()
            .apply(configuration)
            .takeIf(PaginationImplementation::isValid)
    }

    private class PaginationImplementation : Reader.Pagination {
        override var page: Int = 1
        override var size: Int = 2
    }
}
