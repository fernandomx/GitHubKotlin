package com.androidgit.oauth2.ui

import android.app.Activity
import android.app.ProgressDialog
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidgit.oauth2.R
import com.androidgit.oauth2.api.WebServiceOAuth
import com.androidgit.oauth2.api.WebServiceOAuthApi
import com.androidgit.oauth2.model.GitHub
import com.androidgit.oauth2.model.ModelGH
import com.androidgit.oauth2.model.adapter.GitAdapter
import com.androidgit.oauth2.model.callback.GitFetchListener
import com.androidgit.oauth2.model.database.GitDatabase
import com.androidgit.oauth2.model.helper.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL

class GitActivity: AppCompatActivity(), GitAdapter.GitClickListener, GitFetchListener {

    private var mRecyclerView: RecyclerView? = null


    private var mGitAdapter: GitAdapter? = null
    private var mDatabase: GitDatabase? = null


    private var mReload: Button? = null
    private var mDialog: ProgressDialog? = null


    private var activity: Activity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git)

        configViews()

        mDatabase = GitDatabase(this)



        loadGitHubFeed()
        //mReload!!.setOnClickListener { loadGitHubFeed() }


        activity = this

    }

    private fun configViews() {


        mRecyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.setRecycledViewPool(RecyclerView.RecycledViewPool())
        mRecyclerView!!.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        mGitAdapter = GitAdapter(this)

        mRecyclerView!!.adapter = mGitAdapter

    }

    val networkAvailability: Boolean
        get() = Utils.isNetworkAvailable(applicationContext)

    private fun loadGitHubFeed() {

        mDialog = ProgressDialog(this@GitActivity)
        mDialog!!.setMessage("Loading GitHub Data...")
        mDialog!!.setCancelable(true)
        mDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        mDialog!!.isIndeterminate = true
        mGitAdapter!!.reset()
        mDialog!!.show()
        if (networkAvailability) {
            feedGitHub
        } else {
            feedFromDatabase
        }
    }

    val feedGitHub: Unit
        get() {

            val call = WebServiceOAuth.instance
                    ?.createService(WebServiceOAuthApi::class.java)
                    ?.getListGitHub()

            call!!.enqueue(object : Callback<ModelGH?> {


                override fun onResponse(call: Call<ModelGH?>, response: Response<ModelGH?>) {

                    try {


                    if (response.isSuccessful) {

                        val gitHubList = response.body()!!
                        var gitHub: GitHub? = null

                        for (i in gitHubList.items.indices) {


                            gitHub = gitHubList.items[i]

                            Log.i("GITHUB", "GITHUB: " + gitHub.id + "-" + gitHub.name)

                            val task = SaveIntoDatabase()
                            task.execute(gitHub)


                            mGitAdapter!!.addGitHubs(gitHub!!)

                        }

                    } else {
                        val sc = response.code()
                        when (sc) {
                            400 -> Log.e("Error 400", "Bad Request")
                            404 -> Log.e("Error 404", "Not Found")
                            else -> Log.e("Error", "Generic Error")
                        }
                    }
                    mDialog!!.dismiss()

                    } catch (e: Exception) {
                        mDialog!!.dismiss()
                        Log.e("ERROR", "ERRO: " + e.message.toString())
                    }
                }

                override fun onFailure(call: Call<ModelGH?>, t: Throwable) {
                    Log.e("ERROR", "ERRO: " + t.message.toString())
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()

                }


            })
        }

    inner class SaveIntoDatabase : AsyncTask<GitHub?, Void?, Void?>() {
        private val TAG = SaveIntoDatabase::class.java.simpleName
        override fun onPreExecute() {
            super.onPreExecute()
        }

        protected override fun doInBackground(vararg params: GitHub?): Void? {
            val github = params[0]
            try {
                val urlPhoto = github?.owner?.avatar_url
                val stream = URL(urlPhoto).openStream()
                val bitmap = BitmapFactory.decodeStream(stream)

                github?.owner?.picture = bitmap
                github?.let { mDatabase!!.addGitHubs(it) }
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
            }
            return null
        }
    }

    private val feedFromDatabase: Unit
        private get() {
            mDatabase!!.fetchGitHubs(this)
        }



    override fun onClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onDeliverAllGit(githubs: List<GitHub?>?) { }

    override fun onDeliverGit(githubs: GitHub?) {
        mGitAdapter!!.addGitHubs(githubs!!)
    }

    override fun onHideDialog() {
        mDialog!!.dismiss()
    }
}