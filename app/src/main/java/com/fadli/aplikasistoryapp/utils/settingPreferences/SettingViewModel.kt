package com.fadli.aplikasistoryapp.utils.settingPreferences

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.fadli.aplikasistoryapp.utils.Constanta
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: SettingPreferences): ViewModel() {

    fun setUserPreferences(userToken: String, userUid: String, userName:String, userEmail: String) {
        viewModelScope.launch {
            pref.saveLoginSession(userUid,userName,userEmail,userToken)
        }
    }

    fun getUserPreferences(property: String): LiveData<String>{
        return when(property){
            Constanta.UserPreferences.UserId.name -> pref.getUserUid().asLiveData()
            Constanta.UserPreferences.UserToken.name -> pref.getUserToken().asLiveData()
            Constanta.UserPreferences.UserName.name -> pref.getUserName().asLiveData()
            Constanta.UserPreferences.UserEmail.name -> pref.getUserEmail().asLiveData()
            else -> pref.getUserUid().asLiveData()
        }
    }

    fun clearUserPreferences() {
        viewModelScope.launch {
            pref.clearLoginSession()
        }
    }

}