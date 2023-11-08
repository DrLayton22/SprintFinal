package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.MessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.Inet4Address
import java.net.InetAddress
import java.net.UnknownHostException

class LoginViewModel(private val messageRepository: MessageRepository) : ViewModel() {
    private val _loginStatus = MutableLiveData<JSONObject>()
    val loginStatus = _loginStatus

    fun login() {
        val username = messageRepository.getUserName()
        val serverAddress = messageRepository.getServer()

        viewModelScope.launch(Dispatchers.IO) {
            val isUsernameValid = username.isNotEmpty()
            val isIPValid = isValidIPAddress(serverAddress)

            if (isUsernameValid && isIPValid) {
                val loginResponse = messageRepository.login()
                _loginStatus.postValue(loginResponse)
            } else {
                val validationError = JSONObject()
                if (!isUsernameValid) {
                    validationError.put("error", "Nombre de usuario no válido")
                }
                if (!isIPValid) {
                    validationError.put("error", "Dirección IP incorrecta")
                }
                _loginStatus.postValue(validationError)
            }
        }
    }


    private suspend fun isValidIPAddress(ipAddress: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val address = InetAddress.getByName(ipAddress)
            return@withContext address is Inet4Address // Asegurarse de que sea una dirección IPv4
        } catch (e: UnknownHostException) {
            return@withContext false
        }
    }



}

class LoginViewModelFactory(private val messageRepository: MessageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(messageRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

