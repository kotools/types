package io.github.kotools.csv

import kotlin.reflect.KClass

internal class TypedWriterImpl<T : Any> : Configurable,
    ManagerConfiguration(),
    TypedWriter<T> {
    override var overwrite: Boolean = true
    private var recordsHolder: RecordsHolder<T>? = null

    override fun isValid(): Boolean = file.isNotBlank() && recordsHolder != null

    override fun records(configuration: TypedWriter.Records<T>.() -> Unit) {
        recordsHolder = Factory createOrNull configuration
    }

    private infix fun getWriterConfiguration(dataType: DataType<T>):
            Writer.() -> Unit = {
        file = this@TypedWriterImpl.file
        folder = this@TypedWriterImpl.folder
        overwrite = this@TypedWriterImpl.overwrite
        separator = this@TypedWriterImpl.separator
        header = dataType.properties
        records {
            recordsHolder?.items?.let(dataType::getValuesOf)
                ?.forEach { +it }
        }
    }

    @Throws(CsvException::class)
    private infix fun processWriter(dataType: DataType<T>): Unit =
        WriterImpl process getWriterConfiguration(dataType)

    private infix fun processWriterOrNull(dataType: DataType<T>): Unit? =
        WriterImpl processOrNull getWriterConfiguration(dataType)

    companion object {
        @Throws(CsvException::class)
        inline fun <T : Any> process(
            type: KClass<T>,
            configuration: TypedWriter<T>.() -> Unit
        ): Unit = Factory.create<TypedWriterImpl<T>>(configuration)
            .processWriter(type.toDataType())

        inline fun <T : Any> processOrNull(
            type: KClass<T>,
            configuration: TypedWriter<T>.() -> Unit
        ): Unit? = type.toDataTypeOrNull()?.let {
            Factory.createOrNull<TypedWriterImpl<T>>(configuration)
                ?.processWriterOrNull(it)
        }
    }

    class RecordsHolder<T : Any> : ListHolder<T>(), TypedWriter.Records<T>
}
