package org.kotools.types;

import org.junit.jupiter.api.Test;

class ZeroCompanionJavaSampleTest {
    private final ZeroCompanionJavaSample sample =
            new ZeroCompanionJavaSample();

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
    void fromIntOrNull_should_pass() {
        Assert.printsTrue(this.sample::fromIntOrNull);
    }
}
