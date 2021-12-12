package com.github.kotools.csvfile.api

import com.github.kotools.csvfile.core.writer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

public suspend fun csvWriter(config: Writer.() -> Unit): Unit? =
    withContext(IO) { writer(config) }

public infix fun CoroutineScope.csvWriterAsync(
    config: Writer.() -> Unit
): Deferred<Unit?> = async(IO) { writer(config) }

public interface Writer : Csv {
    public var header: Set<String>
    public var overwrite: Boolean

    public infix fun rows(config: Rows.() -> Unit)

    public interface Rows : Csv {
        public operator fun Iterable<String>.unaryPlus()
    }
}
