package com.jccsisc.chatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            rvChat.adapter = adapter

            viewModel.listChat.observe(this@MainActivity, Observer {
                adapter.submitList(it)

                rvChat.layoutManager?.scrollToPosition(it.size - 1)
                if (it.isEmpty()) viewEmpty.visibility = View.VISIBLE else viewEmpty.visibility = View.GONE
            })

            setUpSendMessageLayout(binding)
        }
    }

    private fun setUpSendMessageLayout(binding: ActivityMainBinding) = with(binding) {
       imvSend.setOnClickListener {
           val messge = edtMessage.text.toString()
           if (messge.isNotEmpty()) {
               val messageModel = MessageModel(1, messge)
               viewModel.addMessage(messageModel)
               viewModel.responseBot()
               edtMessage.setText("")
               edtMessage.setHintTextColor(getColor(R.color.gray))
               linearLayout.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.background_text)
               viewEmpty.visibility = View.GONE
           } else {
               edtMessage.setHintTextColor(getColor(R.color.red))
               linearLayout.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.background_chat_empty)
               Toast.makeText(this@MainActivity, "Ingrese un mensaje \uD83D\uDE12", Toast.LENGTH_SHORT).show()
           }
       }
    }
}