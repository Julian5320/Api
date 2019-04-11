package com.lemur.api.RecyclerView

import android.content.Context
import android.content.Intent
import android.media.Rating
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.lemur.api.Locations
import com.lemur.api.R
import com.lemur.api.Venue
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.template_venues.view.*
import org.w3c.dom.Text


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
        holder.direccion?.text = item?.location?.address
        var link = "https://maps.google.com/?q="+item.location?.lat+","+item.location?.lng
        holder.link?.text = link
        //holder.longitud = item.lng

        if (item?.categories?.size != 0) {
             urlImagen = item?.categories?.get(0)!!.icon?.prefix + "bg_64" + item?.categories?.get(0)!!.icon?.suffix
            Picasso.with(contexto).load(urlImagen).into(holder.foto)
            holder.info?.text = item?.categories?.get(0)!!.name

        }



    }

    class ViewHolder(vista: View, listener: ClickListener):RecyclerView.ViewHolder(vista), View.OnClickListener{

        var vista = vista
        var foto:ImageView?=null
        var nombre : TextView?=null
        var direccion:TextView? = null
        var info:TextView?=null
        var link: TextView?=null

        //var longitud:Float = 0.0f

        var listener : ClickListener?=null

        init {
            link = vista.findViewById(R.id.link) as TextView
            foto = vista.findViewById(R.id.ivFoto) as ImageView
            nombre = vista.findViewById(R.id.tvNombre) as TextView
            this.listener = listener
            direccion = vista.findViewById(R.id.address) as TextView
            info = vista.findViewById(R.id.info) as TextView
            vista.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            this.listener?.OnClick(v!!, adapterPosition)

        }



    }


}




// {} es un objeto y [] es un array