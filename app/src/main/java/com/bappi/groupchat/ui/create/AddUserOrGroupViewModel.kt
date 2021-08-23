package com.bappi.groupchat.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bappi.groupchat.data.entity.Groups
import com.bappi.groupchat.data.entity.Participants
import com.bappi.groupchat.data.entity.User
import com.bappi.groupchat.repository.GroupsRepository
import com.bappi.groupchat.repository.ParticipantsRepository
import com.bappi.groupchat.repository.UserRepository
import com.bappi.groupchat.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddUserOrGroupViewModel(
    private val userRepository: UserRepository,
    private val groupsRepository: GroupsRepository,
    private val participantsRepository: ParticipantsRepository
) : ViewModel() {
    private lateinit var userId: String
    var selectedUserList: List<User> = arrayListOf()
    private var groupCreated = MutableLiveData<Boolean>().apply { value = false }

    fun setUserId(mUserId: String) {
        userId = mUserId
    }

    private var users = MutableLiveData<Resource<List<User>>>()

    init {
        fetchUsers()
    }


    private fun fetchUsers() {
        viewModelScope.launch {
            val result: List<User>
            withContext(Dispatchers.IO) { result = userRepository.getAllUser() }
            users.postValue(Resource.success(result))
        }
    }

    fun getUsers(): LiveData<Resource<List<User>>> {
        return users
    }

    fun insertUser(username: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userRepository.insertUser(User(name = username))
            }
            val userResult: List<User>
            withContext(Dispatchers.IO) { userResult = userRepository.getAllUser() }
            users.postValue(Resource.success(userResult))
        }
    }

    fun createGroup(groupName: String) {
        viewModelScope.launch {
            insertGroup(Groups(groupName))
            var group: Groups
            withContext(Dispatchers.IO) {
                group = groupsRepository.getGroup(groupName)[0]
            }
            for (user in selectedUserList) {
                insertParticipants(user.uid, group)
            }
            groupCreated.value = true
        }
    }

    private suspend fun insertParticipants(userId: Int, group: Groups?) {
        withContext(Dispatchers.IO) {
            participantsRepository.insertParticipants(
                Participants(
                    userId,
                    group?.uid!!,
                    group.name!!
                )
            )
        }
    }

    private suspend fun insertGroup(group: Groups) {
        withContext(Dispatchers.IO) {
            groupsRepository.insertGroup(group)
        }
    }

    fun isGroupCreated(): LiveData<Boolean> = groupCreated

}