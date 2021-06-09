package com.example.gg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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
                    //Mensaje de correo enviado
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Aviso")
                    builder.setMessage("Te he enviado un correo electrónico.")
                    builder.setPositiveButton("OK",null)
                    val dialog:AlertDialog=builder.create()
                    dialog.show()

                    // Regresa a la ventana Login
                   // startActivity(Intent(this,activity_login::class.java))
                }
                else //No se envia Email
                {
                    Toast.makeText(this, "Error al enviar Email", Toast.LENGTH_LONG).show()
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Error")
                    builder.setMessage("¡Ups! Parece que no me es posible comunicarme contigo.")
                    builder.setPositiveButton("OK",null)
                    val dialog: AlertDialog =builder.create()
                    dialog.show()
                }
            }
        }
        if(TextUtils.isEmpty(email))
        {
            txtEmail.setError("Email");
        }
    }

    fun login(view: View) {
        startActivity(Intent(this,activity_login::class.java))
    }
}