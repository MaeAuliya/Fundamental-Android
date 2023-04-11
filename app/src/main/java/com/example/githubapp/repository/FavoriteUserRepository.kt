package com.example.githubapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubapp.database.FavoriteUser
import com.example.githubapp.database.FavoriteUserDao
import com.example.githubapp.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteDao : FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteUserDao()
    }

    fun getAllUser() : LiveData<List<FavoriteUser>> = mFavoriteDao.getAllUser()

    fun insert(favoriteUser: FavoriteUser){
        executorService.execute{ mFavoriteDao.insert(favoriteUser)}
    }

    fun delete(favoriteUser: FavoriteUser){
        executorService.execute{ mFavoriteDao.delete(favoriteUser)}
    }


}