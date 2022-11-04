package io.github.kotools.csv.common

import kotools.csv.SinceKotoolsCsv

/** Representation of the character that separates values in a CSV file. */
@SinceKotoolsCsv("2.0")
public sealed class Separator
private constructor(internal val value: Char) {
    private class Comma : Separator(',')
    private class Semicolon : Separator(';')

    public companion object {
        /** Returns the comma [separator][Separator] (`','`). */
        public val Comma: Separator get() = Comma()

        /** Returns the semicolon [separator][Separator] (`';'`). */
        public val Semicolon: Separator get() = Semicolon()
    }
}
