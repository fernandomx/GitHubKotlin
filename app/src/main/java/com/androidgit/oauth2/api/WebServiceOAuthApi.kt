package com.androidgit.oauth2.api

import com.androidgit.oauth2.model.*
import retrofit2.Call
import retrofit2.http.*

interface WebServiceOAuthApi {

    // GET GITHUB
    @GET("https://api.github.com/search/repositories?q=language:kotlin&sort=stars&page=1")
    fun getListGitHub(): Call<ModelGH?>?


}