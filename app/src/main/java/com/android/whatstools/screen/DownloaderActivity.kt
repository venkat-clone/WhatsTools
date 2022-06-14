package com.android.whatstools.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.android.whatstools.databinding.ActivityDownloaderBinding
import com.android.whatstools.screen.downloader.DownloaderActivityViewModel


class DownloaderActivity : AppCompatActivity() {
    lateinit var binding:ActivityDownloaderBinding
    lateinit var viewModel: DownloaderActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloaderBinding.inflate(LayoutInflater.from(this))
        viewModel = ViewModelProvider(this)[DownloaderActivityViewModel::class.java]
        binding.lifecycleOwner =this
        binding.viewModel= viewModel
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener(){
            onBackPressed()
        }
        observer()

    }

    fun observer(){
        viewModel.videoUrl.observe(this){
            Log.i("URL",it)
        }
    }


}