package com.example.racoonsquash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnpong: Button= findViewById(R.id.btn_pong)
        val btnsquash: Button= findViewById(R.id.btn_squash)

        btnpong.setOnClickListener {
            val intent = Intent (this,PongActivity::class.java)
            startActivity(intent)
        }
        btnsquash.setOnClickListener {
            val intent = Intent (this,SquashActivity::class.java)
            startActivity(intent)
        }
    }


}