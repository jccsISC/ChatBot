package com.jccsisc.chatbot.viewmodel

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jccsisc.chatbot.data.model.MessageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {
    private val BOT = 0
    private var _listChatBot = MutableLiveData<MutableList<MessageModel>>()
    private var handler: Handler = Handler()

    val listChat: LiveData<MutableList<MessageModel>>
    get() = _listChatBot

    init {
        _listChatBot.value = mutableListOf()
    }

    fun addMessage(messageModel: MessageModel) {
        val mutableList = _listChatBot.value!!
        mutableList.add(messageModel)
        _listChatBot.value = mutableList
    }

    fun responseBot() {
        val runnable = Runnable {
            val palabra = palabrasBot()
            val messageModel = MessageModel(BOT, palabra)
            val mutableList = _listChatBot.value!!
            mutableList.add(messageModel)
            _listChatBot.value = mutableList
        }
        handler.postDelayed(runnable, 2000)
    }

    fun palabrasBot(): String {
    val array = listOf("Si \uD83D\uDE0E", "No \uD83D\uDE12", "Pregunta de nuevo \uD83E\uDD28", "Es muy probable \uD83D\uDE01", "No lo creo \uD83E\uDD14", "No sé \uD83D\uDE13", "Tal vez \uD83D\uDE44", "Por supuesto \uD83D\uDE0F", "Claro que si \uD83E\uDD20")
        val palabra = array.random()
        return palabra
    }
}