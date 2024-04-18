package org.kotools.types;

import org.junit.jupiter.api.Test;

class EmailAddressJavaSampleTest {
    private final EmailAddressJavaSample sample = new EmailAddressJavaSample();

    @Test
    void constructor_String_should_pass() {
        Assert.printsTrue(sample::constructorString);
    }

    @Test
    void equals_override_should_pass() {
        Assert.printsTrue(sample::equals_override);
    }

    @Test
    void hashCode_override_should_pass() {
        Assert.printsTrue(sample::hashCode_override);
    }

    @Test
    void toString_override_should_pass() {
        Assert.printsTrue(sample::toString_override);
    }
}
