package com.bappi.groupchat.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bappi.groupchat.R
import com.bappi.groupchat.data.entity.Messages

class ChatAdapter(private val messages: ArrayList<Messages>) :
    RecyclerView.Adapter<ChatAdapter.DataViewHolder>() {
    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: Messages) {
            val messageTV = itemView.findViewById<AppCompatTextView>(R.id.message)
            messageTV.text = message.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_messaage, parent, false)
        )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount() = messages.size

    fun addData(messageList: List<Messages>) {
        messages.clear()
        messages.addAll(messageList)
    }
}