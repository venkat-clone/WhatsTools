package com.android.whatstools.screen.status

import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.whatstools.utlis.StatusItem
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class StatusListViewModel : ViewModel() {
    var bitmapList:MutableLiveData<List<StatusItem>> = MutableLiveData()
    var status:MutableLiveData<StatusItem> = MutableLiveData()
    var whatsappShare:MutableLiveData<String> = MutableLiveData()
    var intent:MutableLiveData<String> = MutableLiveData()
    var share:MutableLiveData<String> = MutableLiveData()
    init {
        setStatus()
    }
    fun setStatus() {
        val file = File(Environment.getExternalStorageDirectory().toString() + if (Build.VERSION.SDK_INT <= 30) "/WhatsApp/Media/.Statuses" else "/Android/media/com.whatsapp/WhatsApp/Media/.Statuses")
        val list = file.listFiles()
        val executor:ExecutorService = Executors.newSingleThreadExecutor();
        executor.execute {
            val blist:MutableList<StatusItem> = ArrayList()
            list!!.map {
                if (it.path.contains(".mp4")) {
                    blist.add(StatusItem(it.path,ThumbnailUtils.createVideoThumbnail(it.path, MediaStore.Images.Thumbnails.MICRO_KIND)!!))
                }
                if (it.path.contains(".jpg")) {
                    blist.add(StatusItem(it.path,BitmapFactory.decodeFile(it.absolutePath, BitmapFactory.Options())))
                }
            }
            bitmapList.postValue(blist)

        }
    }

}