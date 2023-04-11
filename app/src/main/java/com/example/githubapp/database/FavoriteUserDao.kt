package com.example.githubapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite : FavoriteUser)

    @Delete
    fun delete(favorite: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser ORDER BY id ASC")
    fun getAllUser() : LiveData<List<FavoriteUser>>
}