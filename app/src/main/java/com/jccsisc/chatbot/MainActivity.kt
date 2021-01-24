package com.jccsisc.chatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.jccsisc.chatbot.data.model.MessageModel
import com.jccsisc.chatbot.databinding.ActivityMainBinding
import com.jccsisc.chatbot.databinding.ItemChatBinding
import com.jccsisc.chatbot.ui.adapter.ChatAdapter
import com.jccsisc.chatbot.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    val adapter = ChatAdapter()
    val listChatBot = mutableListOf<MessageModel>()
    val BOT = 0
    val USER = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.apply {
            rvChat.adapter = adapter
            adapter.submitList(listChatBot)

            if (listChatBot.isEmpty()) viewEmpty.visibility = View.VISIBLE else viewEmpty.visibility = View.GONE

            imvSend.setOnClickListener {
                val messge = edtMessage.text.toString()
                if (messge.isNotEmpty()) {
                    listChatBot.add(MessageModel(1, messge))
                    adapter.notifyDataSetChanged()

                    chatBot()

                    rvChat.layoutManager?.scrollToPosition(listChatBot.size - 1)
                    edtMessage.setText("")
                    edtMessage.setHintTextColor(getColor(R.color.gray))
                    viewEmpty.visibility = View.GONE
                } else {
                    edtMessage.setHintTextColor(getColor(R.color.red))
                    Toast.makeText(this@MainActivity, "Ingrese un mensaje", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun chatBot() {
        val handler = Handler()
        handler.postDelayed(Runnable {
            val palabra = palabrasBot()
            listChatBot.add(MessageModel(0, palabra))
            Thread.sleep(2000)
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