package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import java.net.Inet4Address
import java.net.InetAddress
import java.net.UnknownHostException
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.MessageRepository
import com.example.myapplication.viewmodel.LoginViewModel
import com.example.myapplication.viewmodel.LoginViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var nickNameEditText: EditText
    private lateinit var serverAddressEditText: EditText
    private lateinit var connectButton: Button
    private lateinit var viewModel: LoginViewModel // Agregar referencia al ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtener referencias a las vistas
        nickNameEditText = findViewById(R.id.nickNameText)
        serverAddressEditText = findViewById(R.id.serverAddressText)
        connectButton = findViewById(R.id.buttonConnect)

        // Crear una instancia de MessageRepository
        val messageRepository = MessageRepository.getInstance()

        // Crear una instancia de LoginViewModelFactory pasando el MessageRepository
        val viewModelFactory = LoginViewModelFactory(messageRepository)

        // Crear una instancia del ViewModel
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        // Obtener los valores de nombre de usuario y dirección IP del servidor desde las vistas de texto
        val username = nickNameEditText.text.toString()
        val server = serverAddressEditText.text.toString()

        connectButton.setOnClickListener {
            val username = nickNameEditText.text.toString()
            val server = serverAddressEditText.text.toString()
            // Verificar que los valores no estén vacíos
            if (username.isNotEmpty() && server.isNotEmpty()) {
                // Llamar al método para configurar usuario y servidor en MessageRepository
                messageRepository.configureUser(username, server)

                // Llamar al método login en el ViewModel
                viewModel.login()
            } else {
                // Mostrar un mensaje de error si faltan datos
                Snackbar.make(connectButton, "Nombre de usuario o dirección IP vacío", Snackbar.LENGTH_LONG).show()
            }
        }


        // Establecer un observador para la propiedad loginStatus del ViewModel
        viewModel.loginStatus.observe(this, Observer { loginStatus ->
            if (loginStatus != null) {
                if (loginStatus.has("error")) {
                    // Mostrar mensaje de error en caso de validación fallida
                    Snackbar.make(connectButton, loginStatus.getString("error"), Snackbar.LENGTH_LONG).show()
                } else {
                    // Datos válidos, abrir la ventana de mensajes
                    openMessageWindow(username, server)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun openMessageWindow(nickName: String, serverAddress: String) {
        // Crear un Intent para abrir la actividad MessagesWindow
        val intent = Intent(this, MessagesWindow::class.java)

        // Pasar los datos del nombre de usuario y la dirección del servidor al Intent
        intent.putExtra("nickName", nickName)
        intent.putExtra("serverAddress", serverAddress)

        // Iniciar la nueva actividad
        startActivity(intent)
    }
}

