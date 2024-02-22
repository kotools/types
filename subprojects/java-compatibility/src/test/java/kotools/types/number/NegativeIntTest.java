package kotools.types.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class NegativeIntTest {
    @Nested
    public class Companion {
        private static final Random RANDOM = new Random();

        @Test
        public void create_should_pass() {
            final int number = RANDOM.nextInt(Integer.MIN_VALUE, 0);
            final NegativeInt result = NegativeInt.Companion.create(number);
            final int actual = result.toInt();
            Assertions.assertEquals(number, actual);
        }

        @Test
        public void createOrNull_should_pass() {
            final int number = RANDOM.nextInt(Integer.MIN_VALUE, 0);
            final NegativeInt actual =
                    NegativeInt.Companion.createOrNull(number);
            Assertions.assertNotNull(actual);
        }
    }
}
