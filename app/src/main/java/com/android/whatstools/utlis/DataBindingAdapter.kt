package com.android.whatstools.utlis

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.android.whatstools.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


@BindingAdapter("setimage")
fun setGrid(view: ImageView, s: String) {
    if (s.contains(".mp3")) {
        view.setImageBitmap(
            ThumbnailUtils.createVideoThumbnail(
                s,
                MediaStore.Images.Thumbnails.MICRO_KIND
            )
        )
    } else {
        view.setImageURI(File(s).toUri())
    }
}


@BindingAdapter("loadDp")
fun LoadDp(v: ImageView, loadDp: String) {
    if (loadDp.isNotEmpty())
        v.setImageURI(File(loadDp).toUri())

}

@BindingAdapter("setDate")
fun setDate(view: TextView, date: Long) {
    val formatter: SimpleDateFormat = SimpleDateFormat("dd MMM yyyy hh:mm aaa")

    view.text = formatter.format(Date(date))
}

@BindingAdapter("setWidthRatio")
fun WidthRatio(view: View, setWidthRatio: Int) {
    view.layoutParams.width = BaseClass.deviceWidth * setWidthRatio
}

@BindingAdapter("setHeightRatio")
fun HeightRatio(view: View, setHeightRatio: Double) {
    view.layoutParams.height = (BaseClass.deviceHeight * setHeightRatio).roundToInt()
}

@BindingAdapter("SqureWithWidth")
fun SqureWithWidth(view: View, SqureWithWidth: Double) {
    Log.i("Squre", "Width ${view.layoutParams.width} Height ${view.layoutParams.height}")
    view.layoutParams.height = (BaseClass.deviceWidth * SqureWithWidth).roundToInt()
    view.layoutParams.width = (BaseClass.deviceWidth * SqureWithWidth).roundToInt()
    Log.i("Squre", "Width ${view.layoutParams.width} Height ${view.layoutParams.height}")
}

//@BindingAdapter("SqureWithHeight")
//fun SqureWithHeight(view: View, SqureWithWidth: Int) {
//    view.layoutParams.width = view.height
//}
//@BindingAdapter("SqureWithHeight")
//fun WidthWithHeight(view: View, SqureWithWidth: Int) {
//    view.layoutParams.width = view.height
//}
@BindingAdapter("HeightWithWidth")
fun HeightWithWidth(view: View, HeightWithWidth: Double) {
    view.layoutParams.height = (BaseClass.deviceWidth * HeightWithWidth).roundToInt()
}





