package com.example.gg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button;
import android.widget.TextView
import com.example.gg.ui.MainActivity
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*


class activity_login : AppCompatActivity() {
    //Declaración
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtEmail=findViewById(R.id.txtEmail)
        txtPassword=findViewById(R.id.txtPassword)
        auth= FirebaseAuth.getInstance()
    }
    //si el usuario da click a olvide contraseña
    fun forgotPassword(view: View)
    {
        startActivity(Intent(this,activity_forgotPassword::class.java))

    }
    //Quiere registrarse
    fun txtRegister(view: View)
    {
        startActivity(Intent(this,activity_register::class.java))

    }

    fun login(view: View)
    {
        loginUser()


    }
    //Autenticación de usuario
    private fun loginUser()
    {
        val email:String=txtEmail.text.toString()
        val password:String=txtPassword.text.toString()

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
        {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
                task ->

                if(task.isSuccessful){ ///Autenticación exitosa
                    action()
                }else{ //usuario no registrado
                    Toast.makeText(this, "Error en la autenticación", Toast.LENGTH_LONG).show()
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Error")
                    builder.setMessage("¡Ups! tengo problemas con la autenticación de usuario.")
                    builder.setPositiveButton("OK",null)
                    val dialog:AlertDialog=builder.create()
                    dialog.show()
                }
            }

        }
        else // Si hay campos vacios
        {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Error")
            builder.setMessage("¡Ups! Parece que no haz llenado todos tus datos.")
            builder.setPositiveButton("OK",null)
            val dialog: AlertDialog =builder.create()
            dialog.show()

            //Muestra en que parte esta el error
            if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password))
            {
                txtEmail.setError("Email");
                txtPassword.setError("Contraseña");
            }

        }
    }

    // Despliega el MainActivity (Chat)
    private fun action(){
        startActivity(Intent(this,MainActivity::class.java))
    }

}