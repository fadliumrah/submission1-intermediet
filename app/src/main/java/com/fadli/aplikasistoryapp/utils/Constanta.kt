package com.fadli.aplikasistoryapp.utils

object Constanta {
    enum class StoryDetail {
        UserName, ImageURL, ContentDescription
    }

    enum class UserPreferences {
        UserId, UserName, UserEmail, UserToken
    }


    const val preferenceDefaultValue = "Not Set"
    const val preferenceName = "Settings"

    const val BASEURL = "https://story-api.dicoding.dev/v1/"

    val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
}