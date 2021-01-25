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
    private val USER = 1
    private var _listChatBot = MutableLiveData<MutableList<MessageModel>>()

    val listChat: LiveData<MutableList<MessageModel>>
    get() = _listChatBot

    init {
        viewModelScope.launch {
            _listChatBot.value = fetchChat(1, "")
        }
    }

    suspend fun fetchChat(user: Int, message: String): MutableList<MessageModel> {
        return withContext(Dispatchers.IO) {
            val chatList = mutableListOf<MessageModel>()
            chatList.add(MessageModel(user, message))
            chatBot()

            chatList
        }
    }

    private fun chatBot() {
        var list = mutableListOf<MessageModel>()
        val handler = Handler()
        handler.postDelayed(Runnable {
            val frase = palabrasBot()
            list.add(MessageModel(BOT, frase))
            _listChatBot.value = list
        }, 1000)
    }

//    fun chatBot() {
//        val handler = Handler()
//        handler.postDelayed(Runnable {
//            val palabra = palabrasBot()
//            listChatBot.add(MessageModel(BOT, palabra))
//            //para poder manibulas los views del hilo pincipal
//            runOnUiThread(Runnable {
//                adapter.notifyDataSetChanged()
//                binding.rvChat.layoutManager?.scrollToPosition(listChatBot.size - 1)
//            })
//        }, 2000)
//    }

    fun palabrasBot(): String {
        val array = listOf("Si", "No", "Pregunta de nuevo", "Es muy probable", "No lo creo", "No sé", "Tal vez", "Por supuesto", "Claro que si")
        val palabra = array.random()
        return palabra
    }
}