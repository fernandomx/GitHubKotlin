package com.androidgit.oauth2.model

import com.google.gson.annotations.SerializedName

class GitHub {

    @SerializedName("id")
    var id: Int? = null


    //name of repo
    @SerializedName("name")
    var name: String? = null

    //count of stargazers
    @SerializedName("stargazers_count")
    var stargazers_count: Int? = null

    //count of forks
    @SerializedName("forks_count")
    var forks_count: Int? = null

    @SerializedName("owner")
    var owner: GitHubOwner? = null

    var isFromDatabase = false


}