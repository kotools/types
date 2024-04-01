package org.kotools.types;

import org.junit.jupiter.api.Test;

class EmailAddressJavaSampleTest {
    @Test
    void toString_override_should_pass() {
        final EmailAddressJavaSample sample = new EmailAddressJavaSample();
        Assert.printsTrue(sample::toString_override);
    }
}
