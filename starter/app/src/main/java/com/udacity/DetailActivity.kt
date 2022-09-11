package com.udacity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.udacity.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDetailBinding.inflate(layoutInflater)
        val title = intent.getStringExtra(EXTRA_NAME)
        val downloadStatus = intent.getBooleanExtra(EXTRA_DOWNLOAD_STATUS,false)

        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)

        binding.contentDetail.fileNameTv.text = title

        if (downloadStatus) {
            binding.contentDetail.statusTv.text = getString(R.string.download_success)
            binding.contentDetail.statusTv.setTextColor(Color.GREEN)
        } else {
            binding.contentDetail.statusTv.text = getString(R.string.download_fail)
            binding.contentDetail.statusTv.setTextColor(Color.RED)
        }

        binding.contentDetail.okBtn.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

    }

}

