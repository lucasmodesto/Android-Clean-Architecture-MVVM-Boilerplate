package br.com.lsm.androidsample.data.model

import com.google.gson.annotations.SerializedName


data class RepositorySearchResponse(
    @SerializedName("total_count") var totalCount: Int = 0,
    @SerializedName("incomplete_results") var incompleteResults: Boolean = false,
    @SerializedName("items") var items: List<GitHubRepositoryResponse> = mutableListOf()
)