package com.bappi.groupchat.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bappi.groupchat.data.entity.Messages
import com.bappi.groupchat.repository.MessagesRepository
import com.bappi.groupchat.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GroupDetailViewModel(
    private val messagesRepository: MessagesRepository,
    groupId: String?
): ViewModel() {

    private var messagesList = MutableLiveData<Resource<List<Messages>>>()

    init {
        fetchMessagesOfGroup(groupId)
    }

    private fun fetchMessagesOfGroup(groupId: String?) {
        viewModelScope.launch {
            val result: List<Messages>?
            withContext(Dispatchers.IO) { messagesRepository.getGroupMessages(groupId!!).collect {
                messagesList.postValue(Resource.success(it))
            } }
//            messagesList.postValue(Resource.success(result))
        }
    }

    fun getGroupMessages(): LiveData<Resource<List<Messages>>> {
        return messagesList
    }

    fun insertGroupMessage(message: Messages){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                messagesRepository.insertGroupMessage(message)
            }
        }
    }
}