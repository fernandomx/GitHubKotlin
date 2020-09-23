package com.androiddesdecero.oauth2.model

import android.view.View
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.http.Path
import java.util.*

class Model {
    //@JvmField
    @SerializedName("list")
    var missoes: List<Missions> = ArrayList()


    //@SerializedName("flights")
    //lateinit var lights: List<VFlights>
    //var flights: List<VFlights> = ArrayList()

    /*
    inner class VFlights {

        var id: Int? = null

        var status: String? = null


    }

     */


}