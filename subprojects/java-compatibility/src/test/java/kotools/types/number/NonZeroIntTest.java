package kotools.types.number;

import kotools.types.internal.ErrorMessage;
import kotools.types.internal.ErrorMessageKt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class NonZeroIntTest {
    @Nested
    public class Companion {
        @Test
        public void create_should_pass_with_a_Number_other_than_zero() {
            final Random random = new Random();
            int value;
            do {
                value = random.nextInt();
            } while (value == 0);
            final Number number = value;
            Assertions.assertDoesNotThrow(
                    () -> NonZeroInt.Companion.create(number)
            );
        }

        @Test
        public void create_should_fail_with_a_Number_that_equals_zero() {
            final IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> NonZeroInt.Companion.create(0)
            );
            final ErrorMessage actual = ErrorMessageKt.ErrorMessage(exception);
            final ErrorMessage expected =
                    ErrorMessage.Companion.getZeroNumber();
            Assertions.assertEquals(expected, actual);
        }

        @Test
        public void createOrNull_should_pass_with_a_Number_other_than_zero() {
            final Random random = new Random();
            int value;
            do {
                value = random.nextInt();
            } while (value == 0);
            final Number number = value;
            final NonZeroInt actual = NonZeroInt.Companion.createOrNull(number);
            Assertions.assertNotNull(actual);
        }

        @Test
        public void createOrNull_should_fail_with_a_Number_that_equals_zero() {
            final NonZeroInt actual = NonZeroInt.Companion.createOrNull(0);
            Assertions.assertNull(actual);
        }
    }
}
