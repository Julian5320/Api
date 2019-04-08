package com.lemur.api.RecyclerView

import android.content.Context
import android.media.Rating
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lemur.api.R
import com.lemur.api.Venue
import com.squareup.picasso.Picasso

class AdaptadorCustom( items:ArrayList<Venue>, var listener: ClickListener): Adapter<AdaptadorCustom.ViewHolder>() {

    var items: ArrayList<Venue>? = null

    init {
        this.items = items
    }

    var contexto:Context?=null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val vista  = LayoutInflater.from(p0?.context).inflate(R.layout.template_venues,p0, false)
        contexto = p0.context
        val ViewHolder = ViewHolder(vista, listener)

        return  ViewHolder!!
    }

    override fun getItemCount(): Int {
        return items?.count()!!
    }
    var urlImagen:String=""
    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {

        val item = items?.get(p1)
        holder.nombre?.text = item?.name!!
        if (item?.categories?.size != 0) {
             urlImagen = item?.categories?.get(0)!!.icon?.prefix + "bg_64" + item?.categories?.get(0)!!.icon?.suffix
            Picasso.with(contexto).load(urlImagen).into(holder.foto)
        }



    }

    class ViewHolder(vista: View, listener: ClickListener):RecyclerView.ViewHolder(vista), View.OnClickListener{

        var vista = vista
        var foto:ImageView?=null
        var nombre : TextView?=null

        var listener : ClickListener?=null

        init {
            foto = vista.findViewById(R.id.ivFoto) as ImageView
            nombre = vista.findViewById(R.id.tvNombre) as TextView
            this.listener = listener
            vista.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.OnClick(v!!, adapterPosition)
        }

    }

}




