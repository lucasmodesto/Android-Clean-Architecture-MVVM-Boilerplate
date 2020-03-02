package br.com.lsm.androidsample.data.model.request

import br.com.lsm.androidsample.domain.entity.Language

object LanguageQuery {

    fun getValue(language: Language): String {
        return when (language) {
            is Language.Kotlin -> "kotlin"
            is Language.Swift -> "swift"
            is Language.Java -> "java"
            is Language.Python -> "python"
            is Language.Scala -> "scala"
        }
    }
}