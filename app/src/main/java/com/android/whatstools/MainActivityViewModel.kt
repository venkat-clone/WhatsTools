package com.android.whatstools

import android.os.Build
import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class MainActivityViewModel: ViewModel() {
    public  var number:MutableLiveData<String> = MutableLiveData("");
    public  var message:MutableLiveData<String> = MutableLiveData();
    public var StatusList:MutableLiveData<List<File>> =MutableLiveData();

    fun getStatus(){
        val file = File(Environment.getExternalStorageDirectory().toString() + if (Build.VERSION.SDK_INT <= 30) "/WhatsApp/Media/.Statuses" else "/Android/media/com.whatsapp/WhatsApp/Media/.Statuses")
        val list = file.listFiles()
        StatusList.postValue(list!!.asList())
    }

    fun setStatus(){

    }


}