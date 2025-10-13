package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kotools.types.internal.Warning;

@SuppressWarnings(Warning.TEST_JAVA_CLASS_NAME)
class EmailAddressJavaSample {
    @Test
    void equalsOverride() {
        final String text = "contact@kotools.org";
        final EmailAddress first = EmailAddress.orThrow(text);
        final EmailAddress second = EmailAddress.orThrow(text);
        final boolean actual = first.equals(second);
        Assertions.assertTrue(actual);
    }

    @Test
    void hashCodeOverride() {
        final String text = "contact@kotools.org";
        final int first = EmailAddress.orThrow(text)
                .hashCode();
        final int second = EmailAddress.orThrow(text)
                .hashCode();
        final boolean actual = first == second;
        Assertions.assertTrue(actual);
    }

    @Test
    void toStringOverride() {
        final String text = "contact@kotools.org";
        final EmailAddress address = EmailAddress.orThrow(text);
        final String actual = address.toString();
        Assertions.assertEquals(text, actual);
    }

    // ------------------------------- Companion -------------------------------

    @Test
    void ofText() {
        final EmailAddress result = EmailAddress.of("contact@kotools.org");
        Assertions.assertNotNull(result);
    }

    @Test
    void ofTextRegex() {
        final String text = "contact@kotools.org";
        final EmailAddressRegex regex = EmailAddressRegex.alphabetic();
        final EmailAddress result = EmailAddress.of(text, regex);
        Assertions.assertNotNull(result);
    }

    @Test
    void orThrowString() {
        final String text = "contact@kotools.org";
        EmailAddress.orThrow(text);
    }

    @Test
    void orThrowStringEmailAddressRegex() {
        final String text = "contact@kotools.org";
        final EmailAddressRegex regex = EmailAddressRegex.alphabetic();
        EmailAddress.orThrow(text, regex);
    }
}
