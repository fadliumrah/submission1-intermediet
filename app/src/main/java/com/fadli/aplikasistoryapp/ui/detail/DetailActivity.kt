package com.fadli.aplikasistoryapp.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fadli.aplikasistoryapp.utils.Constanta
import com.bumptech.glide.Glide
import com.fadli.aplikasistoryapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.storyName.text =
            intent.getData(Constanta.StoryDetail.UserName.name, "Name")
        Glide.with(binding.root)
            .load(intent.getData(Constanta.StoryDetail.ImageURL.name, ""))
            .into(binding.storyImage)
        binding.storyDescription.text =
            intent.getData(Constanta.StoryDetail.ContentDescription.name, "Caption")

    }

    private fun Intent.getData(key: String, defaultValue: String = "None"): String {
        return getStringExtra(key) ?: defaultValue
    }

}