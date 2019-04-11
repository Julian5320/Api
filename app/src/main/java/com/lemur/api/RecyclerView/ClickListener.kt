package com.lemur.api.RecyclerView

import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.view.View

interface ClickListener {
    fun OnClick(vista: View, index:Int){

    }
}