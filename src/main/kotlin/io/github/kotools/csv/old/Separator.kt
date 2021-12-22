package io.github.kotools.csv.old

public val comma: Separator get() = Separator.Comma
public val semicolon: Separator get() = Separator.Semicolon

public sealed class Separator(internal val value: Char) {
    internal object Comma : Separator(',')
    internal object Semicolon : Separator(';')
}
