package com.example.myapplication.viewmodel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Message
import com.example.myapplication.MessageAdapter
import com.example.myapplication.repository.MessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MessagesViewModel : ViewModel() {
    // Referencia al repositorio
    private val repository = MessageRepository.getInstance()

    // LiveData para el adaptador del RecyclerView
    private val _adapterLiveData = MutableLiveData<MessageAdapter>()
    val adapterLiveData: LiveData<MessageAdapter>
        get() = _adapterLiveData

    // LiveData para la posición del último elemento
    private val _lastInsertedPositionLiveData = MutableLiveData<Int>()
    val lastInsertedPositionLiveData: LiveData<Int>
        get() = _lastInsertedPositionLiveData

    // LiveData para la lista de mensajes
    val messageList: LiveData<ArrayList<Message>> by lazy {
        repository.getMessages()
    }

    // Método para agregar un mensaje
    fun addMessage(msg: Message) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                repository.sendMessage(msg)
            }
        }
    }

    // Método para obtener el nombre de usuario
    fun getUserName(): String {
        return repository.getUserName()
    }

    // Método para obtener el servidor
    fun getServer(): String {
        return repository.getServer()
    }
}
