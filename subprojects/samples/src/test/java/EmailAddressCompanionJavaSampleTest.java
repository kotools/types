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
    void fromStringSample_should_pass() {
        final EmailAddressCompanionJavaSample sample =
                new EmailAddressCompanionJavaSample();
        final String actual = Assertions.assertDoesNotThrow(
                () -> SystemLambda.tapSystemOut(sample::fromStringSample)
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
    void fromStringOrNull_Any_Any_Sample_should_pass() {
        final EmailAddressCompanionJavaSample sample =
                new EmailAddressCompanionJavaSample();
        final String output = Assertions.assertDoesNotThrow(
                () -> SystemLambda.tapSystemOut(
                        sample::fromStringOrNull_Any_Any_Sample
                )
        ).trim();
        final boolean actual = Boolean.parseBoolean(output);
        Assertions.assertTrue(actual);
    }
}
