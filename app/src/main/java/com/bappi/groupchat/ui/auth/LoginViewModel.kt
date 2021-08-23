package com.bappi.groupchat.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bappi.groupchat.data.entity.User
import com.bappi.groupchat.repository.UserRepository
import com.bappi.groupchat.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val loginRepository: UserRepository) : ViewModel() {
    private val loggedInUser = MutableLiveData<Resource<User>>()

    fun login(username: String) {
        viewModelScope.launch {
            loggedInUser.postValue(Resource.loading(null))
            coroutineScope {
            try {
                var user:List<User>
                withContext(Dispatchers.IO) { loginRepository.getUser(username).also { user = it } }

                if (user.isEmpty()) {
                    val id =
                        withContext(Dispatchers.IO) { insertUser(User(name = username)) }
                    user = withContext(Dispatchers.IO) { loginRepository.getUser(username) }
                    loggedInUser.postValue(Resource.success(user.get(0)))
                } else {
                    loggedInUser.postValue(Resource.success(user.get(0)))
                }
            } catch (e: Exception) {
                loggedInUser.postValue(Resource.error(e.message.toString(), null))
            }
            }
        }
    }

    private fun insertUser(user: User): Long {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
        return loginRepository.insertUser(user)
//            }
//        }
    }

    fun getLoggedInUser(): LiveData<Resource<User>> {
        return loggedInUser
    }
}