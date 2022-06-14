package com.android.whatstools.screen.downloader

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DownloaderActivityViewModel:ViewModel() {
    val vedioFeached:MutableLiveData<Boolean> = MutableLiveData(false)
    val Feaching:MutableLiveData<Boolean> = MutableLiveData(false)
    val videoUrl:MutableLiveData<String> = MutableLiveData("")

}