package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import kotlin.test.assertEquals

internal inline fun <reified T> SerializersModule.assertIncludes(
    expected: KSerializer<T>
) {
    val actual: KSerializer<T> = this.serializer<T>()
    val message = "Serializers module includes '$expected'."
    assertEquals(expected, actual, message)
}
