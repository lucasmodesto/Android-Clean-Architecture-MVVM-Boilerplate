package br.com.lsm.androidsample.data.network

import br.com.lsm.androidsample.data.model.RepositorySearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {

    @GET("search/repositories?")
    fun getRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int
    ): Single<RepositorySearchResponse>
}