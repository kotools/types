package org.kotools.types.kotlinx.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import kotools.types.internal.simpleNameOf
import kotlin.test.assertEquals

internal fun <T> KSerializer<T>.assertDescriptor(kind: PrimitiveKind) {
    val actual: SerialDescriptor = this.descriptor
    val expected: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = simpleNameOf(this::class),
        kind = kind
    )
    assertEquals(expected, actual)
}

internal inline fun <reified T> SerializersModule.assertIncludes(
    expected: KSerializer<T>
) {
    val actual: KSerializer<T> = this.serializer<T>()
    val message = "Serializers module includes '$expected'."
    assertEquals(expected, actual, message)
}
