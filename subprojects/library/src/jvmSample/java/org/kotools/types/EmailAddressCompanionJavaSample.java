package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
class EmailAddressCompanionJavaSample {
    @Test
    void patternSample() {
        final String actual = EmailAddress.PATTERN;
        final String expected = "^\\S+@\\S+\\.\\S+$";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void fromStringAny() {
        final Object text = "contact@kotools.org";
        boolean isSuccess;
        try {
            EmailAddress.fromString(text);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }

    @Test
    void fromStringAnyAny() {
        final Object text = "contact@kotools.org";
        final Object pattern = "^[a-z]+@[a-z]+\\.[a-z]+$";
        boolean isSuccess;
        try {
            EmailAddress.fromString(text, pattern);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }

    @Test
    void fromStringOrNullAny() {
        final Object value = "contact@kotools.org";
        final EmailAddress actual = EmailAddress.fromStringOrNull(value);
        Assertions.assertNotNull(actual);
    }

    @Test
    void fromStringOrNullAnyAny() {
        final Object value = "contact@kotools.org";
        final Object pattern = "^[a-z]+@[a-z]+\\.[a-z]+$";
        final EmailAddress actual =
                EmailAddress.fromStringOrNull(value, pattern);
        Assertions.assertNotNull(actual);
    }
}
