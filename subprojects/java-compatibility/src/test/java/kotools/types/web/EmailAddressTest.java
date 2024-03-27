package kotools.types.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class EmailAddressTest {
    @Nested
    public class Companion {
        @Test
        public void create_should_be_compatible_with_Java() {
            final String value = "contact@kotools.org";
            final String type = EmailAddress.class.getSimpleName();
            final String message = "Creating an '%s' from '%s' should pass."
                    .formatted(type, value);
            Assertions.assertDoesNotThrow(
                    () -> EmailAddress.Companion.create(value),
                    message
            );
        }

        @Test
        public void createOrNull_should_be_compatible_with_Java() {
            final String value = "contact@kotools.org";
            final EmailAddress actual =
                    EmailAddress.Companion.createOrNull(value);
            final String type = EmailAddress.class.getSimpleName();
            final String message = "Creating an '%s' from '%s' should pass."
                    .formatted(type, value);
            Assertions.assertNotNull(actual, message);
        }
    }

    @Test
    public void constructor_should_be_compatible_with_Java() {
        final String value = "contact@kotools.org";
        final String type = EmailAddress.class.getSimpleName();
        final String message = "Creating an '%s' from '%s' should pass."
                .formatted(type, value);
        Assertions.assertDoesNotThrow(() -> new EmailAddress(value), message);
    }

    @Test
    public void equals_should_be_compatible_with_Java() {
        final String value = "contact@kotools.org";
        final EmailAddress first = EmailAddress.Companion.create(value);
        final EmailAddress second = EmailAddress.Companion.create(value);
        final boolean actual = first.equals(second);
        final String message =
                "Comparing 2 email addresses with the same value should pass.";
        Assertions.assertTrue(actual, message);
    }

    @Test
    public void hashCode_should_be_compatible_with_Java() {
        final String value = "contact@kotools.org";
        final int actual = EmailAddress.Companion.create(value)
                .hashCode();
        final int expected = EmailAddress.Companion.create(value)
                .hashCode();
        final String message = "Comparing the hash code of 2 email addresses " +
                "having the same value should pass.";
        Assertions.assertEquals(expected, actual, message);
    }

    @Test
    public void toString_should_be_compatible_with_Java() {
        final String expected = "contact@kotools.org";
        final String actual = EmailAddress.Companion.create(expected)
                .toString();
        final String type = EmailAddress.class.getSimpleName();
        final String message = "'%s.toString()' should return '%s'."
                .formatted(type, expected);
        Assertions.assertEquals(expected, actual, message);
    }
}
