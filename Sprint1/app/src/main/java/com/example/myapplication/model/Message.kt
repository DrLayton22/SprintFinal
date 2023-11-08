package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.communications.CommunicationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

data class Message(val username: String, val text: String)

object MessageManager {
    private val _messages = MutableLiveData<ArrayList<Message>>().apply {
        value = ArrayList()
    }

    val communicationManager = CommunicationManager("127.0.0.1")

    fun getMessageAt(position: Int): Message? {
        val messages = _messages.value
        return messages?.getOrNull(position)
    }

    fun getMessages(): MutableLiveData<ArrayList<Message>> {
        return _messages
    }

    fun add(username: String, text: String) {
        val newMessage = Message(username, text)
        val messages = _messages.value ?: ArrayList()
        messages.add(newMessage)
        _messages.value = messages
    }

    fun size(): Int {
        return _messages.value?.size ?: 0
    }

    fun sendMessage(message: Message) {
        // Crear un objeto JSON con el contenido del mensaje
        val jsonMessage = JSONObject().apply {
            put("username", message.username)
            put("text", message.text)
        }

        // Convertir el objeto JSON a String
        val jsonString = jsonMessage.toString()

        // Enviar el mensaje al servidor en un hilo separado utilizando una corrutina
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = communicationManager.sendServer(jsonString)
                // Aquí puedes manejar la respuesta del servidor si es necesario
            } catch (e: Exception) {
                e.printStackTrace()
                // Manejar cualquier error que ocurra durante el envío
            }
        }
    }
}
