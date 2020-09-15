package com.androiddesdecero.oauth2.model

import com.google.gson.annotations.SerializedName

class Users {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("login")
    var login: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("activated")
    var activated: Boolean? = null

    @SerializedName("group")
    var group: String? = null

}