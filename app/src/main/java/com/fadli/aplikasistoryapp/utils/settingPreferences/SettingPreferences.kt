package com.fadli.aplikasistoryapp.utils.settingPreferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.fadli.aplikasistoryapp.utils.Constanta
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constanta.preferenceName)

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val id = stringPreferencesKey(Constanta.UserPreferences.UserId.name)
    private val email = stringPreferencesKey(Constanta.UserPreferences.UserEmail.name)
    private val name = stringPreferencesKey(Constanta.UserPreferences.UserName.name)
    private val token = stringPreferencesKey(Constanta.UserPreferences.UserToken.name)

    suspend fun saveLoginSession( userUid: String, userName:String, userEmail: String,userToken: String) {
        dataStore.edit { preferences ->
            preferences[id] = userUid
            preferences[name] = userName
            preferences[email] = userEmail
            preferences[token] = userToken
        }
    }

    suspend fun clearLoginSession() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    fun getUserUid(): Flow<String> = dataStore.data.map { it[id] ?: Constanta.preferenceDefaultValue }
    fun getUserEmail(): Flow<String> = dataStore.data.map { it[email] ?: Constanta.preferenceDefaultValue }
    fun getUserName(): Flow<String> = dataStore.data.map { it[name] ?: Constanta.preferenceDefaultValue }
    fun getUserToken(): Flow<String> = dataStore.data.map { it[token] ?: Constanta.preferenceDefaultValue }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}