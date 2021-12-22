package io.github.kotools.csv

import java.io.InputStream
import java.net.URL

internal val loader: ClassLoader get() = ClassLoader.getSystemClassLoader()
internal val ClassLoader.baseUrl: URL? get() = getResource("")

internal infix fun ClassLoader.getStream(path: String): InputStream? =
    getResourceAsStream(path)
