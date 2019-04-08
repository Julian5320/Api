package com.lemur.api

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    var fsq:Foursquare?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var boton = findViewById<Button>(R.id.boton)


        fsq = Foursquare(this)
        if (fsq?.hayToken()!!){
            startActivity(Intent(this,Dos::class.java))
            finish()}
        boton.setOnClickListener {
            fsq!!.iniciarSecion()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        fsq?.validarActivityResult(requestCode,resultCode,data)
    }
}
