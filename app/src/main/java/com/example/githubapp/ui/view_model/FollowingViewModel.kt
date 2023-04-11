package com.example.githubapp.ui.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.api.ApiConfig
import com.example.githubapp.model.HomeUsers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel() : ViewModel() {

    private val _listFollowing = MutableLiveData<ArrayList<HomeUsers>>()
    val listFollowing : LiveData<ArrayList<HomeUsers>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError : LiveData<Boolean> = _isError

    fun getFollowing(id: String){
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().getFollowing(id)
        client.enqueue(object : Callback<ArrayList<HomeUsers>> {
            override fun onResponse(
                call: Call<ArrayList<HomeUsers>>,
                response: Response<ArrayList<HomeUsers>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null){
                    _listFollowing.value = response.body()
                }
            }

            override fun onFailure(call: Call<ArrayList<HomeUsers>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.e("FollowViewModel", "onFailure: ${t.message.toString()}")
            }

        })
    }

}