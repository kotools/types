package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
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
    void toStringOverride() {
        final String pattern = EmailAddressRegex.defaultPattern()
                .toString();
        final String expected = "^\\S+@\\S+\\.\\S+$";
        Assertions.assertEquals(expected, pattern);
    }
}
