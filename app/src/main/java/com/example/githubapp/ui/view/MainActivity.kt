package com.example.githubapp.ui.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.model.HomeUsers
import com.example.githubapp.R
import com.example.githubapp.adapter.UserAdapter
import com.example.githubapp.database.SettingPreferences
import com.example.githubapp.databinding.ActivityMainBinding
import com.example.githubapp.ui.view_model.DarkModeViewModel
import com.example.githubapp.ui.view_model.UserViewModel
import com.example.githubapp.ui.view_model_factory.SettingViewModelFactory

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)

        val darkModeViewModel = ViewModelProvider(this, SettingViewModelFactory(pref))[DarkModeViewModel::class.java]

        userViewModel.listUser.observe(this){ user ->
            setUserData(user)
        }

        userViewModel.isLoading.observe(this){
            showLoading(it)
        }

        userViewModel.isError.observe(this){
            showError(it)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.gitUser.layoutManager = layoutManager

        darkModeViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                if(!query.isNullOrEmpty()){
                    userViewModel.searchUser(query)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        userViewModel.searchUser.observe(this){ results ->
            setUserData(results)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.favorite -> {
                val intent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.theme -> {
                val intent = Intent(this@MainActivity, DarkModeActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> true
        }
    }


    private fun setUserData(users : ArrayList<HomeUsers>){
        val adapter = UserAdapter(users)
        binding.gitUser.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: HomeUsers) {
                Toast.makeText(this@MainActivity, user.login, Toast.LENGTH_SHORT).show()
                toDetailUser(user)
            }
        })

    }

    private fun toDetailUser(user : HomeUsers){
        val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_DATA, user.login)
        startActivity(intent)
    }


    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showError(isError: Boolean){
        if (isError){
            binding.tvError.visibility = View.VISIBLE
        }else{
            binding.tvError.visibility = View.GONE
        }
    }

}