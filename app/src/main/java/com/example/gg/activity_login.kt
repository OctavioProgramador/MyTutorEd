package com.example.gg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import com.example.gg.ui.MainActivity
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import android.text.InputType
import android.text.TextWatcher
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.ActionCodeEmailInfo
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class activity_login : AppCompatActivity() {
    //Declaración
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var checkBox: CheckBox

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        auth = FirebaseAuth.getInstance()
        checkBox = findViewById(R.id.checkBox)

        //llamamos metodo onclick
        checkBox.setOnCheckedChangeListener({ buttonView, isChecked ->
            if (isChecked) {
                //si es seleccionado
                txtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
            } else {
                //sino
                txtPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            }

        })


    }

    //si el usuario da click a olvide contraseña
    fun forgotPassword(view: View) {
        startActivity(Intent(this, activity_forgotPassword::class.java))

    }

    //Quiere registrarse
    fun txtRegister(view: View) {
        startActivity(Intent(this, activity_register::class.java))

    }

    fun login(view: View) {
        loginUser()


    }

    //Autenticación de usuario
    private fun loginUser() {
        val email: String = txtEmail.text.toString()
        val password: String = txtPassword.text.toString()
        val idioma = Locale.getDefault().language
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->

                if (task.isSuccessful) { ///Autenticación exitosa
                    action()
                } else { //usuario no registrado

                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        if(idioma == "es") {
                            txtEmail.setError("Email no válido")
                        }else{
                            txtEmail.setError("Invalid Email")
                        }
                    }
                    else if(auth.currentUser == null)
                    {
                        if (idioma == "es") {
                            Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(this, "Unregistered User", Toast.LENGTH_LONG).show()
                        }

                    }
                    else {
                        /*Toast.makeText(this, "Error en la autenticación", Toast.LENGTH_LONG).show()
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("Error")
                        builder.setMessage("¡Ups! tengo problemas, verifica tu Email y contraseña.")
                        builder.setPositiveButton("OK", null)
                        val dialog: AlertDialog = builder.create()
                        dialog.show()*/
                            if(idioma == "es") {
                                Toast.makeText(this, "¡Ups! Verifica tu Email y contraseña", Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(this, "Ups! verify your email and password", Toast.LENGTH_LONG).show()
                            }
                    }
                }
            }

        } else // Si hay campos vacios
        {
            //Muestra en que parte esta el error
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                if (idioma == "es") {
                    Toast.makeText(this, "¡Ups! Parece que no has llenado todos tus datos", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this, "Ups! It seems that you have not filled all the data", Toast.LENGTH_LONG).show()
                }
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    if (idioma == "es") {
                        txtEmail.setError("Ingresa Email")
                        txtPassword.setError("Ingresa contraseña")
                    }else{
                        txtEmail.setError("Enter Email")
                        txtPassword.setError("Enter password")
                    }
                }
                else if (TextUtils.isEmpty(email)) {
                    if(idioma == "es") {
                        txtPassword.setError("Ingresa Email")
                    }else{
                        txtPassword.setError("Enter Email")
                    }
                }
                else if (TextUtils.isEmpty(password)) {
                    if(idioma == "es") {
                        txtPassword.setError("Ingresa contraseña")
                        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            txtEmail.setError("Email no válido")
                            txtPassword.setError("Ingresa contraseña")
                        }
                    }else{
                        txtPassword.setError("Enter password")
                        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            txtEmail.setError("Invalid Email")
                            txtPassword.setError("Enter password")
                        }
                    }
                }
                else
                {

                }
            }
            // checking the proper email format
        }
    }
    // Despliega el MainActivity (Chat)
    private fun action() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}