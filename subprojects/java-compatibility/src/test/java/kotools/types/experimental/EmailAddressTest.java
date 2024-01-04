package kotools.types.experimental;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class EmailAddressTest {
    private static final String TEXT_SAMPLE = "contact@kotools.org";

    @Test
    public void constructor_should_pass() {
        Assertions.assertDoesNotThrow(() -> new EmailAddress(TEXT_SAMPLE));
    }

    @Test
    public void equals_should_pass() {
        final EmailAddress first = new EmailAddress(TEXT_SAMPLE);
        final EmailAddress second = new EmailAddress(TEXT_SAMPLE);
        Assertions.assertEquals(first, second);
    }

    @Test
    public void hashCode_should_pass() {
        final int first = new EmailAddress(TEXT_SAMPLE)
                .hashCode();
        final int second = new EmailAddress(TEXT_SAMPLE)
                .hashCode();
        Assertions.assertEquals(first, second);
    }

    @Test
    public void toString_should_pass() {
        final String expected = TEXT_SAMPLE;
        final EmailAddress address = new EmailAddress(expected);
        final String actual = address.toString();
        Assertions.assertEquals(expected, actual);
    }

    @Nested
    public class Companion {
        @Test
        public void orNull_should_pass() {
            final EmailAddress actual =
                    EmailAddress.Companion.orNull(TEXT_SAMPLE);
            Assertions.assertNotNull(actual);
        }
    }
}
