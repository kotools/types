package org.kotools.types;

import org.junit.jupiter.api.Test;

class ZeroCompanionJavaSampleTest {
    @Test
    void fromByteOrNull_should_pass() {
        final ZeroCompanionJavaSample sample = new ZeroCompanionJavaSample();
        Assert.printsTrue(sample::fromByteOrNull);
    }
}
