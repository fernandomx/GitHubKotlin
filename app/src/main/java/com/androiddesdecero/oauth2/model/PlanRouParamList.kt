package com.androiddesdecero.oauth2.model

import java.io.Serializable

/**
@autor Fernando Meregali Xavier
@version 1.0
@date today

 */

import com.google.gson.annotations.Expose

class PlanRouParamList: Serializable {

    @Expose
    var id: Int? = null

    @Expose
    var text: String? = null

    @Expose
    var value: String? = null

}