package kotools.types.number;

import org.junit.jupiter.api.Test;
import org.kotools.types.Assert;

class NonZeroIntCompanionJavaSampleTest {
    @Test
    void create_should_pass() {
        final String expected = "23";
        final NonZeroIntCompanionJavaSample sample =
                new NonZeroIntCompanionJavaSample();
        Assert.prints(expected, sample::create);
    }

    @Test
    void createOrNull_should_pass() {
        final String expected = "23";
        final NonZeroIntCompanionJavaSample sample =
                new NonZeroIntCompanionJavaSample();
        Assert.prints(expected, sample::createOrNull);
    }
}
