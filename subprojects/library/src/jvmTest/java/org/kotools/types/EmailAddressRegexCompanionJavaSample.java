package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
class EmailAddressRegexCompanionJavaSample {
    @Test
    void defaultPattern() {
        final EmailAddressRegex regex = EmailAddressRegex.defaultPattern();
        final String pattern = regex.toString();
        final String expected = "^\\S+@\\S+\\.\\S+$";
        Assertions.assertEquals(expected, pattern);
    }

    @Test
    void orThrow() {
        EmailAddressRegex.orThrow("^\\S+@\\S+\\.\\S+$");
    }

    @Test
    void alphabetic() {
        final EmailAddressRegex regex = EmailAddressRegex.alphabetic();
        final String pattern = regex.toString();
        final String expected = "^[a-z]+@[a-z]+\\.[a-z]+$";
        Assertions.assertEquals(expected, pattern);
    }
}
