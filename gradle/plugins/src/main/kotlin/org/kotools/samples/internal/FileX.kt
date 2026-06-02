package org.kotools.samples.internal

import kotlinx.ast.common.AstSource
import kotlinx.ast.common.ast.Ast
import kotlinx.ast.grammar.kotlin.common.summary
import kotlinx.ast.grammar.kotlin.target.antlr.kotlin.KotlinGrammarAntlrKotlinParser
import java.io.File

internal fun File.parseNodes(): List<Ast> {
    val source: AstSource.File = AstSource.File(this.path)
    return KotlinGrammarAntlrKotlinParser.parseKotlinFile(source)
        .summary(attachRawAst = false)
        .get()
}
