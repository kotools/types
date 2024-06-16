package org.kotools.types;

import org.junit.jupiter.api.Test;

class EmailAddressCompanionJavaSampleTest {
    private final EmailAddressCompanionJavaSample sample =
            new EmailAddressCompanionJavaSample();

    @Test
    void patternSample_should_pass() {
        final String expected = "^\\S+@\\S+\\.\\S+$";
        Assert.prints(expected, this.sample::patternSample);
    }

    @Test
    void fromString_Any_should_pass() {
        final String expected = "success";
        Assert.prints(expected, this.sample::fromString_Any);
    }

    @Test
    void fromString_Any_Any_should_pass() {
        final String expected = "success";
        Assert.prints(expected, this.sample::fromString_Any_Any);
    }

    @Test
    void fromStringOrNull_Any_should_pass() {
        Assert.printsTrue(this.sample::fromStringOrNull_Any);
    }

    @Test
    void fromStringOrNull_Any_Any_should_pass() {
        Assert.printsTrue(this.sample::fromStringOrNull_Any_Any);
    }

    @Test
    void orNull_Any_should_pass() {
        Assert.printsTrue(this.sample::orNull_Any);
    }
}
