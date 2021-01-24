package com.jccsisc.chatbot.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jccsisc.chatbot.data.model.MessageModel
import com.jccsisc.chatbot.databinding.ItemChatBinding

class ChatAdapter: ListAdapter<MessageModel, ChatAdapter.ChatViewHolder>(DiffCallback) {

    //Hacemos uso de DiffCallback pa identificar que item se agregó o borró es lo mismo siempre
    companion object DiffCallback: DiffUtil.ItemCallback<MessageModel>() {

        override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
            return oldItem.message == newItem.message //igualamos si el item nuevo con el viejo es el mismo
        }

        override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
            return oldItem == newItem //para igualar modelos debe de ser una data class
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context))
        return ChatViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val messageModel = getItem(position)
        holder.bind(messageModel)
    }

    inner class ChatViewHolder(private val binding: ItemChatBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(messageModel: MessageModel) = with(binding) {
            txtMessage.text = messageModel.message
            executePendingBindings()
        }
    }
}
