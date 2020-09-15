package com.androiddesdecero.oauth2.model

import com.google.gson.annotations.SerializedName
import java.util.*

class Model {
    //@JvmField
    @SerializedName("list")
    var missoes: List<Missions> = ArrayList()
}