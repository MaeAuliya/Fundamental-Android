package com.example.githubapp.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubapp.database.SettingPreferences
import kotlinx.coroutines.launch

class DarkModeViewModel(private val pref: SettingPreferences) : ViewModel() {

    fun getThemeSetting() : LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkMode : Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkMode)
        }
    }

}