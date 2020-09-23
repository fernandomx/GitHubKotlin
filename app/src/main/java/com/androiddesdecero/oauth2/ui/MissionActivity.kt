package com.androiddesdecero.oauth2.ui

//import com.androiddesdecero.oauth2.model.Model2
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddesdecero.oauth2.MissionDetailActivity
import com.androiddesdecero.oauth2.R
import com.androiddesdecero.oauth2.api.WebServiceOAuth
import com.androiddesdecero.oauth2.api.WebServiceOAuthApi
import com.androiddesdecero.oauth2.model.Missions
import com.androiddesdecero.oauth2.model.Model
import com.androiddesdecero.oauth2.model.adapter.MissionsAdapter
import com.androiddesdecero.oauth2.model.callback.MissionsFetchListener
import com.androiddesdecero.oauth2.model.database.MissionsDatabase
import com.androiddesdecero.oauth2.model.helper.Constants
import com.androiddesdecero.oauth2.model.helper.Utils.isNetworkAvailable
import com.androiddesdecero.oauth2.shared_pref.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MissionActivity : AppCompatActivity(), MissionsAdapter.MissionClickListener, MissionsFetchListener {

    private var mRecyclerView: RecyclerView? = null
    //private var mManager: RestManager? = null
    private var mMissionsAdapter: MissionsAdapter? = null
    private var mDatabase: MissionsDatabase? = null


    private var mReload: Button? = null
    private var mDialog: ProgressDialog? = null

    private var tokenManager: TokenManager? = null
    private var activity: Activity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission)

        configViews()
        //mManager = RestManager()
        mDatabase = MissionsDatabase(this)






        loadMissionsFeed()
        mReload!!.setOnClickListener { loadMissionsFeed() }

        activity = this
    }

    private fun loadMissionsFeed() {

        mDialog = ProgressDialog(this@MissionActivity)
        mDialog!!.setMessage("Loading Missions Data...")
        mDialog!!.setCancelable(true)
        mDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        mDialog!!.isIndeterminate = true
        mMissionsAdapter!!.reset()
        mDialog!!.show()
        if (networkAvailability) {
            feed
        } else {
            feedFromDatabase
        }
    }

    private val feedFromDatabase: Unit
        private get() {
            mDatabase!!.fetchMissions(this)
        }

    private fun configViews() {

        tokenManager = TokenManager.getIntance(getSharedPreferences(TokenManager.SHARED_PREFERENCES, Context.MODE_PRIVATE))

        mReload = findViewById<View>(R.id.reload) as Button
        mRecyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.setRecycledViewPool(RecyclerView.RecycledViewPool())
        mRecyclerView!!.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        mMissionsAdapter = MissionsAdapter(this)

        mRecyclerView!!.adapter = mMissionsAdapter

    }


    override fun onClick(position: Int) {
        val selectedMissions = mMissionsAdapter!!.getSelectedMissions(position)
        val intent = Intent(this@MissionActivity, MissionDetailActivity::class.java)
        intent.putExtra(Constants.REFERENCE.MISSIONS, selectedMissions)
        startActivity(intent)
    }



    val feed: Unit
        get() {
            val call = WebServiceOAuth.instance
                    ?.createService(WebServiceOAuthApi::class.java)
                    ?.obterMissoes("Bearer " + tokenManager!!.token.accessToken)

            // -- inicio call missoes
            call!!.enqueue(object : Callback<Model?> {
                override fun onResponse(call: Call<Model?>, response: Response<Model?>) {
                    try {

                        if (response.isSuccessful) {
                            val missionsList = response.body()!!

                            //loop for missions
                            for (i in missionsList.missoes.indices) {
                                val missions = missionsList.missoes[i]


                                //verifica se já  não possui missão criada no banco de dados
                                //val it = intent
                                //if (!it.hasExtra("mission")) {
                                    //missions = it.getSerializableExtra("aluno") as Aluno
                                    //nome.setText(aluno.getNome())
                                    //cpf.setText(aluno.getCpf())
                                    //telefone.setText(aluno.getTelefone())

                                    //Salvar no banco de dados
                                    val task = SaveIntoDatabase()
                                    task.execute(missions)

                                //}


                                mMissionsAdapter!!.addMissions(missions!!)
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
                        Log.e("ERROR", "ERRO: " + e.message.toString())
                    }
                }

                override fun onFailure(call: Call<Model?>, t: Throwable) {
                    Log.i("FALHA", "ON-FAILURE" + t.message)
                }
            })
            //-- end call missoes



        }

    val networkAvailability: Boolean
        get() = isNetworkAvailable(applicationContext)
        //get() = false

    inner class SaveIntoDatabase : AsyncTask<Missions?, Void?, Void?>() {
        private val TAG = SaveIntoDatabase::class.java.simpleName
        override fun onPreExecute() {
            super.onPreExecute()
        }

        protected override fun doInBackground(vararg params: Missions?): Void? {
            val mission = params[0]
            try {
                //val stream = URL(Constants.HTTP.BASE_URL + "/photos/" + flower?.photo).openStream()
                //val bitmap = BitmapFactory.decodeStream(stream)
                //flower?.picture = bitmap
                mission?.let { mDatabase!!.addMissions(it) }
            } catch (e: Exception) {
                Log.d(TAG, e.message)
            }
            return null
        }
    }



    companion object {
        private val TAG = MissionActivity::class.java.simpleName
    }

    override fun onDeliverAllMissions(missions: List<Missions?>?) {}

    override fun onDeliverMissions(missions: Missions?) {

        mMissionsAdapter!!.addMissions(missions!!)

    }

    override fun onHideDialog() {

        mDialog!!.dismiss()
    }

}