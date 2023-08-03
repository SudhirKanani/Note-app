package com.my.note_app.Repository

import android.accounts.NetworkErrorException
import android.net.Network
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.my.note_app.Api.UserApi
import com.my.note_app.Model.LoginRequest
import com.my.note_app.Model.UserResponse
import com.my.note_app.Utils.Constant.TAG
import com.my.note_app.Utils.NetworkResult
import okhttp3.RequestBody
import retrofit2.http.Part
import java.lang.NullPointerException
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userResposeLiveData: LiveData<NetworkResult<UserResponse>>
        get() = _userResponseLiveData


    suspend fun SignUpUser(
        mobile: RequestBody?,
        name: RequestBody?,
        gender: RequestBody?,
        ispublic: RequestBody?,
        pass: RequestBody?,
        firebase_token: RequestBody?
    ) {
        _userResponseLiveData.postValue(NetworkResult.Loading())

        val signup = userApi.Signup(mobile, name, gender, ispublic, pass, firebase_token)
        Log.e(TAG, "${signup.body()}")
        if (signup.isSuccessful && signup.body() != null) {
            if (signup.body()!!.status) {
                _userResponseLiveData.postValue(NetworkResult.Success(signup.body()!!))
            } else {
                _userResponseLiveData.postValue(NetworkResult.Error(signup.body()!!.message))

            }
        } else if (signup.errorBody() != null) {
            _userResponseLiveData.postValue(NetworkResult.Exeption(NetworkErrorException()))

        } else {
            _userResponseLiveData.postValue(NetworkResult.Exeption(NetworkErrorException()))
        }
    }

    suspend fun LoginUser(loginRequest: LoginRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading())

        try {
            val login = userApi.Login(loginRequest)
            Log.e(TAG, "${login.body()}")
            if (login?.body() != null) {
                if (login.body()!!.status) {
                    _userResponseLiveData.postValue(NetworkResult.Success(login.body()!!))
                } else {
                    _userResponseLiveData.postValue(NetworkResult.Error(login.body()!!.message))

                }
            } else {
                _userResponseLiveData.postValue(NetworkResult.Exeption(NullPointerException()))
            }
        } catch (e: Exception) {
            _userResponseLiveData.postValue(NetworkResult.Exeption(e))
        }
    }
}