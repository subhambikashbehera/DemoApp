package com.gietuportalofficial.demoapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.ProgressBar
import android.widget.VideoView
import com.bumptech.glide.Glide
import java.util.*

class Player : AppCompatActivity() {

    lateinit var img: ImageView
    lateinit var video: VideoView
    lateinit var progressBar: ProgressBar

    lateinit var mediaController: MediaController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        img = findViewById(R.id.img_full)
        video=findViewById(R.id.video_full)
        progressBar=findViewById(R.id.progressBar5)


        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val imgLink = intent.getStringExtra("img")
        val type=intent.getStringExtra("type")

//        Log.d("subhamdata", "onCreate: ${imgLink}")
//        Log.d("subhamdata", "onCreate: ${type}")

        when (type) {
            "1" -> {
                video.visibility=View.GONE
                progressBar.visibility=View.GONE
                Glide
                    .with(this)
                    .load(imgLink)
                    .placeholder(R.drawable.photo)
                    .into(img)
            }
            "2" -> {

                video.setVideoURI(Uri.parse(imgLink))
                mediaController= MediaController(this)
                mediaController.setAnchorView(mediaController)
                video.setMediaController(mediaController)

                video.setOnPreparedListener {
                    progressBar.visibility=View.GONE
                    video.start()
                }

            }
            else -> {
                video.visibility=View.GONE
                progressBar.visibility=View.GONE
                Glide
                    .with(this)
                    .load(imgLink)
                    .placeholder(R.drawable.photo)
                    .into(img)
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}