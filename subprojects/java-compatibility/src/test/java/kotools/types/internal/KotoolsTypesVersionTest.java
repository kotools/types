/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.internal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class KotoolsTypesVersionTest {
    @Test
    public void toString_should_pass() {
        final KotoolsTypesVersion[] versions = KotoolsTypesVersion.values();
        Arrays.stream(versions).forEach(version -> {
            final String actual = version.toString();
            final String expected = version.name()
                    .substring(1)
                    .replace('_', '.');
            Assertions.assertEquals(expected, actual);
        });
    }
}
