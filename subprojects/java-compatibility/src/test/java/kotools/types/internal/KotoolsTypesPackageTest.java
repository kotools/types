/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.internal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KotoolsTypesPackageTest {
    @Test
    public void toString_should_pass_on_Number() {
        final String actual = KotoolsTypesPackage.Number.toString();
        final String expected = "kotools.types.number";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void toString_should_pass_on_Text() {
        final String actual = KotoolsTypesPackage.Text.toString();
        final String expected = "kotools.types.text";
        Assertions.assertEquals(expected, actual);
    }
}
