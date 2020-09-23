package com.androiddesdecero.oauth2.model

import com.google.gson.annotations.Expose
import java.io.Serializable
import java.util.ArrayList

/**
@autor Fernando Meregali Xavier
@version 1.0
@date today

 */
class PlannedRouteParameters : Serializable {

    /*
    @Expose
    var id: Int? = null

    @Expose
    var text: String? = null

    @Expose
    var value: String? = null

     */
    @Expose
    var list: List<PlanRouParamList> = ArrayList()




}