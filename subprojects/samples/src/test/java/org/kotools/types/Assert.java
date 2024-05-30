package org.kotools.types;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;

public class Assert {
    public static void prints(final String expected, final Runnable block) {
        final String actual = Assertions.assertDoesNotThrow(
                () -> SystemLambda.tapSystemOut(block::run)
        ).trim();
        Assertions.assertEquals(expected, actual);
    }

    public static void printsTrue(@NotNull final Runnable block) {
        final String output = Assertions.assertDoesNotThrow(
                () -> SystemLambda.tapSystemOut(block::run)
        ).trim();
        final boolean actual = Boolean.parseBoolean(output);
        Assertions.assertTrue(actual);
    }
}
