package com.example.githubapp.api


import com.example.githubapp.model.HomeUsers
import com.example.githubapp.model.SearchResponse
import com.example.githubapp.model.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("/users")
    fun getUsers() : Call<ArrayList<HomeUsers>>

    @GET("search/users")
    fun searchUser(
        @Query("q") q:String
    ) : Call<SearchResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username : String
    ) : Call<UserData>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ) : Call<ArrayList<HomeUsers>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ) : Call<ArrayList<HomeUsers>>

}