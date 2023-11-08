package com.example.myapplication.view

import android.view.View
import android.widget.TextView
import com.example.myapplication.Message
import com.example.myapplication.MessageViewHolder
import com.example.myapplication.R
import java.text.SimpleDateFormat
import java.util.Date

class MessageFromOtherViewHolder(itemView: View) : MessageViewHolder(itemView) {
    private val usernameTextView: TextView = itemView.findViewById(R.id.msg_text_usuari)
    private val textTextView: TextView = itemView.findViewById(R.id.msg_other_text)
    private val horaTextView: TextView = itemView.findViewById(R.id.msg_other_timestamp)

    override fun bind(message: Message) {
        val dateFormat = SimpleDateFormat("HH:mm") // Formato de la hora
        val horaActual = Date() // Obtener la hora actual
        val horaFormateada = dateFormat.format(horaActual) // Formatear la hora actual

        usernameTextView.text = message.username
        textTextView.text = message.text
        horaTextView.text = horaFormateada
    }
}
