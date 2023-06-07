package com.fadli.aplikasistoryapp.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.fadli.aplikasistoryapp.R
import com.fadli.aplikasistoryapp.databinding.ActivityAuthBinding
import com.fadli.aplikasistoryapp.ui.dashboard.MainActivity
import com.fadli.aplikasistoryapp.ui.authentication.login.LoginFragment
import com.fadli.aplikasistoryapp.ui.authentication.register.RegisterFragment

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        @Suppress("DEPRECATION")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, RegisterFragment.newInstance())
                .commit()
        }
    }

    fun routeToMainActivity() {
        startActivity(Intent(this@AuthenticationActivity, MainActivity::class.java))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }


}