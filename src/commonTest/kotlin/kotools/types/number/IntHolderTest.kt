package kotools.types.number

import kotools.assert.assertEquals
import kotools.types.core.RandomValueHolder
import kotools.types.string.NotBlankString
import kotlin.test.Test

private val intHolders: List<IntHolder> = listOf(
    NonZeroInt.random,
    PositiveInt.random,
    StrictlyPositiveInt.random,
    NegativeInt.random,
    StrictlyNegativeInt.random
)

private val nonZeroIntHolders: List<NonZeroIntHolder> = listOf(
    NonZeroInt.random,
    StrictlyPositiveInt.random,
    StrictlyNegativeInt.random
)

class IntHolderTest : RandomValueHolder {
    // ---------- Binary operations ----------

    @Test
    fun compareTo_should_pass_when_comparing_an_Int_with_an_IntHolder() {
        val x: Int = randomInt
        val y: IntHolder = intHolders.random()
        val result: Int = x.compareTo(y)
        result assertEquals x.compareTo(y.value)
    }

    @Test
    fun compareTo_should_pass_with_an_Int() {
        val x: IntHolder = intHolders.random()
        val y: Int = randomInt
        val result: Int = x.compareTo(y)
        result assertEquals x.value.compareTo(y)
    }

    @Test
    fun compareTo_should_pass_with_another_IntHolder() {
        val x: IntHolder = intHolders.random()
        val y: IntHolder = intHolders.random()
        val result: Int = x.compareTo(y)
        result assertEquals x.value.compareTo(y.value)
    }

    @Test
    fun plus_should_return_an_Int_when_adding_an_IntHolder_to_an_Int() {
        val x: Int = randomInt
        val y: IntHolder = intHolders.random()
        val result: Int = x + y
        result assertEquals x + y.value
    }

    @Test
    fun plus_should_return_an_Int_with_an_Int() {
        val x: IntHolder = intHolders.random()
        val y: Int = randomInt
        val result: Int = x + y
        result assertEquals x.value + y
    }

    @Test
    fun plus_should_return_an_Int_with_another_IntHolder() {
        val x: IntHolder = intHolders.random()
        val y: IntHolder = intHolders.random()
        val result: Int = x + y
        result assertEquals x.value + y.value
    }

    @Test
    fun minus_should_return_an_Int_when_subtracting_an_IntHolder_from_an_Int() {
        val x: Int = randomInt
        val y: IntHolder = intHolders.random()
        val result: Int = x - y
        result assertEquals x - y.value
    }

    @Test
    fun minus_should_return_an_Int_with_an_Int() {
        val x: IntHolder = intHolders.random()
        val y: Int = randomInt
        val result: Int = x - y
        result assertEquals x.value - y
    }

    @Test
    fun minus_should_return_an_Int_with_an_IntHolder() {
        val x: IntHolder = intHolders.random()
        val y: IntHolder = intHolders.random()
        val result: Int = x - y
        result assertEquals x.value - y.value
    }

    @Test
    fun times_should_return_an_Int_when_multiplying_an_Int_by_an_IntHolder() {
        val x: Int = randomInt
        val y: IntHolder = intHolders.random()
        val result: Int = x * y
        result assertEquals x * y.value
    }

    @Test
    fun times_should_return_an_Int_with_an_Int() {
        val x: IntHolder = intHolders.random()
        val y: Int = randomInt
        val result: Int = x * y
        result assertEquals x.value * y
    }

    @Test
    fun times_should_return_an_Int_with_an_IntHolder() {
        val x: IntHolder = intHolders.random()
        val y: IntHolder = intHolders.random()
        val result: Int = x * y
        result assertEquals x.value * y.value
    }

    @Test
    fun div_should_return_an_Int_with_a_NonZeroIntHolder() {
        val x: IntHolder = intHolders.random()
        val y: NonZeroIntHolder = nonZeroIntHolders.random()
        val result: Int = x / y
        result assertEquals x.value / y.value
    }

    // ---------- Conversions ----------

    @Test
    fun toNotBlankString_should_pass(): Unit = intHolders.forEach {
        val result: NotBlankString = it.toNotBlankString()
        result.value assertEquals it.value.toString()
    }
}

class NonZeroIntHolderTest : RandomValueHolder {
    // ---------- Binary operations ----------

    @Test
    fun times_should_return_a_NonZeroInt() {
        val x: NonZeroIntHolder = nonZeroIntHolders.random()
        val y: NonZeroIntHolder = nonZeroIntHolders.random()
        val result: NonZeroInt = x * y
        result.value assertEquals x.value * y.value
    }

    @Test
    fun div_should_return_an_Int_when_dividing_an_Int_by_a_NonZeroIntHolder() {
        val x: Int = randomInt
        val y: NonZeroIntHolder = nonZeroIntHolders.random()
        val result: Int = x / y
        result assertEquals x / y.value
    }
}

class PositiveIntHolderTest {
    private val holders: List<PositiveIntHolder> =
        listOf(PositiveInt.random, StrictlyPositiveInt.random)

    @Test
    fun div_should_return_a_PositiveInt_with_a_StrictlyPositiveInt() {
        val x: PositiveIntHolder = holders.random()
        val y: StrictlyPositiveInt = StrictlyPositiveInt.random
        val result: PositiveInt = x / y
        result.value assertEquals x.value / y.value
    }

    @Test
    fun div_should_return_a_NegativeInt_with_a_StrictlyNegativeInt() {
        val x: PositiveIntHolder = holders.random()
        val y: StrictlyNegativeInt = StrictlyNegativeInt.random
        val result: NegativeInt = x / y
        result.value assertEquals x.value / y.value
    }
}

class NegativeIntHolderTest {
    private val holders: List<NegativeIntHolder> =
        listOf(NegativeInt.random, StrictlyNegativeInt.random)

    @Test
    fun div_should_return_a_NegativeInt_with_a_StrictlyPositiveInt() {
        val x: NegativeIntHolder = holders.random()
        val y: StrictlyPositiveInt = StrictlyPositiveInt.random
        val result: NegativeInt = x / y
        result.value assertEquals x.value / y.value
    }

    @Test
    fun div_should_return_a_PositiveInt_with_a_StrictlyNegativeInt() {
        val x: NegativeIntHolder = holders.random()
        val y: StrictlyNegativeInt = StrictlyNegativeInt.random
        val result: PositiveInt = x / y
        result.value assertEquals x.value / y.value
    }
}
