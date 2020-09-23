package com.androiddesdecero.oauth2.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.androiddesdecero.oauth2.R
import com.androiddesdecero.oauth2.api.WebServiceOAuth.Companion.instance
import com.androiddesdecero.oauth2.api.WebServiceOAuthApi
import com.androiddesdecero.oauth2.model.Token
import com.androiddesdecero.oauth2.model.Users
import com.androiddesdecero.oauth2.model.helper.Utils
import com.androiddesdecero.oauth2.shared_pref.TokenManager
import com.androiddesdecero.oauth2.shared_pref.TokenManager.Companion.getIntance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var etUsername: EditText? = null
    private var etPassword: EditText? = null
    private var btObterToken: Button? = null
    private val btCrearUsuario: Button? = null
    private var btVerTodosUsuarios: Button? = null
    private val btVerTodosLosMovimientosBancarios: Button? = null
    private var tokenManager: TokenManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpView()
    }


    val networkAvailability: Boolean
        get() = Utils.isNetworkAvailable(applicationContext)
        //get() = false

    private fun setUpView() {
        tokenManager = getIntance(getSharedPreferences(TokenManager.SHARED_PREFERENCES, Context.MODE_PRIVATE))
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btObterToken = findViewById(R.id.btObterToken)
        btObterToken?.run { setOnClickListener(View.OnClickListener {


            if (networkAvailability) {
                obterToken()
            }else {
                startActivity(Intent(applicationContext, MissionActivity::class.java))
            }


        }) }


        //btCrearUsuario = findViewById(R.id.btCrearUsuario);
        /*
        btCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearUsuario();
            }
        });
  */

        //btVerTodosUsuarios = findViewById(R.id.btVerTodosUsuarios)

        //btVerTodosUsuarios?.run { setOnClickListener(View.OnClickListener { verTodosUsuarios() }) }

/*
        btVerTodosLosMovimientosBancarios = findViewById(R.id.btVerTodosLosMovimientosBancarios);
        btVerTodosLosMovimientosBancarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verMovimientosBancarios();
            }
        });

 */
    }

    /*
    private void verMovimientosBancarios(){
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
    private fun obterToken() {
        val authHeader = Base64.encodeToString("androidApp:123".toByteArray(), Base64.NO_WRAP)
        val call = instance
                ?.createService(WebServiceOAuthApi::class.java)
                ?.obterToken(
                        authHeader,
                        etUsername!!.text.toString(),
                        etPassword!!.text.toString(),
                        "plataforma",
                        "password"
                )
        call!!.enqueue(object : Callback<Token?> {
            var token: Token? = Token()
            override fun onResponse(call: Call<Token?>, response: Response<Token?>) {
                if (response.code() == 200) {
                    Log.d("TAG1", "Access Token: " + response.body()!!.accessToken
                            + " Refresh Token: " + response.body()!!.refreshToken)
                    token = response.body()
                    tokenManager!!.saveToken(token!!)
                    //TODO start new Activity
                    //startActivity(Intent(applicationContext, LogeadoActivity::class.java))
                    startActivity(Intent(applicationContext, MissionActivity::class.java))
                } else {
                    Log.d("TAG1", "Error")
                }
            }

            override fun onFailure(call: Call<Token?>, t: Throwable) {}
        })
    }

    /*
    private void crearUsuario(){
        Users users = new Users();
        //user.setPassword(etPassword.getText().toString());
        //user.setUsername(etUsername.getText().toString());
        Call<Void> call = WebServiceOAuth
                .getInstance()
                .createService(WebServiceOAuthApi.class)
                .crearUsuario(users);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 201){
                    Log.d("TAG1", "Creado Usuario Correctamente");
                }else{
                    Log.d("TAG1", "Error");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

     */
    private fun verTodosUsuarios() {
        val call = instance
                ?.createService(WebServiceOAuthApi::class.java)
                ?.obterUsuarios()
        call!!.enqueue(object : Callback<List<Users?>?> {
            override fun onResponse(call: Call<List<Users?>?>, response: Response<List<Users?>?>) {
                if (response.code() == 200) {
                    for (i in response.body()!!.indices) {
                        //Log.d("TAG1", "Username: " + response.body().get(i).getUsername());
                        Log.d("TAG2", "Name: " + response.body()!![i]?.name)
                    }
                } else {
                    Log.d("TAG1", "Error")
                }
            }

            override fun onFailure(call: Call<List<Users?>?>, t: Throwable) {}
        })
    }
}