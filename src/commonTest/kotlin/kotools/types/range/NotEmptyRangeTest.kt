package kotools.types.range

import kotools.types.number.*
import kotools.types.shouldEqual
import kotlin.test.Test
import kotlin.test.assertTrue

class NotEmptyRangeTest {
    @Test
    fun equals_should_pass_with_another_NotEmptyRange_having_the_same_bounds() {
        val start: InclusiveBound<AnyInt> = NegativeInt.random()
            .toInclusiveBound()
        val end: InclusiveBound<AnyInt> = PositiveInt.random()
            .toInclusiveBound()
        val firstRange: NotEmptyRange<AnyInt> = start..end
        val secondRange: NotEmptyRange<AnyInt> = start..end
        assertTrue { firstRange == secondRange }
    }

    @Test
    fun equals_should_fail_with_another_NotEmptyRange_starting_with_another_type_of_bound_but_with_the_same_value() {
        val start: InclusiveBound<NonZeroInt> = StrictlyNegativeInt.random()
            .toInclusiveBound()
        val end: InclusiveBound<NonZeroInt> = StrictlyPositiveInt.random()
            .toInclusiveBound()
        val firstRange: NotEmptyRange<NonZeroInt> = start..end
        val secondRange: NotEmptyRange<NonZeroInt> =
            start.value.toExclusiveBound()..end
        assertTrue { firstRange != secondRange }
    }

    @Test
    fun equals_should_fail_with_another_NotEmptyRange_starting_with_the_same_type_of_bound_but_with_another_value() {
        val start: InclusiveBound<NonZeroInt> = StrictlyNegativeInt.random()
            .toInclusiveBound()
        val end: InclusiveBound<NonZeroInt> = StrictlyPositiveInt.random()
            .toInclusiveBound()
        val firstRange: NotEmptyRange<NonZeroInt> = start..end
        val secondRange: NotEmptyRange<NonZeroInt> = StrictlyNegativeInt
            .random()
            .toInclusiveBound<NonZeroInt>()
            .rangeTo(end)
        assertTrue { firstRange != secondRange }
    }

    @Test
    fun equals_should_fail_with_another_NotEmptyRange_ending_with_another_type_of_bound_but_with_the_same_value() {
        val start: InclusiveBound<NonZeroInt> = StrictlyNegativeInt.random()
            .toInclusiveBound()
        val end: InclusiveBound<NonZeroInt> = StrictlyPositiveInt.random()
            .toInclusiveBound()
        val firstRange: NotEmptyRange<NonZeroInt> = start..end
        val secondRange: NotEmptyRange<NonZeroInt> =
            start..end.value.toExclusiveBound()
        assertTrue { firstRange != secondRange }
    }

    @Test
    fun equals_should_fail_with_another_NotEmptyRange_ending_with_the_same_type_of_bound_but_with_another_value() {
        val start: InclusiveBound<NonZeroInt> = StrictlyNegativeInt.random()
            .toInclusiveBound()
        val end: InclusiveBound<NonZeroInt> = StrictlyPositiveInt.random()
            .toInclusiveBound()
        val firstRange: NotEmptyRange<NonZeroInt> = start..end
        val secondRange: NotEmptyRange<NonZeroInt> = start..StrictlyPositiveInt
            .random()
            .toInclusiveBound()
        assertTrue { firstRange != secondRange }
    }

    @Test
    fun toString_should_pass_with_2_InclusiveBounds() {
        val start: InclusiveBound<NonZeroInt> = StrictlyNegativeInt.random()
            .toInclusiveBound()
        val end: InclusiveBound<NonZeroInt> = StrictlyPositiveInt.random()
            .toInclusiveBound()
        val range: NotEmptyRange<NonZeroInt> = start..end
        "$range" shouldEqual "[$start;$end]"
    }

