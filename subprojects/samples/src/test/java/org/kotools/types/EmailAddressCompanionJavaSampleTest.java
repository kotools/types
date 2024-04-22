package org.kotools.types;

import org.junit.jupiter.api.Test;

class EmailAddressCompanionJavaSampleTest {
    @Test
    void patternSample_should_pass() {
        final EmailAddressCompanionJavaSample sample =
                new EmailAddressCompanionJavaSample();
        final String expected = "^\\S+@\\S+\\.\\S+$";
        Assert.prints(expected, sample::patternSample);
    }

    @Test
    void fromString_Any_should_pass() {
        final EmailAddressCompanionJavaSample sample =
                new EmailAddressCompanionJavaSample();
        final String expected = "success";
        Assert.prints(expected, sample::fromString_Any);
    }

    @Test
    void fromString_Any_Any_should_pass() {
        final EmailAddressCompanionJavaSample sample =
                new EmailAddressCompanionJavaSample();
        final String expected = "success";
        Assert.prints(expected, sample::fromString_Any_Any);
    }

    @Test
    void fromStringOrNull_Any_should_pass() {
        final EmailAddressCompanionJavaSample sample =
                new EmailAddressCompanionJavaSample();
        Assert.printsTrue(sample::fromStringOrNull_Any);
    }

    @Test
    void fromStringOrNull_Any_Any_should_pass() {
        final EmailAddressCompanionJavaSample sample =
                new EmailAddressCompanionJavaSample();
        Assert.printsTrue(sample::fromStringOrNull_Any_Any);
    }
}
