package com.example.myapplication.communications

import android.util.Log
import com.example.myapplication.Message
import com.example.myapplication.MessageManager
import com.example.myapplication.repository.MessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class CommunicationManager(private val serverAddress: String) {
    private val port = 9999 // Puerto del servidor
    private var listenPort: Int? = null


    suspend fun sendServer(msg: String): JSONObject {
        return withContext(Dispatchers.IO) {
            try {
                val socket = Socket(serverAddress, port)
                val writer = PrintWriter(socket.getOutputStream(), true)
                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

                writer.println(msg)
                writer.flush()

                val response = reader.readLine()

                reader.close()
                writer.close()
                socket.close()

                val jsonResponse = JSONObject(response)
                Log.d("MessageRepository", "Mensaje agregado:"+ jsonResponse)


                // Verificar si la respuesta contiene un mensaje entrante y procesarlo
                if (jsonResponse.has("type") && jsonResponse.getString("type") == "message") {
                    val username = jsonResponse.getString("username")
                    val text = jsonResponse.getString("text")
                    // Agregar el mensaje al MessageRepository para mostrarlo en la aplicación
                    val message = Message(username, text)
                    MessageRepository.getInstance().addMessage(message)
                }

                jsonResponse
            } catch (e: IOException) {
                JSONObject().put("status", "error")
            }
        }
    }

    suspend fun prepareListener() {
        listenPort = findFreePort()
        GlobalScope.launch(Dispatchers.IO) {
            val serverSocket = ServerSocket(listenPort!!)

            while (true) {
                val clientSocket = serverSocket.accept()
                val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))

                val notification = reader.readLine()

                processNotification(notification)

                val response = JSONObject().put("status", "ok")
                val writer = PrintWriter(clientSocket.getOutputStream(), true)
                writer.println(response.toString())

                writer.close()
                reader.close()
                clientSocket.close()
            }
        }
    }

    private fun processNotification(notification: String) {
        try {
            val json = JSONObject(notification)
            val type = json.getString("type")

            if (type == "message") {
                val username = json.getString("username")
                val text = json.getString("text")
                // Añadir el nuevo mensaje a la lista de mensajes
                MessageManager.add(username, text)
            }
            // Manejar otros tipos de notificaciones si es necesario
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    // Function to find a free port
    private fun findFreePort(): Int {
        val serverSocket = ServerSocket(0)
        val port = serverSocket.localPort
        serverSocket.close()
        return port
    }

    suspend fun login(username: String, password: String): JSONObject {
        val response = JSONObject()
        response.put("status", "error")

        try {
            prepareListener()

            val msg = JSONObject()
            msg.put("command", "register")
            msg.put("user", username)
            msg.put("listenPort", listenPort)

            val serverResponse = sendServer(msg.toString())

            if (serverResponse.has("status") && serverResponse.getString("status") == "ok") {
                response.put("status", "ok")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return response
    }
}
