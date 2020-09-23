package com.androiddesdecero.oauth2.api

import com.androiddesdecero.oauth2.model.Model
import com.androiddesdecero.oauth2.model.Token
import com.androiddesdecero.oauth2.model.Users
import retrofit2.Call
import retrofit2.http.*

interface WebServiceOAuthApi {
    @GET("https://gateway.eneva.pixforcemaps.com/api/users")
    fun obterUsuarios(): Call<List<Users?>?>?

    @POST("/api/create_user")
    fun crearUsuario(@Body user: Users?): Call<Void?>?

    @FormUrlEncoded //@POST("/oauth/token")
    @POST("/auth/realms/eneva/protocol/openid-connect/token")
    fun obterToken(
            @Header("Authorization") authorization: String?,
            @Field("username") username: String?,
            @Field("password") password: String?,
            @Field("client_id") clientId: String?,
            @Field("grant_type") grantType: String?
    ): Call<Token?>?

    @FormUrlEncoded //@POST("/oauth/token")
    @POST("/auth/realms/eneva/protocol/openid-connect/token")
    fun obterTokenconRefreshToken(
            @Header("Authorization") authorization: String?,
            @Field("refresh_token") refreshToken: String?,
            @Field("grant_type") grantType: String?
    ): Call<Token?>?

    /* -- alguns exemplos stackoverflow
    @GET("/api/oauth2/movimiento_bancario")
    Call<List<MovimientoBancario>> obtenerMovimientos(@Header("Authorization") String accessToken);

    @POST("/api/oauth2/movimiento_bancario_user")
    Call<List<MovimientoBancario>> obtenerMovimientosUser(@Body Users users);
    */
    @GET("https://gateway.eneva.pixforcemaps.com/api/users")
    fun obterTodosUsuarios2(@Header("Authorization") accessToken: String?): Call<List<Users?>?>?

    //@GET("https://gateway.eneva.pixforcemaps.com/api/missions")
    //@GET("https://gateway.eneva.pixforcemaps.com/api/equipments?page=0&size=4&order=id&direction=ASC")
    // /missions?page=0&size=10&order=id&direction=ASC
    //@GET("https://gateway.eneva.pixforcemaps.com/api/missions")
    @GET("https://gateway.eneva.pixforcemaps.com/api/missions?page=0&size=10&order=id&direction=ASC")
    fun obterMissoes(@Header("Authorization") accessToken: String?): Call<Model?>?

    //@GET("https://gateway.eneva.pixforcemaps.com/api/missions?page=0&size=10&order=id&direction=ASC")
    //fun obterMissoes2(@Header("Authorization") accessToken: String?): Call<Model2?>?
}