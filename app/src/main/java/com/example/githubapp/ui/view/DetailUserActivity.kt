package com.example.githubapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubapp.adapter.SectionPagerAdapter
import com.example.githubapp.R
import com.example.githubapp.ui.view_model_factory.ViewModelFactory
import com.example.githubapp.database.FavoriteUser
import com.example.githubapp.model.UserData
import com.example.githubapp.databinding.ActivityDetailUserBinding
import com.example.githubapp.ui.view_model.DetailUserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var detailUserViewModel: DetailUserViewModel

    private var _detailUserActivityBinding : ActivityDetailUserBinding? = null
    private val binding get() = _detailUserActivityBinding


    private var username: String? = null
    private var userDetail = UserData()
    private var database : FavoriteUser? = null

    private var flag : Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _detailUserActivityBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        detailUserViewModel = obtainViewModel(this@DetailUserActivity)

        username = intent.getStringExtra(EXTRA_DATA) ?: ""

        detailUserViewModel.getUserDetail(username ?: "")

        detailUserViewModel.userDetail.observe(this){ user ->
            userDetail = user
            setUserDetail(user)
            database = FavoriteUser(userDetail.id)

            detailUserViewModel.checkUserDatabase().observe(this){ isFavorite ->
                for (favorite in isFavorite){
                    if (userDetail.id == favorite.id){
                        flag = false
                        setFavoriteButton(flag)
                    }
                }
            }

            binding?.fabFavorite?.setOnClickListener{
                if (flag){
                    flag = false
                    setFavoriteButton(flag)
                    insertUser(userDetail)
                } else {
                    flag = true
                    setFavoriteButton(flag)
                    deleteUser()
                }
            }
        }

        detailUserViewModel.isError.observe(this){
            showError(it)
        }

        detailUserViewModel.isLoading.observe(this){
            showLoading(it)
        }

        setupFollow()
    }

    override fun onDestroy() {
        super.onDestroy()
        _detailUserActivityBinding = null
    }

    private fun insertUser(user : UserData){
        database.let {list ->
            list?.id = user.id
            list?.username = user.login
            list?.avatarUrl = user.avatarUrl
            detailUserViewModel.insert(list as FavoriteUser)
            Toast.makeText(this, "Added To Favorite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteUser() {
        database.apply {
            detailUserViewModel.delete(this as FavoriteUser)
            Toast.makeText(this@DetailUserActivity, "Deleted From Favorite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupFollow(){
        val login = Bundle()
        login.putString(EXTRA_FRAGMENT, username)

        val sectionPagerAdapter = SectionPagerAdapter(this, login)

        val viewPager : ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter

        val tabs : TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setUserDetail(user : UserData){
        binding?.apply {
            tvDetailName.text = user.name
            tvDetailUsername.text = user.login
            tvDetailFollower.text = getString(R.string.followers, user.followers.toString())
            tvDetailFollowing.text = getString(R.string.following, user.following.toString())
            Glide.with(detailProfileUser.context)
                .load(user.avatarUrl)
                .into(detailProfileUser)
        }
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding?.progressBar?.visibility = View.VISIBLE
        }else{
            binding?.progressBar?.visibility = View.GONE
        }
    }

    private fun showError(isError: Boolean){
        if (isError){
            Toast.makeText(this@DetailUserActivity, "Error For Getting Data's From API", Toast.LENGTH_SHORT).show()
        }
    }

    private fun obtainViewModel(activity : AppCompatActivity) : DetailUserViewModel{
        val factory = ViewModelFactory.getInstance(activity.application )
        return ViewModelProvider(activity, factory)[DetailUserViewModel::class.java]
    }

    private fun setFavoriteButton(isFavorite : Boolean){
        if (isFavorite){
            binding?.fabFavorite?.setImageResource(R.drawable.ic_favorite_border)
        } else {
            binding?.fabFavorite?.setImageResource(R.drawable.ic_favorite)
        }
    }

    companion object{
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_FRAGMENT = "extra_fragment"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_following,
            R.string.tab_follower
        )
    }

}