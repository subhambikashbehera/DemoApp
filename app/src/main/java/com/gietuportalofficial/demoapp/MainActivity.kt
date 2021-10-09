package com.gietuportalofficial.demoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.gietuportalofficial.demoapp.Activity.Login
import com.gietuportalofficial.demoapp.Model.Model
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import java.time.LocalTime

class MainActivity : AppCompatActivity() {

    lateinit var drawerd:ImageView
    lateinit var DrawerLayout:DrawerLayout
    private lateinit var closedrawer:ImageButton



        lateinit var name:TextView
        lateinit var logout:MaterialButton
        lateinit var email:TextView
        lateinit var phonenumber:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawerview_layout)
        drawerd=findViewById(R.id.menu)
        DrawerLayout=findViewById(R.id.drawerlayout)
        closedrawer=findViewById(R.id.closedrawer)

        name=findViewById(R.id.fullname)
        email=findViewById(R.id.emailq)
        phonenumber=findViewById(R.id.phone)
        logout=findViewById(R.id.logout)


        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.tooolbars)
        setSupportActionBar(toolbar)
        val bottomNavigationView=findViewById<BottomNavigationView>(R.id.bottomview)
        val navController=findNavController(R.id.fragmentContainerView)
        val appBarConfiguration= AppBarConfiguration(setOf(R.id.home2,R.id.menuitem,R.id.addd,R.id.movie2,R.id.people))
       setupActionBarWithNavController(navController,appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.itemIconTintList = null



        drawerd.setOnClickListener {
            if (!DrawerLayout.isDrawerOpen(GravityCompat.START))
            {
                DrawerLayout.openDrawer(GravityCompat.START)
            }
        }
        closedrawer.setOnClickListener {
            if (DrawerLayout.isDrawerOpen(GravityCompat.START))
            {
                DrawerLayout.closeDrawer(GravityCompat.START)
            }
        }

       FirebaseDatabase.getInstance().getReference("profile").addValueEventListener(object:
           ValueEventListener {

           override fun onDataChange(snapshot: DataSnapshot) {
               val namex = snapshot.child("name").value
               val emailx=snapshot.child("email").value
               val phonex=snapshot.child("phone").value

               name.text=namex.toString()
               email.text=emailx.toString()
               phonenumber.text=phonex.toString()
           }

           override fun onCancelled(error: DatabaseError) {

           }

       })

        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent=Intent(this,Login::class.java)
            startActivity(intent)
            finish()
        }






    }





}