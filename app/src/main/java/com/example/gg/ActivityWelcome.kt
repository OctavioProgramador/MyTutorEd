package com.example.gg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ActivityWelcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }

    fun register(view: View)
    {
        startActivity(Intent(this,activity_register::class.java))
    }

    fun login(view: View)
    {
        startActivity(Intent(this,activity_login::class.java))
    }
}