package com.example.githubapp.database

import android.os.Parcelable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteUser(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,

    @ColumnInfo(name = "username")
    var username : String? = null,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl : String? = null
) : Parcelable
