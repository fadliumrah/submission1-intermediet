package com.fadli.aplikasistoryapp.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.fadli.aplikasistoryapp.utils.settingPreferences.SettingViewModel
import com.fadli.aplikasistoryapp.utils.models.ViewModelSettingFactory
import com.fadli.aplikasistoryapp.ui.authentication.AuthenticationActivity
import com.fadli.aplikasistoryapp.databinding.ActivitySplashScreenBinding
import com.fadli.aplikasistoryapp.ui.dashboard.MainActivity
import com.fadli.aplikasistoryapp.utils.Constanta
import com.fadli.aplikasistoryapp.utils.settingPreferences.SettingPreferences
import com.fadli.aplikasistoryapp.utils.settingPreferences.dataStore
import java.util.*
import kotlin.concurrent.schedule


@SuppressLint("CustomSplashScreen")
class  SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel =
            ViewModelProvider(this, ViewModelSettingFactory(pref))[SettingViewModel::class.java]

        settingViewModel.getUserPreferences(Constanta.UserPreferences.UserToken.name)
            .observe(this) { token ->
                if (token == Constanta.preferenceDefaultValue) Timer().schedule(2000) {
                    startActivity(Intent(this@SplashScreenActivity, AuthenticationActivity::class.java))
                    finish()
                } else Timer().schedule(2000) {
                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    finish()
                }
            }
    }
}