package kotools.types.range

import kotools.types.number.*
import kotools.types.shouldEqual
import kotlin.test.Test
import kotlin.test.assertTrue

class NotEmptyRangeTest {
    @Test
    fun contains_should_pass_with_a_value_in_inclusive_bounds() {
        val start: InclusiveBound<NonZeroInt> =
            NonZeroInt.min.toInclusiveBound()
        val end: InclusiveBound<NonZeroInt> = NonZeroInt.max.toInclusiveBound()
        val range: NotEmptyRange<NonZeroInt> = start..end
        val value: NonZeroInt = NonZeroInt.random()
        assertTrue { value in range }
    }

    @Test
    fun contains_should_pass_with_a_value_in_exclusive_bounds() {
        val start: ExclusiveBound<NonZeroInt> =
            NonZeroInt.min.toExclusiveBound()
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
            NonZeroInt.min.toInclusiveBound()
        val end: ExclusiveBound<NonZeroInt> = NonZeroInt.max.toExclusiveBound()
        val range: NotEmptyRange<NonZeroInt> = start..end
        val value: NonZeroInt = (NonZeroInt.random()
            .takeIf { it != NonZeroInt.max }
            ?: NonZeroInt.min)
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
            NonZeroInt.max.toInclusiveBound()
        val end: ExclusiveBound<NonZeroInt> = NonZeroInt.min.toExclusiveBound()
        val range: NotEmptyRange<NonZeroInt> = start..end
        val value: NonZeroInt = NonZeroInt.min
        assertTrue { value !in range }
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
