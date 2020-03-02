package br.com.lsm.androidsample.domain.entity

sealed class Language {
    object Kotlin : Language()
    object Swift : Language()
    object Java : Language()
    object Python : Language()
    object Scala : Language()
}