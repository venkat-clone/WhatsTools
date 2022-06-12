package com.android.whatstools.utlis

import android.graphics.Bitmap

class DataClass {

}
class StatusItem(path:String,bitmap: Bitmap){
    val path :String
    private val bitmap:Bitmap
    init {
        this.path = path
        this.bitmap = bitmap
    }
    fun getBitmap() : Bitmap {
        return bitmap
    }
}