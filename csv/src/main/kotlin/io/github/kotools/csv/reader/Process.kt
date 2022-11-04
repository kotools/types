package io.github.kotools.csv.reader

import com.github.doyaaaaaken.kotlincsv.client.CsvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import io.github.kotools.csv.common.*
import io.github.kotools.csv.common.Target
import kotools.types.string.NotBlankString
import kotools.types.string.toNotBlankStringOrNull
import kotlin.reflect.KClass

internal infix fun <T : Any> KClass<T>.processReader(
    configuration: Reader<T>.() -> Unit
): List<T> {
    val dataType: DataType<T> = dataType
    val reader: ReaderImplementation<T> = ReaderImplementation<T>()
        .apply(configuration)
    if (!reader.isValid()) invalidConfigurationError()
    return findTarget(reader.filePath)
        .let(reader::read)
        .map(Map<String, String>::withoutBlankKeys)
        .map(dataType::createType)
        .let { reader.filter?.let(it::filter) ?: it }
        .let { reader.pagination?.let(it::getPage) ?: it }
}

internal infix fun <T : Any> KClass<T>.processReaderOrNull(
    configuration: Reader<T>.() -> Unit
): List<T>? = try {
    processReader(configuration)
} catch (exception: IllegalStateException) {
    null
}

private infix fun <T : Any> List<T>.getPage(pagination: Reader.Pagination):
        List<T> = chunked(pagination.size)
    .getOrElse(pagination.page - 1) { emptyList() }

private fun <V : Any> Map<String, V>.withoutBlankKeys():
        Map<NotBlankString, V> = mapKeys {
    it.key.toNotBlankStringOrNull()
        ?: error("CSV file's header shouldn't contain an empty string")
}

private fun <T : Any> Reader<T>.isValid(): Boolean = file.isNotBlank()

private infix fun <T : Any> Reader<T>.read(target: Target):
        List<Map<String, String>> {
    val csv: CsvReader = csvReader {
        delimiter = separator.value
        skipEmptyLine = true
    }
    return when (target) {
        is Target.File -> csv.readAllWithHeader(target.file)
        is Target.Stream -> csv.readAllWithHeader(target.stream)
    }
}

private fun Reader.Pagination.isValid(): Boolean = page > 0 && size > 1

private class ReaderImplementation<T : Any> : ManagerImplementation(),
    Reader<T> {
    val filter: (T.() -> Boolean)? get() = mutableFilter
    val pagination: Reader.Pagination? get() = mutablePagination

    private var mutablePagination: Reader.Pagination? = null
    private var mutableFilter: (T.() -> Boolean)? = null

    override fun equals(other: Any?): Boolean = this === other

    override fun filter(predicate: T.() -> Boolean) {
        mutableFilter = predicate
    }

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
