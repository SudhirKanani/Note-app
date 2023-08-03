package com.my.note_app.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.note_app.Model.LoginRequest
import com.my.note_app.Model.UserResponse
import com.my.note_app.Repository.UserRepository
import com.my.note_app.Utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.RequestBody
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResposeLiveData


    fun SignUpUser(
        mobile: RequestBody?,
        name: RequestBody?,
        gender: RequestBody?,
        ispublic: RequestBody?,
        pass: RequestBody?,
        firebase_token: RequestBody?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.SignUpUser(mobile, name, gender, ispublic, pass, firebase_token)
        }
    }

    fun LoginUser(loginRequest: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.LoginUser(loginRequest)
        }
    }
}