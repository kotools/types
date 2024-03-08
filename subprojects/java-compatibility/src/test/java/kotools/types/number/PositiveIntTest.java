package kotools.types.number;

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
            Assertions.assertDoesNotThrow(
                    () -> PositiveInt.Companion.create(number)
            );
        }

        @Test
        public void create_should_pass_with_a_Number_that_equals_zero() {
            Assertions.assertDoesNotThrow(
                    () -> PositiveInt.Companion.create(0)
            );
        }

        @Test
        public void create_should_fail_with_a_Number_that_is_less_than_zero() {
            final Random random = new Random();
            final Number number = random.nextInt(Integer.MIN_VALUE, 0);
            final IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> PositiveInt.Companion.create(number)
            );
            final String actual = exception.getMessage();
            final String expected = ErrorMessageKt.shouldBePositive(number)
                    .toString();
            Assertions.assertEquals(expected, actual);
        }

        @Test
        public void createOrNull_should_pass() {
            final PositiveInt actual = PositiveInt.Companion.createOrNull(23);
            Assertions.assertNotNull(actual);
        }
    }
}
