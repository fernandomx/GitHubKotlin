package com.androiddesdecero.oauth2.model

/**
@autor Fernando Meregali Xavier
@version 1.0
@date today

 */
import com.google.gson.annotations.Expose
import java.io.Serializable
import java.util.ArrayList

class PlannedRoute: Serializable  {

    @Expose
    var description: String? = null

    @Expose
    //var parameters: List<PlannedRouteParameters> = ArrayList()
    var parameters: PlannedRouteParameters? = null

}