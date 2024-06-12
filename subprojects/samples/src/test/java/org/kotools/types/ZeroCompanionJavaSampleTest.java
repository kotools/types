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
    void fromByte_should_pass() {
        Assert.printsTrue(this.sample::fromByte);
    }

    @Test
    void fromByteOrNull_should_pass() {
        Assert.printsTrue(this.sample::fromByteOrNull);
    }

    @Test
    void fromShort_should_pass() {
        Assert.printsTrue(this.sample::fromShort);
    }

    @Test
    void fromShortOrNull_should_pass() {
        Assert.printsTrue(this.sample::fromShortOrNull);
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
