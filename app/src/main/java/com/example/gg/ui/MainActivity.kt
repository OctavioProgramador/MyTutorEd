package com.example.gg.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gg.R
import com.example.gg.data.Message
import com.example.gg.utils.BotResponse
import com.example.gg.utils.Constants.OPEN_GOOGLE
import com.example.gg.utils.Constants.OPEN_SEARCH
import com.example.gg.utils.Constants.RECEIVE_ID
import com.example.gg.utils.Constants.SEND_ID
import com.example.gg.utils.Time
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var  adapter: MessagingAdapter
    // Nombres a los que el bot se identifica
    private var botList = listOf("Ed", "Profesor Ed", "Super Ed", "Mr. Ed")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the toolbar as support action bar
        setSupportActionBar(toolbar as androidx.appcompat.widget.Toolbar?)

        loadLocate()
        recyclerView()

        clickEvents()


        val random = (0..3).random()
        customMessage("Hola!!! hoy estas hablando con ${botList[random]} como te puedo ayudar?")
    }

    private fun clickEvents() {
        btn_send.setOnClickListener{
            sendMessage()
        }

        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(1000)
                withContext(Dispatchers.Main){
                    rv_messages.scrollToPosition(adapter.itemCount - 1)
                }

            }
        }
    }

    private fun recyclerView() {
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun sendMessage(){
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()) {
            //
            et_message.setText("")

            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)
        }
    }


    private fun botResponse(message: String) {

        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            delay(1000)

            withContext(Dispatchers.Main){

                val response = BotResponse.basicResponse(message)

                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount - 1)

                when (response) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent(ACTION_VIEW))
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }

                    OPEN_SEARCH -> {
                        val site = Intent(Intent(ACTION_VIEW))
                        val searchTerm: String? = message.substringAfter("busca")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)

                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun customMessage(message: String) {
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val timeStamp = Time.timeStamp()
                adapter.insertMessage(Message(message, RECEIVE_ID, timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    // Damos permisos para implementar menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    // Definimos que hace cada opcion del menu
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.itemConfiguraciones -> {

                true
            }
            R.id.itemSalir -> {
                LogOut()
                true
            }
            R.id.itemIdiomaEsp -> {
                CambiarIdiomaEsp()
                true
            }
            R.id.itemIdiomaIng -> {
                CambiarIdiomaIngles()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun CambiarIdiomaIngles() {
        setLocate("en")
    }

    private fun CambiarIdiomaEsp(){
        setLocate("es")
    }

    private fun setLocate(lang: String?){
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()
    }

    private fun loadLocate(){
        val sharedPreferences = getSharedPreferences("Settigns", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        setLocate(language)
    }


    //Funcion que gestiona el logout
    private fun LogOut(){
        FirebaseAuth.getInstance().signOut()
        onBackPressed()
    }
}