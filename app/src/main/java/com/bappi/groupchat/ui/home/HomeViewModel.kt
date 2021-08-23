package com.bappi.groupchat.ui.home

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bappi.groupchat.data.entity.Groups
import com.bappi.groupchat.data.entity.Messages
import com.bappi.groupchat.data.entity.Participants
import com.bappi.groupchat.repository.GroupsRepository
import com.bappi.groupchat.repository.MessagesRepository
import com.bappi.groupchat.repository.ParticipantsRepository
import com.bappi.groupchat.repository.UserRepository
import com.bappi.groupchat.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val participantsRepository: ParticipantsRepository
) : ViewModel() {
    private lateinit var userId: String

    fun setUserId(mUserId: String) {
        userId = mUserId
        fetchUserGroups()
    }

    private var participantGroups = MutableLiveData<Resource<List<Participants>>>()

    private fun fetchUserGroups() {
        viewModelScope.launch {
            val result: List<Participants>
            withContext(Dispatchers.IO) { result = participantsRepository.getGroups(userId) }
            participantGroups.postValue(Resource.success(result))
        }
    }

    fun getGroups(): LiveData<Resource<List<Participants>>> {
        return participantGroups
    }
}