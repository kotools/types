package kotools.types.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class NonZeroIntTest {
    @Nested
    public class Companion {
        @Test
        public void createOrNull_should_pass() {
            final NonZeroInt actual = NonZeroInt.Companion.createOrNull(23);
            Assertions.assertNotNull(actual);
        }
    }
}
