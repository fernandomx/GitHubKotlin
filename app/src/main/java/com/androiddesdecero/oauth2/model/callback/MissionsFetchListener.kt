package com.androiddesdecero.oauth2.model.callback

import com.androiddesdecero.oauth2.model.Missions

/**
@autor Fernando Meregali Xavier
@version 1.0
@date today

 */
interface MissionsFetchListener {
    fun onDeliverAllMissions(missions: List<Missions?>?)
    fun onDeliverMissions(missions: Missions?)
    fun onHideDialog()
}