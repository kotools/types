package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
class EmailAddressCompanionJavaSample {
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
    void orThrowStringEmailAddressRegex() {
        final String text = "contact@kotools.org";
        final EmailAddressRegex regex = EmailAddressRegex.alphabetic();
        EmailAddress.orThrow(text, regex);
    }
}
