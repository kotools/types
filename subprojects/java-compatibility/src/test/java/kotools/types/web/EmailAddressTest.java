package kotools.types.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class EmailAddressTest {
    private static final String TEXT_SAMPLE = "contact@kotools.org";

    @Nested
    public class Companion {
        @Test
        public void create_should_pass() {
            EmailAddress.Companion.create(TEXT_SAMPLE);
        }

        @Test
        public void createOrNull_should_pass() {
            final EmailAddress actual =
                    EmailAddress.Companion.createOrNull(TEXT_SAMPLE);
            Assertions.assertNotNull(actual);
        }
    }

    @Test
    public void equals_should_pass() {
        final EmailAddress first =
                EmailAddress.Companion.createOrNull(TEXT_SAMPLE);
        Assertions.assertNotNull(first);
        final EmailAddress second =
                EmailAddress.Companion.createOrNull(TEXT_SAMPLE);
        Assertions.assertNotNull(second);
        Assertions.assertEquals(first, second);
    }

    @Test
    public void hashCode_should_pass() {
        final EmailAddress first =
                EmailAddress.Companion.createOrNull(TEXT_SAMPLE);
        Assertions.assertNotNull(first);
        final EmailAddress second =
                EmailAddress.Companion.createOrNull(TEXT_SAMPLE);
        Assertions.assertNotNull(second);
        final int firstHashCode = first.hashCode();
        final int secondHashCode = second.hashCode();
        Assertions.assertEquals(firstHashCode, secondHashCode);
    }

    @Test
    public void toString_should_pass() {
        final String expected = TEXT_SAMPLE;
        final EmailAddress address =
                EmailAddress.Companion.createOrNull(expected);
        Assertions.assertNotNull(address);
        final String actual = address.toString();
        Assertions.assertEquals(expected, actual);
    }
}
