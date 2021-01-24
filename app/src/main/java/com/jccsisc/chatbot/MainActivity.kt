package com.jccsisc.chatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jccsisc.chatbot.data.model.MessageModel
import com.jccsisc.chatbot.databinding.ActivityMainBinding
import com.jccsisc.chatbot.ui.adapter.ChatAdapter
import java.lang.Exception

val listChatBot = mutableListOf<MessageModel>()

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val adapter = ChatAdapter()
    var click = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            binding.rvChat.adapter = adapter
            adapter.submitList(listChatBot)

            if (listChatBot.isEmpty()) viewEmpty.visibility = View.VISIBLE else viewEmpty.visibility = View.GONE

            imvSend.setOnClickListener {
                val messge = edtMessage.text.toString()
                if (messge.isNotEmpty()) {
                    listChatBot.add(MessageModel(messge))
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
        Thread(Runnable {
            val palabra = palabrasBot()
            listChatBot.add(MessageModel(palabra))
            Thread.sleep(2000)
            runOnUiThread(Runnable {
                adapter.notifyDataSetChanged()
                binding.rvChat.layoutManager?.scrollToPosition(listChatBot.size - 1)
            })
        }).start()
    }

    fun palabrasBot(): String {
        val array = listOf("Si", "No", "Pregunta de nuevo", "Es muy probable", "No lo creo", "No sé", "Tal vez", "Por supuesto", "Claro que si")
        val palabra = array.random()
        return palabra
    }
}