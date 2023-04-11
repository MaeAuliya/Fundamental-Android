package com.example.githubapp.ui.view_model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.database.FavoriteUser
import com.example.githubapp.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {

    private val mUserRepository : FavoriteUserRepository = FavoriteUserRepository(application)

    fun getAllFavoriteUser() : LiveData<List<FavoriteUser>>{
        return mUserRepository.getAllUser()
    }

}