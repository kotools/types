package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
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

    @Test
    void alphanumeric() {
        final EmailAddressRegex regex = EmailAddressRegex.alphanumeric();
        final String pattern = regex.toString();
        final String expected = "^[0-9a-z]+@[0-9a-z]+\\.[0-9a-z]+$";
        Assertions.assertEquals(expected, pattern);
    }
}
