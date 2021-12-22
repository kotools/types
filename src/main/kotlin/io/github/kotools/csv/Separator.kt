@file:Suppress("DEPRECATION")

package io.github.kotools.csv

public val comma: Separator get() = Separator.Comma
public val semicolon: Separator get() = Separator.Semicolon

public sealed class Separator(internal val value: Char) {
    @Deprecated(
        message = "Use the `comma` property instead.",
        ReplaceWith("comma")
    )
    public object Comma : Separator(',')

    @Deprecated(
        message = "Use the `semicolon` property instead.",
        ReplaceWith("semicolon")
    )
    public object Semicolon : Separator(';')
}