    @Test
    fun toString_should_pass_with_2_ExclusiveBounds() {
        val start: ExclusiveBound<NonZeroInt> = StrictlyNegativeInt.random()
            .toExclusiveBound()
        val end: ExclusiveBound<NonZeroInt> = StrictlyPositiveInt.random()
            .toExclusiveBound()
        val range: NotEmptyRange<NonZeroInt> = start..end
        "$range" shouldEqual "]$start;$end["
    }

    @Test
    fun toString_should_pass_with_an_InclusiveBound_and_ExclusiveBound() {
        val start: InclusiveBound<NonZeroInt> = StrictlyNegativeInt.random()
            .toInclusiveBound()
        val end: ExclusiveBound<NonZeroInt> = StrictlyPositiveInt.random()
            .toExclusiveBound()
        val range: NotEmptyRange<NonZeroInt> = start..end
        "$range" shouldEqual "[$start;$end["
    }

    @Test
    fun contains_should_pass_with_a_value_in_inclusive_bounds() {
        val range: NotEmptyRange<PositiveInt> = PositiveInt.range
        val value: PositiveInt = PositiveInt.random()
        assertTrue { value in range }
    }

    @Test
    fun contains_should_pass_with_a_value_in_exclusive_bounds() {
        val start: ExclusiveBound<NonZeroInt> =
            NonZeroInt.negativeRange.start.value.toExclusiveBound()
        val end: ExclusiveBound<NonZeroInt> = 2.toStrictlyPositiveInt()
            .getOrThrow()
            .toExclusiveBound()
        val range: NotEmptyRange<NonZeroInt> = start..end
        val value: NonZeroInt = 1.toStrictlyPositiveInt()
            .getOrThrow()
        assertTrue { value in range }
    }

    @Test
    fun contains_should_pass_with_a_value_in_inclusive_and_exclusive_bounds() {
        val start: InclusiveBound<NonZeroInt> =
            NonZeroInt.negativeRange.start.value.toInclusiveBound()
        val end: ExclusiveBound<NonZeroInt> =
            NonZeroInt.positiveRange.end.value.toExclusiveBound()
        val range: NotEmptyRange<NonZeroInt> = start..end
        val value: NonZeroInt = NonZeroInt.random()
            .takeIf { it != NonZeroInt.positiveRange.end.value }
            ?: NonZeroInt.negativeRange.start.value
        assertTrue { value in range }
    }

    @Test
    fun contains_should_fail_with_a_value_that_is_not_in_inclusive_bounds() {
        val start: InclusiveBound<PositiveInt> = 1.toStrictlyPositiveInt()
            .getOrThrow()
            .toInclusiveBound()
        val end: InclusiveBound<PositiveInt> =
            PositiveInt.range.end.value.toInclusiveBound()
        val range: NotEmptyRange<PositiveInt> = start..end
        val value: PositiveInt = ZeroInt
        assertTrue { value !in range }
    }

    @Test
    fun contains_should_fail_with_a_value_that_is_not_in_exclusive_bounds() {
        val start: ExclusiveBound<PositiveInt> =
            PositiveInt.range.start.value.toExclusiveBound()
        val end: ExclusiveBound<PositiveInt> =
            PositiveInt.range.end.value.toExclusiveBound()
        val range: NotEmptyRange<PositiveInt> = start..end
        val value: PositiveInt = PositiveInt.range.start.value
        assertTrue { value !in range }
    }

    @Test
    fun contains_should_fail_with_a_value_that_is_not_in_inclusive_and_exclusive_bounds() {
        val start: InclusiveBound<NonZeroInt> =
            NonZeroInt.positiveRange.end.value.toInclusiveBound()
        val end: ExclusiveBound<NonZeroInt> =
            NonZeroInt.negativeRange.start.value.toExclusiveBound()
        val range: NotEmptyRange<NonZeroInt> = start..end
        val value: NonZeroInt = NonZeroInt.negativeRange.start.value
        assertTrue { value !in range }
    }

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
