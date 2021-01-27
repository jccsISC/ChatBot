package com.jccsisc.chatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jccsisc.chatbot.data.model.MessageModel
import com.jccsisc.chatbot.databinding.ActivityMainBinding
import com.jccsisc.chatbot.ui.adapter.ChatAdapter
import com.jccsisc.chatbot.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private val adapter = ChatAdapter()

    private val listChatBot = mutableListOf<MessageModel>()
    private val USER = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            rvChat.adapter = adapter

            viewModel.listChat.observe(this@MainActivity, Observer {
                adapter.submitList(it)
                rvChat.layoutManager?.scrollToPosition(listChatBot.size - 1)
                if (it.isEmpty()) viewEmpty.visibility = View.VISIBLE else viewEmpty.visibility = View.GONE
            })

            setUpSendMessageLayout(binding)
        }
    }

    private fun setUpSendMessageLayout(binding: ActivityMainBinding) = with(binding) {
       imvSend.setOnClickListener {
           val messge = edtMessage.text.toString()
           if (messge.isNotEmpty()) {
               val messageModel = MessageModel(USER, messge)
               viewModel.addMessage(messageModel)
               viewModel.responseBot()
               edtMessage.setText("")
               edtMessage.setHintTextColor(getColor(R.color.gray))
               viewEmpty.visibility = View.GONE
           } else {
               edtMessage.setHintTextColor(getColor(R.color.red))
               Toast.makeText(this@MainActivity, "Ingrese un mensaje \uD83D\uDE12", Toast.LENGTH_SHORT).show()
           }
       }
    }
}