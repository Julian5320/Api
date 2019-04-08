package com.lemur.api

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.gson.Gson
import com.lemur.api.RecyclerView.AdaptadorCustom
import com.lemur.api.RecyclerView.ClickListener

class Dos : AppCompatActivity() {

    var lista : RecyclerView?=null
    var adaptador:AdaptadorCustom?=null
    var layoutManager: RecyclerView.LayoutManager? = null
    var fsq:Foursquare? = null

    private  val permisoFineLocation = android.Manifest.permission.ACCESS_FINE_LOCATION

    private  val permisoCoarseLocation = android.Manifest.permission.ACCESS_COARSE_LOCATION

    private  val CODIGO_SOLICITUD_PERMISO = 100

    var fusedLocatioClient: FusedLocationProviderClient? = null

    var locationRequest:LocationRequest?=null

    private var callback:LocationCallback?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dos)



         fsq = Foursquare(this)


        lista = findViewById(R.id.lista)
        lista?.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(this)
        lista?.layoutManager = layoutManager

        fusedLocatioClient = FusedLocationProviderClient(this)
        inicializarLocationRequest()

        callback = object : LocationCallback(){
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)
                for (ubicacion in p0?.locations!!){
                    Toast.makeText(applicationContext, ubicacion?.latitude.toString() + " - " + ubicacion.longitude.toString(),Toast.LENGTH_SHORT).show()
                    obtenerLugares(ubicacion.latitude.toString(),ubicacion.longitude.toString())
                }
            }
        }







    }

    fun obtenerLugares(lon:String, lat:String){
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.foursquare.com/v2/venues/search?ll="+lon+","+lat+"&oauth_token="+fsq?.obtenerToken()+"&v=20190317"
        Log.d("Link",url)
        val solicitud = StringRequest(Request.Method.GET, url, Response.Listener<String>{
            response ->
            Log.d("RESPONSE HTTP", response)

            val gson =Gson()
            val venues = gson.fromJson(response, FoursquareRequest::class.java)


            Log.d("Venues", venues.response?.venues?.size.toString())
            adaptador = AdaptadorCustom(venues.response?.venues!!, object :ClickListener{
                override fun OnClick(vista: View, index:Int){

                }
            })
            lista?.adapter = adaptador
        },Response.ErrorListener{

        })

        queue.add(solicitud)
    }

    private fun inicializarLocationRequest(){
        locationRequest = LocationRequest()
        locationRequest?.interval = 10000
        locationRequest?.fastestInterval = 5000
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }


    private fun validarPermisosUbicacion():Boolean{
        val hayUbicacionPrecisa = ActivityCompat.checkSelfPermission(this, permisoFineLocation) == PackageManager.PERMISSION_GRANTED
        val hayUbicacionOrdinaria = ActivityCompat.checkSelfPermission(this, permisoCoarseLocation) == PackageManager.PERMISSION_GRANTED
        return  hayUbicacionPrecisa && hayUbicacionOrdinaria

    }

    @SuppressLint("MissingPermission")
    private fun obtenerUbicacion(){

        /* fusedLocatioClient?.lastLocation?.addOnSuccessListener(this, object : OnSuccessListener<Location>{
             override fun onSuccess(p0: Location?) {
                 if (p0 != null){
                     Toast.makeText(applicationContext, p0?.latitude.toString() + " - " + p0.longitude.toString(),Toast.LENGTH_SHORT).show()
                 }
             }

         })*/


        fusedLocatioClient?.requestLocationUpdates(locationRequest, callback, null)
    }

    private fun pedirPermisos() {
        val racional = ActivityCompat.shouldShowRequestPermissionRationale(this, permisoFineLocation)

        if (racional) {
            //mandar mensaje con explicacion
            solicitudPermiso();
        } else {
            solicitudPermiso();
        }
    }
    private fun solicitudPermiso(){
        ActivityCompat.requestPermissions(this,arrayOf(permisoFineLocation,permisoCoarseLocation), CODIGO_SOLICITUD_PERMISO)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            CODIGO_SOLICITUD_PERMISO ->{
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    obtenerUbicacion()
                }
                else
                {
                    Toast.makeText(this,"No diste permiso",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (validarPermisosUbicacion ()){
            obtenerUbicacion()
        }
        else
        {
            pedirPermisos()
        }
    }
}


