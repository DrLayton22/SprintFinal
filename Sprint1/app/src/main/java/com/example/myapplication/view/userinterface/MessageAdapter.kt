package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Message
import com.example.myapplication.repository.MessageRepository
import com.example.myapplication.view.MessageFromOtherViewHolder

class MessageAdapter : RecyclerView.Adapter<MessageViewHolder>() {

    private val messageRepository = MessageRepository.getInstance()

    private val MESSAGE_FROM_USER = 1
    private val MESSAGE_FROM_OTHER = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return when (viewType) {
            MESSAGE_FROM_USER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.my_msg_viewholder, parent, false)
                MessageViewHolder(view)
            }
            MESSAGE_FROM_OTHER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.other_msg_viewholder, parent, false)
                MessageFromOtherViewHolder(view)
            }
            else -> throw IllegalArgumentException("ViewType not supported")
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messageRepository.getMessageAt(position)
        if (message != null) {
            if (getItemViewType(position) == MESSAGE_FROM_USER) {
                holder.bind(message)
            } else if (holder is MessageFromOtherViewHolder) {
                holder.bind(message)
            }
        }
    }

    override fun getItemCount(): Int {
        return messageRepository.getMessageCount()
    }

    override fun getItemViewType(position: Int): Int {
        val message = messageRepository.getMessageAt(position)
        return if (message?.username == messageRepository.getUserName()) {
            MESSAGE_FROM_USER
        } else {
            MESSAGE_FROM_OTHER
        }
    }
}




