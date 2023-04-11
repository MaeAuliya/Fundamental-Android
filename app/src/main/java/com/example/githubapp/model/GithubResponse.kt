package com.example.githubapp.model

import com.google.gson.annotations.SerializedName


data class SearchResponse(
	@field:SerializedName("items")
	val items: ArrayList<HomeUsers>
)

data class HomeUsers(
	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,
)

data class UserData(
	@field:SerializedName("id")
	var id : Int = 0,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("followers")
	val followers: Int? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("following")
	val following: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,
)
