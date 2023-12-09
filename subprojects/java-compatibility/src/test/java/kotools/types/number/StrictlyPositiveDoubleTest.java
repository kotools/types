package kotools.types.number;

import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Random;

public class StrictlyPositiveDoubleTest {
    @Test
    public void constructor_should_pass() {
        final Random random = new Random();
        final double origin = 0.0,
                bound = Double.MAX_VALUE,
                value = random.nextDouble(origin, bound);
        new StrictlyPositiveDouble(value);
    }

    @Test
    public void equals_should_pass() {
        final Random random = new Random();
        final double origin = 0.0,
                bound = Double.MAX_VALUE,
                value = random.nextDouble(origin, bound);
        final StrictlyPositiveDouble first = new StrictlyPositiveDouble(value),
                second = new StrictlyPositiveDouble(value);
        Assertions.assertEquals(first, second);
    }

    @Test
    public void hashCode_should_pass() {
        final Random random = new Random();
        final double origin = 0.0,
                bound = Double.MAX_VALUE,
                value = random.nextDouble(origin, bound);
        final StrictlyPositiveDouble number = new StrictlyPositiveDouble(value);
        final int actual = number.hashCode(),
                expected = Objects.hash(value);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void toDouble_should_pass() {
        final Random random = new Random();
        final double origin = 0.0,
                bound = Double.MAX_VALUE,
                value = random.nextDouble(origin, bound);
        final StrictlyPositiveDouble number = new StrictlyPositiveDouble(value);
        final double actual = number.toDouble();
        Assertions.assertEquals(value, actual);
    }

    @Test
    public void toString_should_pass() {
        final Random random = new Random();
        final double origin = 0.0,
                bound = Double.MAX_VALUE,
                value = random.nextDouble(origin, bound);
        final StrictlyPositiveDouble number = new StrictlyPositiveDouble(value);
        final String actual = number.toString(),
                expected = Double.toString(value);
        Assertions.assertEquals(expected, actual);
    }

    @Nested
    public class Companion {
        @Test
        public void orNull_should_pass() {
            final Random random = new Random();
            final double origin = 1.0,
                    bound = Double.MAX_VALUE,
                    value = random.nextDouble(origin, bound);
            @Nullable final StrictlyPositiveDouble actual =
                    StrictlyPositiveDouble.Companion.orNull(value);
            Assertions.assertNotNull(actual);
        }
    }
}
