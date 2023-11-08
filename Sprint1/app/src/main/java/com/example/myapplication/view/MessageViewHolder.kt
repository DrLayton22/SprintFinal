package com.example.myapplication

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Message
import java.text.SimpleDateFormat
import java.util.Date

open class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val usernameTextView: TextView = itemView.findViewById(R.id.msg_text)
    private val textTextView: TextView = itemView.findViewById(R.id.msg_text)
    private val horaTextView: TextView = itemView.findViewById(R.id.msg_me_timestamp)

    open fun bind(message: Message) {
        val dateFormat = SimpleDateFormat("HH:mm") // Formato de la hora
        val horaActual = Date() // Obtener la hora actual
        val horaFormatada = dateFormat.format(horaActual) // Formatear la hora actual

        usernameTextView.text = message.username
        textTextView.text = message.text
        horaTextView.text = horaFormatada
    }
}
