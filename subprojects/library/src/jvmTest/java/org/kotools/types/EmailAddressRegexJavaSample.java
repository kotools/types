package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
class EmailAddressRegexJavaSample {
    @Test
    void equalsOverride() {
        final EmailAddressRegex regex = EmailAddressRegex.defaultPattern();
        final EmailAddressRegex other = EmailAddressRegex.defaultPattern();
        final boolean result = regex.equals(other);
        final String message =
                "Regular expressions with the same pattern are equal.";
        Assertions.assertTrue(result, message);
    }

    @Test
    void hashCodeOverride() {
        final int hashCode = EmailAddressRegex.defaultPattern()
                .hashCode();
        final int other = EmailAddressRegex.defaultPattern()
                .hashCode();
        final boolean result = hashCode == other;
        final String message = "Regular expressions with the same pattern " +
                "have the same hash code value.";
        Assertions.assertTrue(result, message);
    }

    @Test
    void matches() {
        final String text = "contact@kotools.org";
        final EmailAddressRegex regex = EmailAddressRegex.alphabetic();
        final boolean result = regex.matches(text);
        final String message = String.format(
                "'%s' matches the following regular expression: '%s'.",
                text,
                regex
        );
        Assertions.assertTrue(result, message);
    }

    @Test
    void toStringOverride() {
        final String pattern = EmailAddressRegex.defaultPattern()
                .toString();
        final String expected = "^\\S+@\\S+\\.\\S+$";
        Assertions.assertEquals(expected, pattern);
    }

    // ------------------------------- Companion -------------------------------

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
