package com.android.whatstools.Utlis

import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import java.io.File

class DataBindingAdapter {
    @BindingAdapter("setimage")
    fun setGrid(view:ImageView, s:String) {
        if(s.contains(".mp3")){
            view.setImageBitmap(ThumbnailUtils.createVideoThumbnail(s, MediaStore.Images.Thumbnails.MICRO_KIND))
        }
        else{
            view.setImageURI(File(s).toUri())
        }
    }

}
