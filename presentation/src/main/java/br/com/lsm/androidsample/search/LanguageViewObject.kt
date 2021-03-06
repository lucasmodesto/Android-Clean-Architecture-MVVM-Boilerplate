package br.com.lsm.androidsample.search

import br.com.lsm.androidsample.domain.entity.Language

data class LanguageViewObject(
    val language: Language,
    val displayNameResId: Int,
    val imageResId: Int,
    var isSelected: Boolean = false
)