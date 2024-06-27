package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
class EmailAddressJavaSample {
    @Test
    void equalsOverride() {
        final Object value = "contact@kotools.org";
        final EmailAddress first = EmailAddress.fromString(value);
        final EmailAddress second = EmailAddress.fromString(value);
        final boolean actual = first.equals(second);
        Assertions.assertTrue(actual);
    }

    @Test
    void hashCodeOverride() {
        final Object value = "contact@kotools.org";
        final int first = EmailAddress.fromString(value)
                .hashCode();
        final int second = EmailAddress.fromString(value)
                .hashCode();
        final boolean actual = first == second;
        Assertions.assertTrue(actual);
    }

    @Test
    void toStringOverride() {
        final Object value = "contact@kotools.org";
        final EmailAddress address = EmailAddress.fromString(value);
        final String actual = address.toString();
        Assertions.assertEquals(value, actual);
    }
}
