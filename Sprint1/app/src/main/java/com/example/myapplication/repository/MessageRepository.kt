package com.example.myapplication.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Message
import com.example.myapplication.MessageManager
import com.example.myapplication.communications.CommunicationManager
import org.json.JSONObject

class MessageRepository private constructor() {

    private var username: String = ""
    private var server: String = ""
    val communicationManager = CommunicationManager("127.0.0.1")


    companion object {
        @Volatile
        private var instance: MessageRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: MessageRepository().also { instance = it }
            }
    }

    // Método para configurar el nombre de usuario y servidor
    fun configureUser(username: String, server: String) {
        this.username = username
        this.server = server
        Log.d("MessageRepository", "Username: $username")
        Log.d("MessageRepository", "Server IP: $server")
    }

    // Métodos para obtener la lista de mensajes y la cantidad de mensajes
    fun getMessageAt(position: Int): Message? {
        // Lógica para obtener un mensaje en una posición desde el modelo
        return MessageManager.getMessageAt(position)
    }

    fun getMessages(): MutableLiveData<ArrayList<Message>> {
        return MessageManager.getMessages()
    }

    fun getMessageCount(): Int {
        // Lógica para obtener la cantidad de mensajes desde el modelo
        return MessageManager.size()
    }

    // Método para agregar un mensaje
    fun addMessage(message: Message) {
        // Lógica para agregar un mensaje al modelo
        MessageManager.add(message.username, message.text)
    }

    // Método para enviar un mensaje al servidor
    suspend fun sendMessage(message: Message) {
        // Lógica para enviar un mensaje al servidor a través de CommunicationManager
        val jsonMessage = JSONObject().apply {
            put("username", message.username)
            put("text", message.text)
        }
        val jsonString = jsonMessage.toString()
        communicationManager.sendServer(jsonString)
    }

    // Método para iniciar sesión
    suspend fun login() : JSONObject {
        Log.d("MessageRepository", "Username: $username")
        Log.d("MessageRepository", "Server IP: $server")
        // Lógica para iniciar sesión utilizando CommunicationManager
        val loginResponse = communicationManager.login(username, server)
        // Devolver la respuesta del servidor
        return loginResponse
    }

    fun getUserName(): String {
        return  username
    }

    fun getServer(): String {
        return server
    }
}
