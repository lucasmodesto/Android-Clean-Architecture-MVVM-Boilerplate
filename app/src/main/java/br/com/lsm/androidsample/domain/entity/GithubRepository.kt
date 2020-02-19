package br.com.lsm.androidsample.domain.entity

data class GithubRepository(
    val name: String,
    val description: String,
    val forks: Int,
    val stars: Int,
    val owner: Owner
)