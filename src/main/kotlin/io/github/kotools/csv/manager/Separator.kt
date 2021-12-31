package io.github.kotools.csv.manager

/** Returns the comma [separator][Separator] (`','`). */
public val comma: Separator get() = Comma()

/** Returns the semicolon [separator][Separator] (`';'`). */
public val semicolon: Separator get() = Semicolon()

/** Representation of the character that separates values in a CSV file. */
public sealed class Separator private constructor(internal val value: Char)

internal class Comma : Separator(',')
internal class Semicolon : Separator(';')
