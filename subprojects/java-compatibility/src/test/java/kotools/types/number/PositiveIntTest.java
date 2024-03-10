package kotools.types.number;

import kotools.types.internal.ErrorMessage;
import kotools.types.internal.ErrorMessageKt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class PositiveIntTest {
    @Nested
    public class Companion {
        @Test
        public void create_should_pass_with_a_Number_that_is_greater_than_zero() {
            final Random random = new Random();
            final Number number = random.nextInt(1, Integer.MAX_VALUE);
            PositiveInt.Companion.create(number);
        }

        @Test
        public void create_should_pass_with_a_Number_that_equals_zero() {
            PositiveInt.Companion.create(0);
        }

        @Test
        public void create_should_fail_with_a_Number_that_is_less_than_zero() {
            final Random random = new Random();
            final Number number = random.nextInt(Integer.MIN_VALUE, 0);
            final IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> PositiveInt.Companion.create(number)
            );
            final ErrorMessage actual = ErrorMessageKt.ErrorMessage(exception);
            final ErrorMessage expected =
                    ErrorMessageKt.shouldBePositive(number);
            Assertions.assertEquals(expected, actual);
        }

        @Test
        public void createOrNull_should_pass_with_a_Number_that_is_greater_than_zero() {
            final Number number = new Random()
                    .nextInt(1, Integer.MAX_VALUE);
            final PositiveInt actual =
                    PositiveInt.Companion.createOrNull(number);
            Assertions.assertNotNull(actual);
        }

        @Test
        public void createOrNull_should_pass_with_a_Number_that_equals_zero() {
            final PositiveInt actual = PositiveInt.Companion.createOrNull(0);
            Assertions.assertNotNull(actual);
        }

        @Test
        public void createOrNull_should_fail_with_a_Number_that_is_less_than_zero() {
            final Number number = new Random()
                    .nextInt(Integer.MIN_VALUE, 0);
            final PositiveInt actual =
                    PositiveInt.Companion.createOrNull(number);
            Assertions.assertNull(actual);
        }
    }
}
