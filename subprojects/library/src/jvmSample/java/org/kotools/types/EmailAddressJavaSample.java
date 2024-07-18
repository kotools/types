package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
class EmailAddressJavaSample {
    // -------------------- Structural equality operations ---------------------

    @Test
    void equalsOverride() {
        final Object text = "contact@kotools.org";
        final EmailAddress first = EmailAddress.fromString(text);
        final EmailAddress second = EmailAddress.fromString(text);
        final boolean actual = first.equals(second);
        Assertions.assertTrue(actual);
    }

    @Test
    void hashCodeOverride() {
        final Object text = "contact@kotools.org";
        final int first = EmailAddress.fromString(text)
                .hashCode();
        final int second = EmailAddress.fromString(text)
                .hashCode();
        final boolean actual = first == second;
        Assertions.assertTrue(actual);
    }

    // ------------------------------ Conversions ------------------------------

    @Test
    void toStringOverride() {
        final Object text = "contact@kotools.org";
        final EmailAddress address = EmailAddress.fromString(text);
        final String actual = address.toString();
        Assertions.assertEquals(text, actual);
    }
}
