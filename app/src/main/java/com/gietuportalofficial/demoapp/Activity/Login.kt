package com.gietuportalofficial.demoapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.gietuportalofficial.demoapp.MainActivity
import com.gietuportalofficial.demoapp.R
import com.gietuportalofficial.demoapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login)

        auth = Firebase.auth
        firebaseDatabase= FirebaseDatabase.getInstance()


        val currentUser = auth.currentUser
        if (currentUser !=null)
        {
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.login.setTextColor(resources.getColor(R.color.red))
        binding.login.setOnClickListener {
            binding.login.setTextColor(resources.getColor(R.color.red))
            binding.signup.setTextColor(resources.getColor(R.color.black))
            binding.loginlayout.visibility= View.VISIBLE
            binding.signuplayout.visibility= View.GONE
        }
        binding.signup.setOnClickListener {
            binding.signup.setTextColor(resources.getColor(R.color.red))
            binding.login.setTextColor(resources.getColor(R.color.black))
            binding.loginlayout.visibility= View.GONE
            binding.signuplayout.visibility= View.VISIBLE
        }


        binding.loginbtn.setOnClickListener {
            val emailX=binding.Email.text.toString().trim()
            val passwordX=binding.Password1.text.toString().trim()
            if (TextUtils.isEmpty(emailX))
            {
                binding.Email.error="required"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(passwordX))
            {
                binding.Password1.error="required"
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(emailX,passwordX).addOnCompleteListener {task->
                if (task.isSuccessful)
                {
                    Toast.makeText(this, "Signed In", Toast.LENGTH_SHORT).show()
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                   finish()
                }else
                {
                    Toast.makeText(this,"${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }


        }
        binding.createbtn.setOnClickListener {
            val emailX=binding.Email1.text.toString().trim()
            val passwordX=binding.Password.text.toString().trim()
            val name=binding.name.text.toString().trim()
            val phone=binding.phone.text.toString().trim()


            if (TextUtils.isEmpty(emailX))
            {
                binding.Email1.error="required"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(passwordX))
            {
                binding.Password.error="required"
                return@setOnClickListener
            }
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailX,passwordX).addOnCompleteListener { task->
                if (task.isSuccessful)
                {
                    val map= hashMapOf<String,String>()
                    map["email"] = emailX
                    map["password"]=passwordX
                    map["name"]=name
                    map["phone"]=phone
                    firebaseDatabase.getReference("profile").setValue(map)
                    Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show()
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }else
                {
                    Toast.makeText(this,"${task.exception?.message}",Toast.LENGTH_LONG).show()
                }
            }
        }

    }

}