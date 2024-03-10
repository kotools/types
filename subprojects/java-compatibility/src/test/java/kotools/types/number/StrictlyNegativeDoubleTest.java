package kotools.types.number;

import kotools.types.internal.ErrorMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class StrictlyNegativeDoubleTest {
    @Nested
    class Companion {
        @Test
        public void create_should_pass_with_a_Number_that_is_less_than_zero() {
            final Random random = new Random();
            final Number number = random.nextInt(Integer.MIN_VALUE, 0);
            StrictlyNegativeDouble.Companion.create(number);
        }

        @Test
        public void create_should_fail_with_a_Number_that_equals_zero() {
            final Number number = 0;
            final IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> StrictlyNegativeDouble.Companion.create(number)
            );
            final String actual = exception.getMessage();
            final String expected = ErrorMessage.Companion
                    .shouldBeLessThanZero(number)
                    .toString();
            Assertions.assertEquals(expected, actual);
        }

        @Test
        public void create_should_fail_with_a_Number_that_is_greater_than_zero() {
            final Random random = new Random();
            final Number number = random.nextInt(1, Integer.MAX_VALUE);
            final IllegalArgumentException exception = Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> StrictlyNegativeDouble.Companion.create(number)
            );
            final String actual = exception.getMessage();
            final String expected = ErrorMessage.Companion
                    .shouldBeLessThanZero(number)
                    .toString();
            Assertions.assertEquals(expected, actual);
        }

        @Test
        public void createOrNull_should_pass_with_a_Number_that_is_less_than_zero() {
            final Random random = new Random();
            final Number number = random.nextInt(Integer.MIN_VALUE, 0);
            final StrictlyNegativeDouble actual =
                    StrictlyNegativeDouble.Companion.createOrNull(number);
            final String typeName =
                    StrictlyNegativeDouble.class.getSimpleName();
            final String message = "Converting %s to %s should pass"
                    .formatted(number, typeName);
            Assertions.assertNotNull(actual, message);
        }

        @Test
        public void createOrNull_should_fail_with_a_Number_that_equals_zero() {
            final Number number = 0;
            final StrictlyNegativeDouble actual =
                    StrictlyNegativeDouble.Companion.createOrNull(number);
            final String typeName =
                    StrictlyNegativeDouble.class.getSimpleName();
            final String message = "Converting %s to %s should fail"
                    .formatted(number, typeName);
            Assertions.assertNull(actual, message);
        }

        @Test
        public void createOrNull_should_fail_with_a_Number_that_is_greater_than_zero() {
            final Random random = new Random();
            final Number number = random.nextInt(1, Integer.MAX_VALUE);
            final StrictlyNegativeDouble actual =
                    StrictlyNegativeDouble.Companion.createOrNull(number);
            final String typeName =
                    StrictlyNegativeDouble.class.getSimpleName();
            final String message = "Converting %s to %s should fail"
                    .formatted(number, typeName);
            Assertions.assertNull(actual, message);
        }
    }

    @Test
    public void structural_equality_should_pass_with_another_StrictlyNegativeDouble_having_the_same_value() {
        final Random random = new Random();
        final Number number = random.nextInt(Integer.MIN_VALUE, 0);
        final StrictlyNegativeDouble first =
                StrictlyNegativeDouble.Companion.create(number);
        final StrictlyNegativeDouble second =
                StrictlyNegativeDouble.Companion.create(number);
        Assertions.assertTrue(() -> first.equals(second));
        final int firstHashCode = first.hashCode();
        final int secondHashCode = second.hashCode();
        Assertions.assertEquals(firstHashCode, secondHashCode);
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void structural_equality_should_fail_with_another_object_that_is_not_a_StrictlyNegativeDouble() {
        final Random random = new Random();
        final Number number = random.nextInt(Integer.MIN_VALUE, 0);
        final StrictlyNegativeDouble first =
                StrictlyNegativeDouble.Companion.create(number);
        Assertions.assertFalse(() -> first.equals(number));
        final int firstHashCode = first.hashCode();
        final int secondHashCode = number.hashCode();
        Assertions.assertNotEquals(firstHashCode, secondHashCode);
    }

    @Test
    public void structural_equality_should_fail_with_another_StrictlyNegativeDouble_having_another_value() {
        final Random random = new Random();
        final Number firstValue = random.nextInt(Integer.MIN_VALUE, 0);
        final StrictlyNegativeDouble first =
                StrictlyNegativeDouble.Companion.create(firstValue);
        final Number secondValue = random.nextInt(Integer.MIN_VALUE, 0);
        final StrictlyNegativeDouble second =
                StrictlyNegativeDouble.Companion.create(secondValue);
        Assertions.assertFalse(() -> first.equals(second));
        final int firstHashCode = first.hashCode();
        final int secondHashCode = second.hashCode();
        Assertions.assertNotEquals(firstHashCode, secondHashCode);
    }

    @Test
    public void toDouble_should_pass() {
        final Random random = new Random();
        final Number value = random.nextInt(Integer.MIN_VALUE, 0);
        final StrictlyNegativeDouble number =
                StrictlyNegativeDouble.Companion.create(value);
        final double actual = number.toDouble();
        final double expected = value.doubleValue();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void toString_should_pass() {
        final Random random = new Random();
        final Number value = random.nextInt(Integer.MIN_VALUE, 0);
        final StrictlyNegativeDouble number =
                StrictlyNegativeDouble.Companion.create(value);
        final String actual = number.toString();
        final double doubleValue = value.doubleValue();
        final String expected = Double.toString(doubleValue);
        Assertions.assertEquals(expected, actual);
    }
}
