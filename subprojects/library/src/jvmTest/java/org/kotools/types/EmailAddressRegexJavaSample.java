package org.kotools.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NewClassNamingConvention")
class EmailAddressRegexJavaSample {
    @Test
    void toStringOverride() {
        final String pattern = EmailAddressRegex.defaultPattern()
                .toString();
        final String expected = "^\\S+@\\S+\\.\\S+$";
        Assertions.assertEquals(expected, pattern);
    }
}
