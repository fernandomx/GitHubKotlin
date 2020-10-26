package com.androidgit.oauth2.model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class ModelGH {

    @SerializedName("items")
    var items: List<GitHub> = ArrayList()

}