package com.example.githubapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.R
import com.example.githubapp.ui.view_model_factory.ViewModelFactory
import com.example.githubapp.adapter.FavoriteUserAdapter
import com.example.githubapp.databinding.ActivityFavoriteUserBinding
import com.example.githubapp.ui.view_model.FavoriteUserViewModel

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    private var _favoriteUserActivityBinding : ActivityFavoriteUserBinding? = null
    private val binding get() = _favoriteUserActivityBinding

    private lateinit var adapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _favoriteUserActivityBinding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = getString(R.string.favoriteduser)

        favoriteUserViewModel = obtainViewModel(this@FavoriteUserActivity)

        favoriteUserViewModel.getAllFavoriteUser().observe(this){ list ->
            if (list != null){
                adapter.setListFavorite(list)
            }
        }
        adapter = FavoriteUserAdapter()
        binding?.rvFavoriteUser?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavoriteUser?.setHasFixedSize(false)
        binding?.rvFavoriteUser?.adapter = adapter
    }

    private fun obtainViewModel(activity : AppCompatActivity) : FavoriteUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application )
        return ViewModelProvider(activity, factory)[FavoriteUserViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.rvFavoriteUser?.adapter = null
        _favoriteUserActivityBinding = null
    }

}