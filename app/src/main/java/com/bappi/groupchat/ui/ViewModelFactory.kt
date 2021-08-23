package com.bappi.groupchat.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bappi.groupchat.data.db.AppDatabase
import com.bappi.groupchat.repository.GroupsRepository
import com.bappi.groupchat.repository.MessagesRepository
import com.bappi.groupchat.repository.ParticipantsRepository
import com.bappi.groupchat.repository.UserRepository
import com.bappi.groupchat.ui.auth.LoginViewModel
import com.bappi.groupchat.ui.chat.GroupDetailViewModel
import com.bappi.groupchat.ui.create.AddUserOrGroupViewModel
import com.bappi.groupchat.ui.home.HomeViewModel

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class ViewModelFactory(private val context: Context, val groupId: String? = null) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(
                    loginRepository = UserRepository(
                        AppDatabase.getDatabase(context).userDao()
                    )
                ) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                val appDatabase = AppDatabase.getDatabase(context)
                return HomeViewModel(
                    participantsRepository = ParticipantsRepository(appDatabase.participantsDao())
                ) as T
            }
            modelClass.isAssignableFrom(AddUserOrGroupViewModel::class.java) -> {
                val appDatabase = AppDatabase.getDatabase(context)
                return AddUserOrGroupViewModel(
                    userRepository = UserRepository(AppDatabase.getDatabase(context).userDao()),
                    groupsRepository = GroupsRepository(appDatabase.groupDao()),
                    participantsRepository = ParticipantsRepository(appDatabase.participantsDao())
                ) as T
            }
            modelClass.isAssignableFrom(GroupDetailViewModel::class.java) -> {
                return GroupDetailViewModel(
                    messagesRepository = MessagesRepository(
                        AppDatabase.getDatabase(context).messageDao()
                    ),
                    groupId = groupId
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}