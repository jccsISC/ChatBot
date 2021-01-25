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
    private val BOT = 0
    private val USER = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            rvChat.adapter = adapter
            adapter.submitList(listChatBot)

            if (listChatBot.isEmpty()) viewEmpty.visibility = View.VISIBLE else viewEmpty.visibility = View.GONE

            imvSend.setOnClickListener {
                val messge = edtMessage.text.toString()
                if (messge.isNotEmpty()) {

//                    viewModel.fetchChat(USER, messge)

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

    suspend fun datos(user: Int, message: String) {
        viewModel.fetchChat(user, message)
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
        val array = listOf("Si \uD83D\uDE0E", "No \uD83D\uDE12", "Pregunta de nuevo \uD83E\uDD28", "Es muy probable \uD83D\uDE01", "No lo creo \uD83E\uDD14", "No sé \uD83D\uDE13", "Tal vez \uD83D\uDE44", "Por supuesto \uD83D\uDE0F", "Claro que si \uD83E\uDD20")
        val palabra = array.random()
        return palabra
    }
}