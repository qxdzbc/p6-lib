package com.emeraldblast.p6.formula.translator

object FormulaTranslators {
    val standard: FormulaTranslator = FormulaTranslatorFinal()
    val directLiteralTranslator: FormulaTranslator =
        DirectLiteralTranslator()
}