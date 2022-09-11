package com.udacity

import android.app.DownloadManager
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.udacity.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {

    private var downloadID: Long = 0
    lateinit var binding: ActivityMainBinding
    private var downloadURL = ""
    private var downloadTitle = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.contentMain.radioGroup.setOnCheckedChangeListener(this)
        setSupportActionBar(toolbar)
        registerReceiver(DownloadReceiver(), IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        binding.contentMain.downloadButton.setOnClickListener {
            download()
        }

    }

    private fun download() {
        if (downloadURL.isEmpty()) {
            Toast.makeText(this, "Please select the file to download", Toast.LENGTH_LONG).show()
            return
        }

        val request =
            DownloadManager.Request(Uri.parse(downloadURL))
                .setTitle(downloadTitle)
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.

    }


    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {

        when (checkedId) {
            R.id.radioGlide -> {
                downloadURL = getString(R.string.glide_url)
                downloadTitle = getString(R.string.glide_image_loading_library_by_bumptech)
            }
            R.id.radioLoadApp -> {
                downloadURL = getString(R.string.github_url)
                downloadTitle = getString(R.string.loadapp_current_repository_by_udacity)
            }
            R.id.radioRetrofit -> {
                downloadURL = getString(R.string.retrofit_url)
                downloadTitle =
                    getString(R.string.retrofit_type_safe_http_client_for_android_and_java_by_square_inc)
            }
            else -> {
                downloadURL = ""
                downloadTitle = ""
            }
        }
    }

}
