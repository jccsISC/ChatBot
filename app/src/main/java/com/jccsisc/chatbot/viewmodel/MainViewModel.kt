package com.jccsisc.chatbot.viewmodel

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jccsisc.chatbot.data.model.MessageModel

class MainViewModel: ViewModel() {
    private val BOT = 0
    private val USER = 1
    private val listChatBot = mutableListOf<MessageModel>()
    val listChat: LiveData<MutableList<MessageModel>>
    get() = getChat()


    fun getChat(): LiveData<MutableList<MessageModel>> {
        val mutableData = MutableLiveData<MutableList<MessageModel>>()
        val listData = mutableListOf<MessageModel>()

    }


    fun chatBot() {
        val handler = Handler()
        handler.postDelayed(Runnable {
            val palabra = palabrasBot()
            listChatBot.add(MessageModel(BOT, palabra))
            //para poder manibulas los views del hilo pincipal
            runOnUiThread(Runnable {
                adapter.notifyDataSetChanged()
                binding.rvChat.layoutManager?.scrollToPosition(listChatBot.size - 1)
            })
        }, 2000)
    }

    fun palabrasBot(): String {
        val array = listOf("Si", "No", "Pregunta de nuevo", "Es muy probable", "No lo creo", "No sé", "Tal vez", "Por supuesto", "Claro que si")
        val palabra = array.random()
        return palabra
    }
}