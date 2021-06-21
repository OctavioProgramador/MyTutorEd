package com.example.gg


import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class activity_register : AppCompatActivity() {

    //Declaración variables
    private lateinit var txtUsename: EditText
    private lateinit var txtLastName: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    //private lateinit var progressBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var  checkBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Lamamos a nuestras vistas
        txtUsename=findViewById(R.id.txtUsername)
        txtLastName=findViewById(R.id.txtLastName)
        txtEmail=findViewById(R.id.txtEmail)
        txtPassword=findViewById(R.id.txtPassword)
        checkBox=findViewById(R.id.checkBox)

        //progressBar= ProgressBar(this)

        database= FirebaseDatabase.getInstance() //Instancia para BD
        auth= FirebaseAuth.getInstance() //Instancia para la autenticación

        dbReference=database.reference.child("User") //Referencia para leer o escribir en una ubicación en la BD


        //llamamos metodo onclick
        checkBox.setOnCheckedChangeListener({buttonView,isChecked ->
            if(isChecked){
                //si es seleccionado
                txtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
            }else{
                //sino
                txtPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            }

        })

    }

    fun btnRegistrarse(view: View) //onClick
    {
        crearCuenta()

    }

    //Crear cuenta
    private fun crearCuenta()
    {
        val username:String=txtUsename.text.toString()
        val lastname:String=txtLastName.text.toString()
        val email:String=txtEmail.text.toString()
        val password:String=txtPassword.text.toString()
        val idioma = Locale.getDefault().language
        // Confirmamos que los campos no esten vacios
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(lastname) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(
                password
            ))
        {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){ // Registro con Email y Contraseña
                    task ->
                if(task.isSuccessful) //Se registro correctamente
                {
                    val user:FirebaseUser?=auth.currentUser //puede que tenga valor nulo
                    verificarEmail(user)

                    //Dar de alta los otros datos en la BD
                    val userDB=dbReference.child(user?.uid.toString())
                    //val userBD=dbReference.child(user?.uid.toString())

                    userDB.child("Nombre").setValue(username)
                    userDB.child("Apellido").setValue(lastname)
                    userDB.child("Email").setValue(email)
                    userDB.child("Contraseña").setValue(password)
                   login()
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    if (idioma == "es") {
                        txtEmail.setError("Email no válido")
                    }else{
                        txtEmail.setError("Not valid Email")
                    }
                }
                else if(auth.currentUser != null)
                {
                    if (idioma == "es") {
                        Toast.makeText(this, "Usuario registrado.", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, "User registered.", Toast.LENGTH_LONG).show()
                    }
                }
                else
                {
                    if (idioma == "es") {
                        Toast.makeText(this, "¡Ups! Parece que tenemos algunos problemas.", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, "Ups! It seems we have some problems.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        else //Hay campos vacios
        {
            //Muestra en que parte esta el error
            if(TextUtils.isEmpty(username) || TextUtils.isEmpty(lastname) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
            {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Error")
                if(idioma == "es"){
                    builder.setMessage("¡Ups! Parece que no haz llenado todos tus datos")
                }else{
                    builder.setMessage("Ups! It seems that you have not filled all your data.")
                }
                builder.setPositiveButton("OK",null)
                val dialog:AlertDialog=builder.create()
                dialog.show()
                if(TextUtils.isEmpty(username))
                {
                    if(idioma == "es") {
                        txtUsename.setError("Ingrese nombre")
                    }else{
                        txtUsename.setError("Enter name")
                    }
                }
                if(TextUtils.isEmpty(lastname))
                {
                    if(idioma == "es") {
                        txtLastName.setError("Ingrese apellido")
                    }else{
                        txtLastName.setError("Enter last name")
                    }
                }
                if(TextUtils.isEmpty(email))
                {
                    if(idioma == "es") {
                        txtEmail.setError("Ingrese Email")
                    }else{
                        txtEmail.setError("Enter Email")
                    }
                }
                if(TextUtils.isEmpty(password))
                {
                    if(idioma == "es") {
                        txtPassword.setError("Ingrese contraseña")
                    }else{
                        txtPassword.setError("Enter password")
                    }
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    if(idioma == "es") {
                        txtEmail.setError("Email no válido")
                    }else{
                        txtEmail.setError("Not valid Email")
                    }
                }
            }
        }
    }
    // Si se registro correctamente abre Login
    private fun login()
    {
        startActivity(Intent(this, activity_login::class.java))
    }
    // Verificar Email
    private  fun verificarEmail(user: FirebaseUser?)
    {
        val idioma = Locale.getDefault().language
        user?.sendEmailVerification()?.addOnCompleteListener(this)
        { task ->
            if(task.isSuccessful)
            {
                if(idioma == "es") {
                    Toast.makeText(this, "Email enviado", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this, "Email sendend", Toast.LENGTH_LONG).show()
                }
            }else{
                if(idioma == "es") {
                    Toast.makeText(this, "Error al enviar Email", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this, "Error sending Email", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    //Abre ventana Login
    fun txtLogin(view: View)
    {
        startActivity(Intent(this, activity_login::class.java))
    }


}