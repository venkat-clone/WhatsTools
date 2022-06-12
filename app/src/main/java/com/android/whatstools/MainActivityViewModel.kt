package com.android.whatstools

import android.app.Application
import android.os.Build
import android.os.Environment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.whatstools.dataBase.message.Repository
import java.io.File

class MainActivityViewModel(application: Application): AndroidViewModel(application) {
     var number:MutableLiveData<String> = MutableLiveData("");
     var message:MutableLiveData<String> = MutableLiveData();
     var StatusList:MutableLiveData<MutableList<File>> =MutableLiveData();
    var isWhatsappExists:MutableLiveData<Boolean> = MutableLiveData(false)
    var permission:MutableLiveData<Boolean> = MutableLiveData(false)
    val repository:Repository ;
    val messages:LiveData<List<MessageEntity>>;
    init {
        repository = Repository(application)
        messages = repository.getMessages()
    }

    fun getStatus(){
        permission.value =true
        val file = File(Environment.getExternalStorageDirectory().toString() + if (Build.VERSION.SDK_INT <= 30) "/WhatsApp/Media/.Statuses" else "/Android/media/com.whatsapp/WhatsApp/Media/.Statuses")
        if(file.exists()){
            isWhatsappExists.postValue(true)
            val list = file.listFiles()
            StatusList.postValue(list!!.asList().toMutableList())
        }
    }



    fun registerBroadcast(){

    }


}