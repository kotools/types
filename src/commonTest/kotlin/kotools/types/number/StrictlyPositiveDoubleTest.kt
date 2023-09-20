package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.experimental.ExperimentalNumberApi
import kotools.types.shouldBeNotNull
import kotools.types.shouldBeNull
import kotools.types.shouldEqual
import kotools.types.shouldHaveAMessage
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

@ExperimentalNumberApi
class StrictlyPositiveDoubleTest {
    @Test
    fun toStrictlyPositiveDouble_should_pass_with_a_strictly_positive_Number() {
        val value: Number =
            Random.nextDouble(from = 0.1, until = Double.MAX_VALUE)
        val result: Result<StrictlyPositiveDouble> =
            value.toStrictlyPositiveDouble()
        result.getOrThrow().toDouble() shouldEqual value
    }

    @Test
    fun toStrictlyPositiveDouble_should_fail_with_a_negative_Number() {
        val value: Number = Random
            .nextDouble(from = 0.1, until = Double.MAX_VALUE)
            .unaryMinus()
        val result: Result<StrictlyPositiveDouble> =
            value.toStrictlyPositiveDouble()
        assertFailsWith<IllegalArgumentException> { result.getOrThrow() }
            .shouldHaveAMessage()
    }

    @Test
    fun toStrictlyPositiveDoubleOrNull_should_pass_with_a_strictly_positive_Double() {
        val value: Number =
            Random.nextDouble(from = 0.1, until = Double.MAX_VALUE)
        value.toStrictlyPositiveDoubleOrNull()
            .shouldBeNotNull()
            .toDouble()
            .shouldEqual(value)
    }

    @Test
    fun toStrictlyPositiveDoubleOrNull_should_fail_with_a_negative_Double(): Unit =
        Random.nextDouble(from = 0.1, until = Double.MAX_VALUE)
            .unaryMinus()
            .toStrictlyPositiveDoubleOrNull()
            .shouldBeNull()

    @Test
    fun toStrictlyPositiveDoubleOrThrow_should_pass_with_a_strictly_positive_Double() {
        val value: Number =
            Random.nextDouble(from = 0.1, until = Double.MAX_VALUE)
        value.toStrictlyPositiveDoubleOrThrow()
            .toDouble()
            .shouldEqual(value)
    }

    @Test
    fun toStrictlyPositiveDoubleOrThrow_should_fail_with_a_negative_Double() {
        val value: Number = Random
            .nextDouble(from = 0.1, until = Double.MAX_VALUE)
            .unaryMinus()
        assertFailsWith<IllegalArgumentException> {
            value.toStrictlyPositiveDoubleOrThrow()
        }.shouldHaveAMessage()
    }

    @Test
    fun compareTo_should_return_zero_with_the_same_StrictlyPositiveDouble() {
        val x: StrictlyPositiveDouble = Random.nextDouble()
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val y: StrictlyPositiveDouble = x.toDouble()
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val result: Int = x.compareTo(y)
        result shouldEqual 0
    }

    @Test
    fun compareTo_should_return_a_negative_Int_with_a_greater_StrictlyPositiveDouble() {
        val x: StrictlyPositiveDouble = Random.nextDouble(until = 0.5)
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val y: StrictlyPositiveDouble = Random
            .nextDouble(from = 0.6, until = Double.MAX_VALUE)
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val result: Int = x.compareTo(y)
        assertTrue(result < 0)
    }

    @Test
    fun compareTo_should_return_a_positive_Int_with_a_lower_StrictlyPositiveDouble() {
        val x: StrictlyPositiveDouble = Random
            .nextDouble(from = 0.6, until = Double.MAX_VALUE)
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val y: StrictlyPositiveDouble = Random.nextDouble(until = 0.5)
            .toStrictlyPositiveDouble()
            .getOrThrow()
        val result: Int = x.compareTo(y)
        assertTrue(result > 0)
    }

    @Test
    fun toString_should_return_the_string_representation_of_its_value() {
        val value: Double = Random.nextDouble()
        value.toStrictlyPositiveDouble()
            .getOrThrow()
            .toString()
            .shouldEqual("$value")
    }
}

@ExperimentalNumberApi
class StrictlyPositiveDoubleSerializerTest {
    private val serializer: KSerializer<StrictlyPositiveDouble> =
        StrictlyPositiveDoubleSerializer

    @ExperimentalSerializationApi
    @Test
    fun descriptor_serial_name_should_be_the_qualified_name_of_StrictlyPositiveDouble() {
        // GIVEN
        val serializer: KSerializer<StrictlyPositiveDouble> =
            StrictlyPositiveDouble.serializer()
        // WHEN
        val actual: String = serializer.descriptor.serialName
        // THEN
        val expected = "${Package.number}.StrictlyPositiveDouble"
        assertEquals(expected, actual)
    }

    @ExperimentalSerializationApi
    @Test
    fun descriptor_kind_should_be_a_PrimitiveKind_Double() {
        // GIVEN
        val serializer: KSerializer<StrictlyPositiveDouble> =
            StrictlyPositiveDouble.serializer()
        // WHEN
        val actual: SerialKind = serializer.descriptor.kind
        // THEN
        assertEquals(expected = PrimitiveKind.DOUBLE, actual)
    }

    @Test
    fun serialize_should_behave_like_a_Double() {
        val value: Double =
            Random.nextDouble(from = 0.1, until = Double.MAX_VALUE)
        val result: String = value.toStrictlyPositiveDouble()
            .getOrThrow()
            .let { Json.encodeToString(serializer, it) }
        result shouldEqual Json.encodeToString(value)
    }

    @Test
    fun deserialize_should_pass_with_a_strictly_positive_Double() {
        val value: Double =
            Random.nextDouble(from = 0.1, until = Double.MAX_VALUE)
        Json.decodeFromString(serializer, "$value")
            .toDouble()
            .shouldEqual(value)
    }

    @Test
    fun deserialize_should_pass_with_a_negative_Double() {
        val value: Double = -Random.nextDouble()
        assertFailsWith<SerializationException> {
            Json.decodeFromString(serializer, "$value")
        }.shouldHaveAMessage()
    }
}
