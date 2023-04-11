package com.example.githubapp.ui.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.api.ApiConfig
import com.example.githubapp.database.FavoriteUser
import com.example.githubapp.model.UserData
import com.example.githubapp.repository.FavoriteUserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : ViewModel() {

    private val _userDetail = MutableLiveData<UserData>()
    val userDetail : LiveData<UserData> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError : LiveData<Boolean> = _isError

    private val mFavoriteUserRepository : FavoriteUserRepository = FavoriteUserRepository(application)

    fun insert(user : FavoriteUser){
        mFavoriteUserRepository.insert(user)
    }

    fun delete(user: FavoriteUser){
        mFavoriteUserRepository.delete(user)
    }

    fun checkUserDatabase() : LiveData<List<FavoriteUser>>{
        return mFavoriteUserRepository.getAllUser()
    }

    fun getUserDetail(id : String){
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().getUserDetail(id)
        client.enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null){
                    _userDetail.value = response.body()
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                _isError.value = true
                _isLoading.value = false
                Log.e("UserViewModel", "onFailure: ${t.message.toString()}")
            }

        })
    }
}