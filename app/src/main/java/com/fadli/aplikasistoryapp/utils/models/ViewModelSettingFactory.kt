package com.fadli.aplikasistoryapp.utils.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fadli.aplikasistoryapp.utils.settingPreferences.SettingPreferences
import com.fadli.aplikasistoryapp.utils.settingPreferences.SettingViewModel

class ViewModelSettingFactory(private val pref: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
