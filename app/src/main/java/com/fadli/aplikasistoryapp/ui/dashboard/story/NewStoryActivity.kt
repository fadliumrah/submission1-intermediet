package com.fadli.aplikasistoryapp.ui.dashboard.story

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.fadli.aplikasistoryapp.utils.settingPreferences.SettingViewModel
import com.fadli.aplikasistoryapp.utils.models.ViewModelGeneralFactory
import com.fadli.aplikasistoryapp.utils.models.ViewModelSettingFactory
import com.fadli.aplikasistoryapp.R
import com.fadli.aplikasistoryapp.databinding.ActivityNewStoryBinding
import com.fadli.aplikasistoryapp.ui.dashboard.MainActivity
import com.fadli.aplikasistoryapp.utils.Constanta
import com.fadli.aplikasistoryapp.utils.Helper
import com.fadli.aplikasistoryapp.utils.settingPreferences.SettingPreferences
import com.fadli.aplikasistoryapp.utils.settingPreferences.dataStore
import java.io.File

class NewStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewStoryBinding
    private var userToken: String? = null
    private var storyViewModel: StoryViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storyViewModel = ViewModelProvider(
            this,
            ViewModelGeneralFactory(this)
        )[StoryViewModel::class.java]

        /* get Token from preference */
        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel =
            ViewModelProvider(this, ViewModelSettingFactory(pref))[SettingViewModel::class.java]
        settingViewModel.getUserPreferences(Constanta.UserPreferences.UserToken.name)
            .observe(this) { token ->
                userToken = StringBuilder("Bearer ").append(token).toString()
            }


        val myFile = intent?.getSerializableExtra(EXTRA_PHOTO_RESULT) as File
        val isBackCamera = intent?.getBooleanExtra(EXTRA_CAMERA_MODE, true) as Boolean
        val rotatedBitmap = Helper.rotateBitmap(
            BitmapFactory.decodeFile(myFile.path),
            isBackCamera
        )
        binding.storyImage.setImageBitmap(rotatedBitmap)
        binding.btnUpload.setOnClickListener {
            if (binding.storyDescription.text.isNotEmpty()) {
                uploadImage(myFile, binding.storyDescription.text.toString())
            } else {
                Helper.showDialogInfo(
                    this,
                    getString(R.string.UI_validation_empty_story_description)
                )
            }
        }
        storyViewModel?.let { vm ->
            vm.isSuccessUploadStory.observe(this) {
                if (it) {
                    val dialog = Helper.dialogInfoBuilder(
                        this,
                        getString(R.string.API_success_upload_image)
                    )
                    val btnOk = dialog.findViewById<Button>(R.id.button_ok)
                    btnOk.setOnClickListener {
                        finish()
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                    dialog.show()
                }

            }
            vm.loading.observe(this) {
                binding.loading.root.visibility = it
            }
            vm.error.observe(this) {
                if (it.isNotEmpty()) {
                    Helper.showDialogInfo(this, it)
                }
            }
        }
    }

    private fun uploadImage(image: File, description: String) {
        if (userToken != null) {
            storyViewModel?.uploadNewStory(userToken!!, image, description)
        } else {
            Helper.showDialogInfo(
                this,
                getString(R.string.API_error_header_token)
            )
        }
    }

    companion object {
        const val EXTRA_PHOTO_RESULT = "PHOTO_RESULT"
        const val EXTRA_CAMERA_MODE = "CAMERA_MODE"
    }
}