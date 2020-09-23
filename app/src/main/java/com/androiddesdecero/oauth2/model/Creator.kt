package com.androiddesdecero.oauth2.model

/**
@autor Fernando Meregali Xavier
@version 1.0
@date today

 */
import com.google.gson.annotations.Expose
import java.io.Serializable

class Creator:  Serializable  {

    @Expose
    var login: String? = null

    @Expose
    var name: String? = null



}