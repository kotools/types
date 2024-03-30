package org.kotools.types.samples

import org.gradle.api.file.Directory

internal interface SampleFile {
    fun saveIn(directory: Directory)
}
