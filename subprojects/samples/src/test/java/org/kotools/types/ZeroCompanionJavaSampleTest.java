package org.kotools.types;

import org.junit.jupiter.api.Test;

class ZeroCompanionJavaSampleTest {
    private final ZeroCompanionJavaSample sample =
            new ZeroCompanionJavaSample();

    @Test
    void pattern_should_pass() {
        Assert.printsTrue(this.sample::pattern);
    }

    @Test
    void orNull_should_pass() {
        Assert.printsTrue(this.sample::orNull);
    }

    @Test
    void orThrow_should_pass() {
        Assert.printsTrue(this.sample::orThrow);
    }
}
