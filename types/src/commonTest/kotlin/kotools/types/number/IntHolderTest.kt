package kotools.types.number

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.types.core.RandomValueHolder
import kotlin.test.Ignore
import kotlin.test.Test

class IntHolderTest : RandomValueHolder {
    private val randomHolders: Set<IntHolder> = setOf(
        randomNonZeroInt(),
        PositiveInt.random(),
        StrictlyPositiveInt.random(),
        NegativeInt.random(),
        StrictlyNegativeInt.random()
    )

    @Test
    fun compareTo_should_pass_when_comparing_an_Int_with_an_IntHolder() {
        val x: Int = randomInt
        val y: IntHolder = randomHolders.random()
        val result: Int = x.compareTo(y)
        result assertEquals x.compareTo(y.value)
    }

    @Test
    fun compareTo_should_pass_with_an_Int() {
        val x: IntHolder = randomHolders.random()
        val y: Int = randomInt
        val result: Int = x.compareTo(y)
        result assertEquals x.value.compareTo(y)
    }

    @Test
    fun compareTo_should_pass_with_an_IntHolder() {
        val x: IntHolder = randomHolders.random()
        val y: IntHolder = randomHolders.random()
        val result: Int = x.compareTo(y)
        result assertEquals x.value.compareTo(y.value)
    }

    @Test
    fun plus_should_pass_when_adding_an_IntHolder_to_an_Int() {
        val x: Int = randomInt
        val y: IntHolder = randomHolders.random()
        val result: Int = x + y
        result assertEquals x + y.value
    }

    @Test
    fun plus_should_pass_with_an_Int() {
        val x: IntHolder = randomHolders.random()
        val y: Int = randomInt
        val result: Int = x + y
        result assertEquals x.value + y
    }

    @Test
    fun plus_should_pass_with_an_IntHolder() {
        val x: IntHolder = randomHolders.random()
        val y: IntHolder = randomHolders.random()
        val result: Int = x + y
        result assertEquals x.value + y.value
    }

    @Test
    fun minus_should_pass_when_subtracting_an_IntHolder_from_an_Int() {
        val x: Int = randomInt
        val y: IntHolder = randomHolders.random()
        val result: Int = x - y
        result assertEquals x - y.value
    }

    @Test
    fun minus_should_pass_with_an_Int() {
        val x: IntHolder = randomHolders.random()
        val y: Int = randomInt
        val result: Int = x - y
        result assertEquals x.value - y
    }

    @Test
    fun minus_should_pass_with_an_IntHolder() {
        val x: IntHolder = randomHolders.random()
        val y: IntHolder = randomHolders.random()
        val result: Int = x - y
        result assertEquals x.value - y.value
    }

    @Test
    fun times_should_pass_when_multiplying_an_Int_by_an_IntHolder() {
        val x: Int = randomInt
        val y: IntHolder = randomHolders.random()
        val result: Int = x * y
        result assertEquals x * y.value
    }

    @Test
    fun times_should_pass_with_an_Int() {
        val x: IntHolder = randomHolders.random()
        val y: Int = randomInt
        val result: Int = x * y
        result assertEquals x.value * y
    }

    @Test
    fun times_should_pass_with_an_IntHolder() {
        val x: IntHolder = randomHolders.random()
        val y: IntHolder = randomHolders.random()
        val result: Int = x * y
        result assertEquals x.value * y.value
    }

    @Test
    fun div_should_pass_with_a_NonZeroInt() {
        val x: IntHolder = randomHolders.random()
        val y: NonZeroInt = randomNonZeroInt()
        val result: Int = x / y
        result assertEquals x.value / y.value
    }
}

@Ignore // Because we test the implementations of the class IntHolderSerializer.
sealed class IntHolderSerializerTest<T : IntHolder>(
    private val serializer: IntHolder.Serializer<T>,
    private val generator: () -> T
) {
    @Test
    fun serialization_should_pass(): Unit = generator().let {
        val result: String = Json.encodeToString(serializer, it)
        result assertEquals Json.encodeToString(it.value)
    }

    @Test
    fun deserialization_should_pass(): Unit = generator().let {
        val encoded: String = Json.encodeToString(serializer, it)
        val result: T = Json.decodeFromString(serializer, encoded)
        result.value assertEquals it.value
    }
}
