package com.androidgit.oauth2.model.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.androidgit.oauth2.R
import com.androidgit.oauth2.model.GitHub
import java.util.ArrayList

class GitAdapter(listener: GitClickListener) : RecyclerView.Adapter<GitAdapter.Holder>() {
    private val mListener: GitClickListener
    private val mGitHubs: MutableList<GitHub>
    //private val mCreator: MutableList<Creator>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.row_item, null, false)
        return Holder(row)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currGitHub = mGitHubs[position]


        //holder.mStart.text = Instant.ofEpochMilli(currMissions.start!!.getTime()).atZone(ZoneId.systemDefault()).toLocalDate().toString()


        holder.mLogin.text = currGitHub.owner?.login
        holder.mName.text = "Rep.: " + currGitHub.name
        holder.mStars.text = "Stars: " + currGitHub.stargazers_count.toString()
        holder.mForks.text = "Forks: " + currGitHub.forks_count.toString()

        if (currGitHub.isFromDatabase) {
            holder.mPhoto.setImageBitmap(currGitHub.owner?.picture)
        } else {
            val urlPhoto: String? = currGitHub.owner?.avatar_url
            Picasso.with(holder.itemView.context).load(urlPhoto).into(holder.mPhoto)
        }

        /*
         if (currMissions.isFromDatabase) {
             //holder.mPhoto.setImageBitmap(currFlower.picture)
         } else {
             //Picasso.with(holder.itemView.context).load(Constants.HTTP.BASE_URL + "/photos/" + currFlower.photo).into(holder.mPhoto)
         }

         */
    }

    override fun getItemCount(): Int {
        return mGitHubs.size

    }

    fun addGitHubs(githubs: GitHub) {
        mGitHubs.add(githubs)
        notifyDataSetChanged()
    }



    /**
     * @param position
     * @return
     */

    fun getSelectedGitHubs(position: Int): GitHub {
        return mGitHubs[position]
    }


    fun reset() {
        mGitHubs.clear()

        notifyDataSetChanged()
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val mPhoto: ImageView
        val mName: TextView
        val mLogin: TextView
        val mStars: TextView
        val mForks: TextView



        override fun onClick(v: View) {
            mListener.onClick(layoutPosition)
        }

        init {
            mPhoto = itemView.findViewById<View>(R.id.gitHubPhoto) as ImageView
            mLogin = itemView.findViewById<View>(R.id.gitHubLogin) as TextView

            mName = itemView.findViewById<View>(R.id.gitHubName) as TextView
            mStars = itemView.findViewById<View>(R.id.stargazersCount) as TextView
            mForks = itemView.findViewById<View>(R.id.forksCount) as TextView


            //itemView.setOnClickListener(this)
        }
    }

    interface GitClickListener {
        fun onClick(position: Int)
    }

    companion object {
        private val TAG = GitAdapter::class.java.simpleName
    }

    init {
        mGitHubs = ArrayList()
        mListener = listener
    }
}