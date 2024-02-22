package kotools.types.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class NegativeIntTest {
    @Nested
    public class Companion {
        @Test
        public void createOrNull_should_pass() {
            final int number = new Random().nextInt(Integer.MIN_VALUE, 0);
            final NegativeInt actual =
                    NegativeInt.Companion.createOrNull(number);
            Assertions.assertNotNull(actual);
        }
    }
}
