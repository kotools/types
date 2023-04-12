package kotools.types.range

import kotools.types.number.NonZeroInt
import kotools.types.number.StrictlyNegativeInt
import kotools.types.number.StrictlyPositiveInt
import kotools.types.shouldEqual
import kotlin.test.Test
import kotlin.test.assertTrue

class BoundTest {
    @Test
    fun rangeTo_should_return_a_NotEmptyRange_ending_with_the_other_one() {
        val firstBound: Bound<NonZeroInt> = StrictlyNegativeInt.random()
            .toInclusiveBound()
        val otherBound: Bound<NonZeroInt> = StrictlyPositiveInt.random()
            .toExclusiveBound()
        val result: NotEmptyRange<NonZeroInt> = firstBound..otherBound
        result.start shouldEqual firstBound
        result.end shouldEqual otherBound
    }

    @Test
    fun rangeTo_should_return_a_NotEmptyRange_starting_with_the_other_one() {
        val firstBound: Bound<NonZeroInt> = StrictlyPositiveInt.random()
            .toExclusiveBound()
        val otherBound: Bound<NonZeroInt> = StrictlyNegativeInt.random()
            .toInclusiveBound()
        val result: NotEmptyRange<NonZeroInt> = firstBound..otherBound
        result.start shouldEqual otherBound
        result.end shouldEqual firstBound
    }
}

class InclusiveBoundTest {
    @Test
    fun equals_should_pass_with_another_InclusiveBound_having_the_same_value() {
        val firstBound: InclusiveBound<NonZeroInt> = NonZeroInt.random()
            .toInclusiveBound()
        val secondBound: InclusiveBound<NonZeroInt> =
            firstBound.value.toInclusiveBound()
        assertTrue { firstBound == secondBound }
    }

    @Test
    fun equals_should_fail_with_another_InclusiveBound_having_another_value() {
        val firstBound: InclusiveBound<NonZeroInt> = StrictlyPositiveInt
            .random()
            .toInclusiveBound()
        val secondBound: InclusiveBound<NonZeroInt> = StrictlyNegativeInt
            .random()
            .toInclusiveBound()
        assertTrue { firstBound != secondBound }
    }

    @Test
    fun equals_should_fail_with_another_ExclusiveBound() {
        val firstBound: InclusiveBound<NonZeroInt> = NonZeroInt.random()
            .toInclusiveBound()
        val secondBound: ExclusiveBound<NonZeroInt> =
            firstBound.value.toExclusiveBound()
        assertTrue { firstBound != secondBound }
    }

    @Test
    fun toString_should_return_the_string_representation_of_its_value() {
        val bound: InclusiveBound<NonZeroInt> = NonZeroInt.random()
            .toInclusiveBound()
        "$bound" shouldEqual "${bound.value}"
    }

    @Test
    fun toInclusiveBound_should_return_an_InclusiveBound_of_this_value() {
        val value: NonZeroInt = NonZeroInt.random()
        val result: InclusiveBound<NonZeroInt> = value.toInclusiveBound()
        result.value shouldEqual value
    }
}

class ExclusiveBoundTest {
    @Test
    fun equals_should_pass_with_another_ExclusiveBound_having_the_same_value() {
        val firstBound: ExclusiveBound<NonZeroInt> = NonZeroInt.random()
            .toExclusiveBound()
        val secondBound: ExclusiveBound<NonZeroInt> =
            firstBound.value.toExclusiveBound()
        assertTrue { firstBound == secondBound }
    }

    @Test
    fun equals_should_fail_with_another_ExclusiveBound_having_another_value() {
        val firstBound: ExclusiveBound<NonZeroInt> = StrictlyPositiveInt
            .random()
            .toExclusiveBound()
        val secondBound: ExclusiveBound<NonZeroInt> = StrictlyNegativeInt
            .random()
            .toExclusiveBound()
        assertTrue { firstBound != secondBound }
    }

    @Test
    fun equals_should_fail_with_another_InclusiveBound() {
        val firstBound: ExclusiveBound<NonZeroInt> = NonZeroInt.random()
            .toExclusiveBound()
        val secondBound: InclusiveBound<NonZeroInt> =
            firstBound.value.toInclusiveBound()
        assertTrue { firstBound != secondBound }
    }

    @Test
    fun toString_should_return_the_string_representation_of_its_value() {
        val bound: ExclusiveBound<NonZeroInt> = NonZeroInt.random()
            .toExclusiveBound()
        "$bound" shouldEqual "${bound.value}"
    }

    @Test
    fun toExclusiveBound_should_return_an_ExclusiveBound_of_this_value() {
        val value: NonZeroInt = NonZeroInt.random()
        val result: ExclusiveBound<NonZeroInt> = value.toExclusiveBound()
        result.value shouldEqual value
    }
}
