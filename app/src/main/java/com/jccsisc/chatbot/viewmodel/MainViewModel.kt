package com.jccsisc.chatbot.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jccsisc.chatbot.data.model.MessageModel

class MainViewModel: ViewModel() {

    private var _listChat: MutableLiveData<MessageModel> = MutableLiveData()



//    fun getChat(): LiveData<MutableList<MessageModel>> {
//        val mutableData = MutableLiveData<MutableList<MessageModel>>()
//
//
//    }
}