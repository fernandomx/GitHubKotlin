package com.androiddesdecero.oauth2.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import com.androiddesdecero.oauth2.R
import com.androiddesdecero.oauth2.api.WebServiceOAuth.Companion.instance
import com.androiddesdecero.oauth2.api.WebServiceOAuthApi
import com.androiddesdecero.oauth2.model.Missions
import com.androiddesdecero.oauth2.model.Model
import com.androiddesdecero.oauth2.model.Users
import com.androiddesdecero.oauth2.shared_pref.TokenManager
import com.androiddesdecero.oauth2.shared_pref.TokenManager.Companion.getIntance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogeadoActivity : AppCompatActivity() {
    private val btVerTodosLosMovimientosBancarios: Button? = null
    private val btVerTodosLosMovimientosBancariosUser: Button? = null
    private var btVerTodosUsuarios2: Button? = null
    private var btVerMissoes: Button? = null
    private var tokenManager: TokenManager? = null
    private var activity: Activity? = null
    var listaMissoes: List<Missions>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logeado)
        setUpView()
        activity = this
    }

    private fun setUpView() {
        tokenManager = getIntance(getSharedPreferences(TokenManager.SHARED_PREFERENCES, Context.MODE_PRIVATE))

        /* example stackoverflow
        btVerTodosLosMovimientosBancarios = findViewById(R.id.btVerTodosLosMovimientosBancarios);
        btVerTodosLosMovimientosBancarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verTodosMovimientos();
            }
        });

         */

        /* example stackoverflow
        btVerTodosLosMovimientosBancariosUser = findViewById(R.id.btVerTodosLosMovimientosBancariosUser);

        btVerTodosLosMovimientosBancariosUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verTodosMovimientosUser();
            }
        });


         */
        btVerTodosUsuarios2 = findViewById(R.id.btVerTodosUsuarios2)
        btVerTodosUsuarios2?.run { setOnClickListener(View.OnClickListener { verTodosUsuarios2() }) }
        btVerMissoes = findViewById(R.id.btVerMissoes)
        btVerMissoes?.run { setOnClickListener(View.OnClickListener { verMissoes() }) }
    }

    private fun verMissoes() {
        val call = instance
                ?.createService(WebServiceOAuthApi::class.java)
                ?.obterMissoes("Bearer " + tokenManager!!.token.accessToken)
        call!!.enqueue(object : Callback<Model?> {
            override fun onResponse(call: Call<Model?>, response: Response<Model?>) {
                try {
                    val model: Model?
                    model = response.body()
                    Log.i("LogeadoActivity", "onResponse: " + model.toString())
                    Log.i("LogeadoActivity", "onResponse: " + model!!.missoes[0].name)
                    for (i in model.missoes.indices) {
                        Log.i("LogeadoActivity", "MISSION: " + model.missoes[i].id + model.missoes[i].name) ///onResponse: null
                    }
                } catch (e: Exception) {
                    Log.e("ERROR", "ERRO: " + e.message.toString())


                }
            }

            override fun onFailure(call: Call<Model?>, t: Throwable) {
                Log.i("FALHA", "ON-FAILURE" + t.message)
            }
        })


/*

        call.enqueue(new Callback<List<Missions>>() {
            @Override
            public void onResponse(Call<List<Missions>> call, Response<List<Missions>> response) {
                Log.i("ok","code=" + response.code());

                if (response.isSuccessful()) {

                    listaMissoes = response.body();

                    for (int i=0; i <listaMissoes.size(); i++){
                        Missions missoes = listaMissoes.get(i);
                        Log.d("resultado", "resultado: " + missoes.getId() + "/" + missoes.getName());
                    }


                } else {

                             Log.d("error","erro" +response.errorBody() ) ;
                        // Do error handling here

                };

            }

            @Override
            public void onFailure(Call<List<Missions>> call, Throwable t) {

            }
            });

 */
    }

    private fun verTodosUsuarios2() {
        val call = instance
                ?.createService(WebServiceOAuthApi::class.java)
                ?.obterTodosUsuarios2("Bearer " + tokenManager!!.token.accessToken)
        call!!.enqueue(object : Callback<List<Users?>?> {
            override fun onResponse(call: Call<List<Users?>?>, response: Response<List<Users?>?>) {
                Log.d("RESPONSE", "CODE=" + response.code())
                val texto = response.body()!!
                for (i in texto.indices) {
                    Log.d("USUARIOS", "NOME: " + texto[i]?.name)
                }

                /*
                if(res.code() == 200){

                    for(int i=0; i<res.body().size(); i++){
                        //Log.d("TAG1", "Username: " + response.body().get(i).getUsername());
                        Log.d("TAG2", "Name: " + res.body().get(i).getName());
                    }

                    Toast.makeText(LogeadoActivity.this,"usuarios carregados",Toast.LENGTH_LONG).show();

                }else{
                    Log.d("TAG2", "Error");
                }

                */
            }

            override fun onFailure(call: Call<List<Users?>?>, t: Throwable) {}
        })
    } /*
    private void verTodosMovimientos(){
        Call<List<MovimientoBancario>> call = WebServiceOAuth
                .getInstance()
                .createService(WebServiceOAuthApi.class)
                .obtenerMovimientos("Bearer " + tokenManager.getToken().getAccessToken());

        call.enqueue(new Callback<List<MovimientoBancario>>() {
            @Override
            public void onResponse(Call<List<MovimientoBancario>> call, Response<List<MovimientoBancario>> response) {
                if(response.code()==200){
                    for(int i=0; i<response.body().size(); i++){
                        Log.d("TAG1", "UserID: " + response.body().get(i).getUserID() +
                                " Importe: " + response.body().get(i).getImporte() +
                                " Nombre: " +response.body().get(i).getName());
                    }
                }else {
                    Log.d("TAG1", "Error");
                }
            }

            @Override
            public void onFailure(Call<List<MovimientoBancario>> call, Throwable t) {

            }
        });
    }

 */
    /*
    private void verTodosMovimientosUser(){
        Users users = new Users();
        //users.setId(3l);
        Call<List<MovimientoBancario>> call = WebServiceOAuth
                .getInstance()
                .createServiceWithOAuth2(WebServiceOAuthApi.class, tokenManager)
                .obtenerMovimientosUser(users);

        call.enqueue(new Callback<List<MovimientoBancario>>() {
            @Override
            public void onResponse(Call<List<MovimientoBancario>> call, Response<List<MovimientoBancario>> response) {
                if(response.code() == 200){
                    for(int i=00; i<response.body().size(); i++){
                        Log.d("TAG1",
                                "UserID: " + response.body().get(i).getUserID() +
                                " Importe: " + response.body().get(i).getImporte() +
                                " Nombre: " +response.body().get(i).getName());
                    }
                }else if(response.code() == 404){
                    Log.d("TAG1", "No hay movimientos");
                }else if(response.code() == 401){
                    Token newToken = new Token();
                    newToken.setRefreshToken("");
                    newToken.setRefreshToken("");
                    tokenManager.saveToken(newToken);
                    try{
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Log.d("TAG1", "Invalid Access Token: " + jsonObject.getString("error"));
                    } catch (Exception e){
                        Log.d("TAG1", "Invalid Access Token: " + e.getMessage());
                    }

                    activity.finish();
                } else{
                    Log.d("TAG1", "Error");
                }
            }

            @Override
            public void onFailure(Call<List<MovimientoBancario>> call, Throwable t) {

            }
        });
    }

 */
}