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
    void orThrowString() {
        final String text = "contact@kotools.org";
        boolean isSuccess;
        try {
            EmailAddress.orThrow(text);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }

    @Test
    void orThrowStringString() {
        final String text = "contact@kotools.org";
        final String pattern = "^[a-z]+@[a-z]+\\.[a-z]+$";
        boolean isSuccess;
        try {
            EmailAddress.orThrow(text, pattern);
            isSuccess = true;
        } catch (final IllegalArgumentException exception) {
            isSuccess = false;
        }
        Assertions.assertTrue(isSuccess);
    }
}
