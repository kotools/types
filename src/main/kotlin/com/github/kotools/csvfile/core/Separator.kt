package com.github.kotools.csvfile.core

public val comma: Separator get() = Separator.Comma
public val semicolon: Separator get() = Separator.Semicolon

public sealed class Separator(internal val value: Char) {
    public object Comma : Separator(value = ',')
    public object Semicolon : Separator(value = ';')
}
