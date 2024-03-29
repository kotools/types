package kotools.types.number;

import kotools.types.internal.ErrorMessage;
import kotools.types.internal.ErrorMessageKt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class NegativeIntTest {
    @Nested
    public class Companion {
        @Test
        public void create_should_pass_with_a_Number_that_is_less_than_zero() {
            final int number = new Random()
                    .nextInt(Integer.MIN_VALUE, 0);
            NegativeInt.Companion.create(number);
        }

        @Test
        public void create_should_pass_with_a_Number_that_equals_zero() {
            NegativeInt.Companion.create(0);
        }

        @Test
        public void create_should_fail_with_a_Number_that_is_greater_than_zero() {
            final Number number = new Random()
                    .nextInt(1, Integer.MAX_VALUE);
            final IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> NegativeInt.Companion.create(number)
            );
            final ErrorMessage actual = ErrorMessageKt.ErrorMessage(exception);
            final ErrorMessage expected =
                    ErrorMessageKt.shouldBeNegative(number);
            Assertions.assertEquals(expected, actual);
        }

        @Test
        public void createOrNull_should_pass_with_a_Number_that_is_less_than_zero() {
            final int number = new Random()
                    .nextInt(Integer.MIN_VALUE, 0);
            final NegativeInt actual =
                    NegativeInt.Companion.createOrNull(number);
            Assertions.assertNotNull(actual);
        }

        @Test
        public void createOrNull_should_pass_with_a_Number_that_equals_zero() {
            final NegativeInt actual = NegativeInt.Companion.createOrNull(0);
            Assertions.assertNotNull(actual);
        }

        @Test
        public void createOrNull_should_fail_with_a_Number_that_is_greater_than_zero() {
            final Number number = new Random()
                    .nextInt(1, Integer.MAX_VALUE);
            final NegativeInt actual =
                    NegativeInt.Companion.createOrNull(number);
            Assertions.assertNull(actual);
        }
    }
}
