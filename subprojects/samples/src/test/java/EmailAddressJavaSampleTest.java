package org.kotools.types;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailAddressJavaSampleTest {
    @Test
    void toString_override_should_pass() {
        final EmailAddressJavaSample sample = new EmailAddressJavaSample();
        final String output = Assertions.assertDoesNotThrow(
                () -> SystemLambda.tapSystemOut(sample::toString_override)
        ).trim();
        final boolean actual = Boolean.parseBoolean(output);
        Assertions.assertTrue(actual);
    }
}
