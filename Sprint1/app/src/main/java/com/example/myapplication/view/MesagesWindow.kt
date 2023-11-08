package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.viewmodel.MessagesViewModel
import com.google.android.material.textfield.TextInputEditText

class MessagesWindow : AppCompatActivity() {

    private lateinit var connectionInfoTextView: TextView
    private lateinit var messageText: TextInputEditText
    private lateinit var sendMessageButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageViewModel: MessagesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages_window)

        recyclerView = findViewById(R.id.messagesRecyclerView)
        messageText = findViewById(R.id.MessageText)
        sendMessageButton = findViewById(R.id.sendMessage)

        // Configurar el RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Inicializar el ViewModel utilizando ViewModelProvider
        messageViewModel = ViewModelProvider(this).get(MessagesViewModel::class.java)

        // Establecer un adaptador para el RecyclerView desde el ViewModel
        val adapter = messageViewModel.adapterLiveData.value
        recyclerView.adapter = adapter

        // Recuperar datos del Intent
        val intent = intent
        val nickName = intent.getStringExtra("nickName")
        val serverAddress = intent.getStringExtra("serverAddress")

        // Configurar el TextView de conexión
        connectionInfoTextView = findViewById(R.id.connectionInfoTextView)
        connectionInfoTextView.text = "Conectado a $serverAddress como $nickName"

        // Configurar el botón de envío
        sendMessageButton.setOnClickListener {
            // Obtener el texto del mensaje
            val messageTextString = messageText.text.toString()

            // Comprobar si el mensaje no está vacío
            if (messageTextString.isNotEmpty()) {
                // Crear un objeto Message con el nombre de usuario y el texto
                val message = Message(nickName ?: "Pep", messageTextString)
                // Agregar el mensaje al ViewModel
                messageViewModel.addMessage(message)

                // Borrar el texto del mensaje
                messageText.text?.clear()
            }
        }

        // Observador para hacer scroll al último mensaje
        messageViewModel.lastInsertedPositionLiveData.observe(this, { lastPosition ->
            recyclerView.smoothScrollToPosition(lastPosition)
        })
    }
}
