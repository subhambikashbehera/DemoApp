package com.gietuportalofficial.demoapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gietuportalofficial.demoapp.Model.Model
import com.gietuportalofficial.demoapp.Player
import com.gietuportalofficial.demoapp.R

class AdapterXt: RecyclerView.Adapter<AdapterXt.Myviewholder> {

   var context:Context
   var datal= arrayListOf<Model>()

    constructor(context: Context, datal: ArrayList<Model>) : super() {
        this.context = context
        this.datal = datal
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        val view=LayoutInflater.from(context).inflate(R.layout.item_demo,parent,false)
        return Myviewholder(view)
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {

        val detail=datal[position]

        Glide
            .with(context)
            .load(detail.userImagex)
            .centerCrop()
            .into(holder.image)

        Glide
            .with(context)
            .load(detail.contentx)
            .centerCrop()
            .into(holder.mainImage)

        if (detail.posttypex=="1"){
            holder.feedchoice.visibility=View.GONE
        }else if (detail.posttypex=="2"){
            holder.feedchoice.visibility=View.VISIBLE
        }
        holder.favoriteCount.text=detail.likesx
        holder.msgCount.text=detail.commentsx
        holder.name.text=detail.userNamex

        holder.mainImage.setOnClickListener {
            val intent=Intent(context,Player::class.java)
            intent.putExtra("img",detail.contentx)
            intent.putExtra("type",detail.posttypex)
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return datal.size
    }

    inner class Myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.profile_img)
        var name: TextView = itemView.findViewById(R.id.txt_head)
        var caption:TextView = itemView.findViewById(R.id.txt_sub)
        var wpBtn:ImageView = itemView.findViewById(R.id.whatsapp)
        var sharebtn:ImageView = itemView.findViewById(R.id.share)
        var message:ImageView = itemView.findViewById(R.id.message)
        var save:ImageView = itemView.findViewById(R.id.save)
        var wpCount:TextView = itemView.findViewById(R.id.count_wp)
        var shareCount:TextView = itemView.findViewById(R.id.count_sh)
        var favoriteCount:TextView = itemView.findViewById(R.id.count_fav)
        var msgCount:TextView = itemView.findViewById(R.id.count_msg)
        var saveCount:TextView = itemView.findViewById(R.id.count_save)
        var mainImage:ImageView = itemView.findViewById(R.id.feed_data)
        var mainVideo: VideoView = itemView.findViewById(R.id.feed_data_video)
        var feedchoice=itemView.findViewById<LinearLayout>(R.id.feed_choice)
    }

}