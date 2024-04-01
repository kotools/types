package org.kotools.types;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailAddressCompanionJavaSampleTest {
    @Test
    void patternSample_should_pass() {
        final EmailAddressCompanionJavaSample sample =
                new EmailAddressCompanionJavaSample();
        final String actual = Assertions.assertDoesNotThrow(
                () -> SystemLambda.tapSystemOut(sample::patternSample)
        ).trim();
        final String expected = "^\\S+@\\S+\\.\\S+$";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void fromString_Any_should_pass() {
        final EmailAddressCompanionJavaSample sample =
                new EmailAddressCompanionJavaSample();
        final String actual = Assertions.assertDoesNotThrow(
                () -> SystemLambda.tapSystemOut(sample::fromString_Any)
        ).trim();
        final String expected = "success";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void fromString_Any_Any_should_pass() {
        final EmailAddressCompanionJavaSample sample =
                new EmailAddressCompanionJavaSample();
        final String actual = Assertions.assertDoesNotThrow(
                () -> SystemLambda.tapSystemOut(sample::fromString_Any_Any)
        ).trim();
        final String expected = "success";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void fromStringOrNull_Any_should_pass() {
        final EmailAddressCompanionJavaSample sample =
                new EmailAddressCompanionJavaSample();
        final String output = Assertions.assertDoesNotThrow(
                () -> SystemLambda.tapSystemOut(sample::fromStringOrNull_Any)
        ).trim();
        final boolean actual = Boolean.parseBoolean(output);
        Assertions.assertTrue(actual);
    }

    @Test
    void fromStringOrNull_Any_Any_should_pass() {
        final EmailAddressCompanionJavaSample sample =
                new EmailAddressCompanionJavaSample();
        final String output = Assertions.assertDoesNotThrow(
                () -> SystemLambda.tapSystemOut(
                        sample::fromStringOrNull_Any_Any
                )
        ).trim();
        final boolean actual = Boolean.parseBoolean(output);
        Assertions.assertTrue(actual);
    }
}
