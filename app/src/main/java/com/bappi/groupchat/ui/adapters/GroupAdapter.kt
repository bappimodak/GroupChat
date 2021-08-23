package com.bappi.groupchat.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bappi.groupchat.R
import com.bappi.groupchat.data.entity.Groups
import com.bappi.groupchat.data.entity.Participants

class GroupAdapter(
    private val groups: ArrayList<Participants>
) : RecyclerView.Adapter<GroupAdapter.DataViewHolder>() {

    var onItemClick: ((Participants) -> Unit)? = null

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(groups[adapterPosition])
            }
        }

        fun bind(group: Participants) {
            val groupName = itemView.findViewById<TextView>(R.id.textViewGroupName)
            groupName.text = group.groupName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_group, parent,
                false
            )
        )

    override fun getItemCount(): Int = groups.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(groups[position])

    fun addData(list: List<Participants>) {
        groups.clear()
        groups.addAll(list)
    }

}