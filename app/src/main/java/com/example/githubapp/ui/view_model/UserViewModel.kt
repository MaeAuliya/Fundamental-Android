package com.example.githubapp.ui.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.githubapp.api.ApiConfig
import com.example.githubapp.database.SettingPreferences
import com.example.githubapp.model.HomeUsers
import com.example.githubapp.model.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    private val _listUser = MutableLiveData<ArrayList<HomeUsers>>()
    val listUser : LiveData<ArrayList<HomeUsers>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _searchUser = MutableLiveData<ArrayList<HomeUsers>>()
    val searchUser : LiveData<ArrayList<HomeUsers>> = _searchUser

    private val _isError = MutableLiveData<Boolean>()
    val isError : LiveData<Boolean> = _isError

    init {
        getUser()
    }

//    fun getThemeSetting() : LiveData<Boolean> {
//        return pref.getThemeSetting().asLiveData()
//    }

    private fun getUser(){
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().getUsers()
        client.enqueue(object : Callback<ArrayList<HomeUsers>> {
            override fun onResponse(
                call: Call<ArrayList<HomeUsers>>,
                response: Response<ArrayList<HomeUsers>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful && response.body() != null){
                    _listUser.value = response.body()
                }
            }

            override fun onFailure(call: Call<ArrayList<HomeUsers>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.e("UserViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun searchUser(query: String){
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().searchUser(query)
        client.enqueue(object : Callback<SearchResponse>{
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null){
                    _searchUser.value = response.body()?.items
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.e("UserViewModel", "onFailure: ${t.message.toString()}")
            }

        })
    }
}