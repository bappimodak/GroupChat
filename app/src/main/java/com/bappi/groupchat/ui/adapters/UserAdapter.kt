package com.bappi.groupchat.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bappi.groupchat.R
import com.bappi.groupchat.data.entity.User


class UserAdapter(private val users: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.DataViewHolder>() {
    private var showCheckbox: Boolean = false

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User, showCheckbox: Boolean) {
            val userName = itemView.findViewById<AppCompatTextView>(R.id.textViewUserName)
            userName.text = user.name
            val checkBox = itemView.findViewById<AppCompatCheckBox>(R.id.userCheckbox)
            checkBox.setOnCheckedChangeListener(null)
            if(showCheckbox){
                checkBox.visibility = View.VISIBLE
            } else {
                checkBox.visibility = View.GONE
            }
            checkBox.isChecked = user.isSelected;
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                user.isSelected = isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_user,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position], showCheckbox)
    }

    override fun getItemCount() = users.size


    fun addUsers(usersList: List<User>) {
        users.clear()
        users.addAll(usersList)
    }

    fun getUsers() = users


    fun showCheckbox(enableCheckbox: Boolean) {
        showCheckbox = enableCheckbox
    }
}