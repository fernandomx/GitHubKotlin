package com.androiddesdecero.oauth2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.androiddesdecero.oauth2.model.Missions
import com.androiddesdecero.oauth2.model.helper.Constants


private var mName: TextView? = null
private var mStatus: TextView? = null

private var mId: TextView? = null
private var mStatusVoo: TextView? = null
private var mFlights: TextView? = null
private var mFlightsPlan: TextView? = null


class MissionDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission_detail)


        val intent = intent
        val missions = intent.getSerializableExtra(Constants.REFERENCE.MISSIONS) as Missions


        configViews()

        //Show data
        mName!!.text = missions.name
        mStatus!!.text = missions.status

        //show flights
        val mi_fli = missions.flights

        var fligthsDetalhe: String? = ""
        var fligthParameters: String? = " "




        //get flights of missions
        for (i in mi_fli.indices) {

            val flights = mi_fli[i]

            Log.i("MISSIONS", "FLIGHT: " + flights.id + "-" + flights.status + "/")
            fligthsDetalhe = fligthsDetalhe + flights.id + "-" + flights.status + " | "



            val param = flights.plannedRoute!!.parameters!!.list

            //val xRotaDescription = flights.plannedRoute!!.description

            fligthParameters = fligthParameters + fligthsDetalhe + ">>"

            for (y in param.indices) {


                val parameters = param[y]

                Log.i("MISSIONS", "PARAMETROS: " + parameters.text + parameters.value + "/")
                fligthParameters = fligthParameters  + parameters.id +"-"+ parameters.text + "-" + parameters.value + "|"
            }


        }

        //get flights of missions






        //show flight and parameters

        mFlights!!.text = fligthsDetalhe
        mFlightsPlan!!.text = fligthParameters


    }

    private fun configViews() {


        //mReload = findViewById<View>(R.id.reload) as Button
        mName = findViewById<View>(R.id.missionName) as TextView
        mStatus = findViewById<View>(R.id.missionStatus) as TextView
        mFlights = findViewById<View>(R.id.missionFlights) as TextView
        mFlightsPlan = findViewById<View>(R.id.missionsParameters) as TextView




    }
}