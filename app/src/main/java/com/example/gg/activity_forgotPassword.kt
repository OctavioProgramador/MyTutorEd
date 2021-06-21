package com.example.gg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class activity_forgotPassword : AppCompatActivity() {

    private lateinit var txtEmail: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        txtEmail = findViewById(R.id.txtEmail)
        auth= FirebaseAuth.getInstance()
    }

    fun send(view:View)
    {
        val email=txtEmail.text.toString()

        if(!TextUtils.isEmpty(email))
        {
            auth.sendPasswordResetEmail(email).addOnCompleteListener(this){
                task ->

                if(task.isSuccessful){

                    Toast.makeText(this, "Te he enviado un correo electrónico.", Toast.LENGTH_LONG).show()
                }
                else //No se envia Email
                {

                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                    {
                        txtEmail.setError("Email no válido")
                    }
                    else if(auth.currentUser == null)
                    {
                        Toast.makeText(this, "Usuario no registrado.", Toast.LENGTH_LONG).show()
                    }
                    else
                    {
                        Toast.makeText(this, "¡Ups! No me es posible contactarme contigo.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        if(TextUtils.isEmpty(email))
        {
            txtEmail.setError("Ingresar Email");
        }

    }

    fun login(view: View) {
        startActivity(Intent(this,activity_login::class.java))
        finish()
    }
}