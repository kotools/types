@file:Suppress("DEPRECATION")

package com.github.kotools.csv.api

public val comma: Separator get() = Separator.Comma
public val semicolon: Separator get() = Separator.Semicolon

public sealed class Separator(internal val value: Char) {
    @Deprecated(
        message = "Use the `comma` property instead.",
        ReplaceWith(expression = "comma")
    )
    public object Comma : Separator(value = ',')

    @Deprecated(
        message = "Use the `semicolon` property instead.",
        ReplaceWith(expression = "semicolon")
    )
    public object Semicolon : Separator(value = ';')
}
