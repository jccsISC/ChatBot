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
    private val listChatBot = mutableListOf<MessageModel>()
    private val adapter = ChatAdapter()
    private val BOT = 0
    private val USER = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            rvChat.adapter = adapter

            viewModel.getChat().observe(this@MainActivity, Observer {
                adapter.submitList(it)

                adapter.notifyDataSetChanged()
                rvChat.layoutManager?.scrollToPosition(listChatBot.size - 1)
                edtMessage.setText("")
                edtMessage.setHintTextColor(getColor(R.color.gray))
                viewEmpty.visibility = View.GONE
            })

            if (listChatBot.isEmpty()) viewEmpty.visibility = View.VISIBLE else viewEmpty.visibility = View.GONE

            imvSend.setOnClickListener {
                val messge = edtMessage.text.toString()
                if (messge.isNotEmpty()) {
                    listChatBot.add(MessageModel(USER, messge))
                    adapter.notifyDataSetChanged()

                    chatBot() //hilo secundario
                    //retorna al ultimo elemento del recicler
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