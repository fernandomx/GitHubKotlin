package com.androiddesdecero.oauth2.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class Missions : Serializable {
    @Expose
    var id: Int? = null

    @Expose
    var name: String? = null

    @Expose
    var start: Double? = null

    @Expose
    var status: String? = null

    var isFromDatabase = false

    @Expose
    var flights: List<Flights> = ArrayList()




    companion object {
        private const val serialVersionUID = 111696345129311948L
    }
}