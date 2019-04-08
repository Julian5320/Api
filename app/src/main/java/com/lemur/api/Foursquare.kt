package com.lemur.api

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.foursquare.android.nativeoauth.FoursquareOAuth

class Foursquare(var activity: AppCompatActivity) {

    private val  CODIGO_CONEXION = 200
    private val CODIGO_INTERCAMBIO = 201

    private val CLIENT_ID = "5AYDPDO4NSENH5XPJF3H3I5AENPWK5PABS0ZVK0M1ZVGFZOK"
    private val CLIENT_SECRET= "SI1IGIUHLIW2RNJ5VW1CZAVYCGFMJ1DTGSV0HDJSCO1L5ONZ"

    private val SETTINGS = "settings"
    init {

    }

    fun iniciarSecion(){
    val intent = FoursquareOAuth.getConnectIntent(activity.applicationContext,CLIENT_ID)
        if (FoursquareOAuth.isPlayStoreIntent(intent)){
            //Si no esta instalada mostrar mensaje
            Mensaje("no tienes la app")
            activity.startActivity(intent)
        }
        else{
            activity.startActivityForResult(intent, CODIGO_CONEXION)

        }
    }

    fun validarActivityResult(requestCode: Int, resultCode: Int, data: Intent? ){

        when(requestCode){
            CODIGO_CONEXION ->{conexionCompleta(resultCode,data)}

            CODIGO_INTERCAMBIO->{intercambioTokenCompleta(resultCode,data)}
        }
    }

    fun conexionCompleta(resultCode: Int, data: Intent?){
        val codigoRespuesta=FoursquareOAuth.getAuthCodeFromResult(resultCode,data)
        val exeption =  codigoRespuesta.exception
        if (exeption==null){
            //correct
            val codigo = codigoRespuesta.code
            realizarIntercambiToken(codigo)
        }
        else{
          Mensaje("no se pudo completar la conexion " +exeption.toString())
        }
    }

    private fun intercambioTokenCompleta(resultCode: Int, data: Intent?){
        val respuestaToken = FoursquareOAuth.getTokenFromResult(resultCode, data)
        val exeption = respuestaToken.exception

        if (exeption==null){
            val accessToken = respuestaToken.accessToken
            guardarToken(accessToken)
            Mensaje("token= "+accessToken)
            navergarSiguinete()
        }
        else
        {
            //hubo un problema
            Mensaje("No se puedo obtener Token")
        }
    }

    private fun realizarIntercambiToken(codigo:String){
        val intent = FoursquareOAuth.getTokenExchangeIntent(activity.applicationContext,CLIENT_ID,CLIENT_SECRET,codigo)
        activity.startActivityForResult(intent, CODIGO_INTERCAMBIO)
    }


    fun hayToken():Boolean{
        if (obtenerToken() == "")
        {
            return false
        }
        else
        {
            return true
        }
    }

    fun guardarToken(token:String){
        val settings = activity.getSharedPreferences(SETTINGS,0)
        val editor = settings.edit()
        editor.putString("accessToken", token)

        editor.commit()
    }

    fun obtenerToken():String{
        val setting = activity.getSharedPreferences(SETTINGS,0)
        val token = setting.getString("accessToken", "")
        return token
    }

    fun Mensaje(mensaje:String){
        Toast.makeText(activity.applicationContext, mensaje,Toast.LENGTH_SHORT).show()
    }
    fun navergarSiguinete(){
        activity.startActivity(Intent(activity,Dos::class.java))
        activity.finish()
    }
}

