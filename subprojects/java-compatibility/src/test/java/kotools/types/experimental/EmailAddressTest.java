package kotools.types.experimental;

import kotlin.text.Regex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class EmailAddressTest {
    private static final String TEXT_SAMPLE = "contact@kotools.org";

    @Test
    public void equals_should_pass() {
        final EmailAddress first = EmailAddress.Companion.from(TEXT_SAMPLE);
        Assertions.assertNotNull(first);
        final EmailAddress second = EmailAddress.Companion.from(TEXT_SAMPLE);
        Assertions.assertNotNull(second);
        Assertions.assertEquals(first, second);
    }

    @Test
    public void hashCode_should_pass() {
        final EmailAddress first = EmailAddress.Companion.from(TEXT_SAMPLE);
        Assertions.assertNotNull(first);
        final EmailAddress second = EmailAddress.Companion.from(TEXT_SAMPLE);
        Assertions.assertNotNull(second);
        final int firstHashCode = first.hashCode();
        final int secondHashCode = second.hashCode();
        Assertions.assertEquals(firstHashCode, secondHashCode);
    }

    @Test
    public void toString_should_pass() {
        final String expected = TEXT_SAMPLE;
        final EmailAddress address = EmailAddress.Companion.from(expected);
        Assertions.assertNotNull(address);
        final String actual = address.toString();
        Assertions.assertEquals(expected, actual);
    }

    @Nested
    public class Companion {
        @Test
        public void regex_should_pass() {
            final Regex regex = EmailAddress.regex;
            final String actualPattern = regex.getPattern();
            final String expectedPattern = "^\\S+@\\S+\\.\\S+$";
            Assertions.assertEquals(expectedPattern, actualPattern);
        }

        @Test
        public void from_should_pass() {
            final EmailAddress actual =
                    EmailAddress.Companion.from(TEXT_SAMPLE);
            Assertions.assertNotNull(actual);
        }
    }
}
