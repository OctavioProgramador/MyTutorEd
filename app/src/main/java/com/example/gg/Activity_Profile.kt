package com.example.gg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity__profile.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.profile_toolbar.*
import java.util.*

class Activity_Profile : AppCompatActivity() {
   // private val currenUser = FirebaseAuth.getInstance().currentUser
   //Declaración variables
   private lateinit var txtUsename: EditText
    private lateinit var txtLastName: EditText
    private lateinit var txtEmail: EditText
    //  private lateinit var txtPassword: EditText
    //private lateinit var progressBar: ProgressBar
    var dbReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null
    // private lateinit var auth: FirebaseAuth
    private lateinit var userID:String
    private val TAG = "Profile"
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity__profile)
        back.setOnClickListener {
            onBackPressed()
        }
        database= FirebaseDatabase.getInstance() //Instancia para BD
        auth= FirebaseAuth.getInstance() //Instancia para la autenticación
        dbReference=database?.reference!!.child("User") //Referencia para leer o escribir en una ubicación en la BD
        val user=auth.currentUser
        val userreference=dbReference?.child(user?.uid!!)
        mostrar()
    }
    fun btnActualizar(view: View) //onClick
    {
        actualizar()
    }
    private fun mostrar()
    {
        val user=auth.currentUser
        val userreference=dbReference?.child(user?.uid!!)
        txtUsename=findViewById(R.id.txtUsername)
        txtLastName=findViewById(R.id.txtLastName)
        txtEmail=findViewById(R.id.txtEmail)
        txtEmail.setText(user.email)
        userreference?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                txtUsename.setText(snapshot.child("Nombre").value.toString())
                txtLastName.setText(snapshot.child("Apellido").value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun actualizar()
    {
        val idioma = Locale.getDefault().language
        val user=auth.currentUser
        val userreference=dbReference?.child(user?.uid!!)
        txtUsename=findViewById(R.id.txtUsername)
        txtLastName=findViewById(R.id.txtLastName)
        txtEmail=findViewById(R.id.txtEmail)
        database= FirebaseDatabase.getInstance() //Instancia para BD
        auth= FirebaseAuth.getInstance() //Instancia para la autenticación
        dbReference=database?.reference!!.child("User")
        //val userDB=dbReference.child(user?.uid.toString())
        val username:String=txtUsename.text.toString()
        val lastname:String=txtLastName.text.toString()

        // Confirmamos que los campos no esten vacios
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(lastname)) {

            userreference?.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    userreference.child("Nombre").setValue(username);
                    userreference.child("Apellido").setValue(lastname);


                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
            if (idioma == "es") {
                Toast.makeText(this, "Cambios guardados exitosamente.", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "Changes saved successfully", Toast.LENGTH_LONG).show()
            }

        }
        else
        {
            if(idioma == "es") {
                Toast.makeText(this, "¡Ups! Parece que no hemos podido guardar los cambios.", Toast.LENGTH_LONG).show()
                if (TextUtils.isEmpty(username)) {
                    txtUsename.setError("Ingrese nombre")
                }
                if (TextUtils.isEmpty(lastname)) {
                    txtLastName.setError("Ingrese apellido")
                }
            }else{
                Toast.makeText(this, "Ups! It seems that we have not been able to save the changes.", Toast.LENGTH_LONG).show()
                if (TextUtils.isEmpty(username)) {
                    txtUsename.setError("Enter name")
                }
                if (TextUtils.isEmpty(lastname)) {
                    txtLastName.setError("Enter last name")
                }
            }
        }

/*
        userreference?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userreference.child("Nombre").setValue(username);
                userreference.child("Apellido").setValue(lastname);


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })*/





    }
}