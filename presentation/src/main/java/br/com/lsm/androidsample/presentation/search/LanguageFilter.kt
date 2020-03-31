package br.com.lsm.androidsample.presentation.search

import br.com.lsm.androidsample.domain.entity.Language

data class LanguageFilter(
    val language: Language,
    val displayNameResId: Int,
    val imageResId: Int,
    var isSelected: Boolean = false
)