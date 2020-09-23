package com.androiddesdecero.oauth2.model

import com.google.gson.annotations.Expose
import java.io.Serializable
import java.util.ArrayList

/**
@autor Fernando Meregali Xavier
@version 1.0
@date today

 */
class Flights:  Serializable  {

    @Expose
    var id: Int? = null

    @Expose
    var status: String? = null

    @Expose
    var plannedRoute: PlannedRoute? = null
    //var plannedRoute: List<PlannedRoute> = ArrayList()

}
