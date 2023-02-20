package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.types.Package
import kotools.types.shouldEqual
import kotlin.random.Random
import kotlin.test.Test

class AnyIntTest {
    @Test
    fun int_plus_should_pass_with_an_AnyInt() {
        val x: Int = Random.nextInt()
        val y: AnyInt = NonZeroInt.random()
        val result: Int = x + y
        result shouldEqual x + y.toInt()
    }

    @Test
    fun plus_should_pass_with_an_Int() {
        val x: AnyInt = PositiveInt.random()
        val y: Int = Random.nextInt()
        val result: Int = x + y
        result shouldEqual x.toInt() + y
    }

    @Test
    fun plus_should_pass_with_an_AnyInt() {
        val x: AnyInt = PositiveInt.random()
        val y: AnyInt = NegativeInt.random()
        val result: Int = x + y
        result shouldEqual x.toInt() + y.toInt()
    }

    @Test
    fun int_minus_should_pass_with_an_AnyInt() {
        val x: Int = Random.nextInt()
        val y: AnyInt = NonZeroInt.random()
        val result: Int = x - y
        result shouldEqual x - y.toInt()
    }

    @Test
    fun minus_should_pass_with_an_Int() {
        val x: AnyInt = PositiveInt.random()
        val y: Int = Random.nextInt()
        val result: Int = x - y
        result shouldEqual x.toInt() - y
    }

    @Test
    fun minus_should_pass_with_an_AnyInt() {
        val x: AnyInt = PositiveInt.random()
        val y: AnyInt = NegativeInt.random()
        val result: Int = x - y
        result shouldEqual x.toInt() - y.toInt()
    }

    @Test
    fun int_times_should_pass_with_an_AnyInt() {
        val x: Int = Random.nextInt()
        val y: AnyInt = NonZeroInt.random()
        val result: Int = x * y
        result shouldEqual x * y.toInt()
    }

    @Test
    fun times_should_pass_with_an_Int() {
        val x: AnyInt = PositiveInt.random()
        val y: Int = Random.nextInt()
        val result: Int = x * y
        result shouldEqual x.toInt() * y
    }

    @Test
    fun times_should_pass_with_an_AnyInt() {
        val x: AnyInt = PositiveInt.random()
        val y: AnyInt = NegativeInt.random()
        val result: Int = x * y
        result shouldEqual x.toInt() * y.toInt()
    }

    @Test
    fun div_should_pass_with_a_NonZeroInt() {
        val x: AnyInt = PositiveInt.random()
        val y: NonZeroInt = NonZeroInt.random()
        val result: Int = x / y
        result shouldEqual x.toInt() / y.toInt()
    }
}

class AnyIntSerializerTest {
    private val serializer: KSerializer<AnyInt> = AnyIntSerializerImplementation

    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_AnyInt_as_serial_name(): Unit =
        serializer.descriptor.serialName shouldEqual "${Package.number}.AnyInt"

    @Test
    fun serialize_should_behave_like_an_Int() {
        val x: AnyInt = StrictlyPositiveInt.random()
        val result: String = Json.encodeToString(serializer, x)
        result shouldEqual Json.encodeToString(x.toInt())
    }

    @Test
    fun deserialize_should_pass_with_an_Int() {
        val value: Int = Random.nextInt()
        val encoded: String = Json.encodeToString(value)
        val result: AnyInt = Json.decodeFromString(serializer, encoded)
        result.toInt() shouldEqual value
    }
}
