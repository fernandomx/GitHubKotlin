package com.androiddesdecero.oauth2.model.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.androiddesdecero.oauth2.R
import com.androiddesdecero.oauth2.model.Creator
import com.androiddesdecero.oauth2.model.Missions
import java.time.Instant
import java.time.ZoneId
import java.util.ArrayList

/**
@autor Fernando Meregali Xavier
@version 1.0
@date today

 */
class MissionsAdapter(listener: MissionClickListener) : RecyclerView.Adapter<MissionsAdapter.Holder>() {
    private val mListener: MissionClickListener
    private val mMissions: MutableList<Missions>
    private val mCreator: MutableList<Creator>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.row_item, null, false)
        return Holder(row)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currMissions = mMissions[position]
        //val currCreator = mCreator[position]
        holder.mName.text = currMissions.name
        //holder.mStart.text = currMissions.start

        //holder.mStart.text = Instant.ofEpochMilli(currMissions.start!!.getTime()).atZone(ZoneId.systemDefault()).toLocalDate().toString()

        holder.mStatus.text = currMissions.status



       /*
        if (currMissions.isFromDatabase) {
            //holder.mPhoto.setImageBitmap(currFlower.picture)
        } else {
            //Picasso.with(holder.itemView.context).load(Constants.HTTP.BASE_URL + "/photos/" + currFlower.photo).into(holder.mPhoto)
        }

        */
    }

    override fun getItemCount(): Int {
        return mMissions.size
        //return mCreator.size
    }

    fun addMissions(missions: Missions) {
        mMissions.add(missions)
        notifyDataSetChanged()
    }

    fun addCreator(creator: Creator) {
        mCreator.add(creator)
        notifyDataSetChanged()
    }

    /**
     * @param position
     * @return
     */

    fun getSelectedMissions(position: Int): Missions {
        return mMissions[position]
    }



    fun reset() {
        mMissions.clear()
        //mCreator.clear()
        notifyDataSetChanged()
    }

    inner class Holder(itemView: View) : ViewHolder(itemView), View.OnClickListener {
        //val mPhoto: ImageView
        val mName: TextView
        //val mStart: TextView
        val mStatus: TextView


        override fun onClick(v: View) {
            mListener.onClick(layoutPosition)
        }

        init {
            //mPhoto = itemView.findViewById<View>(R.id.flowerPhoto) as ImageView
            mName = itemView.findViewById<View>(R.id.missionName) as TextView
            //mStart = itemView.findViewById<View>(R.id.missionStart) as TextView
            mStatus = itemView.findViewById<View>(R.id.missionStatus) as TextView
            itemView.setOnClickListener(this)
        }
    }

    interface MissionClickListener {
        fun onClick(position: Int)
    }

    companion object {
        private val TAG = MissionsAdapter::class.java.simpleName
    }

    init {
        mMissions = ArrayList()
        mCreator = ArrayList()
        mListener = listener
    }
}