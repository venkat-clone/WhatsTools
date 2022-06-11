package com.android.whatstools.Utlis

import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import java.io.File
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
            Log.i("Squre","Width ${view.layoutParams.width} Height ${view.layoutParams.height}")
            view.layoutParams.height = (BaseClass.deviceWidth*SqureWithWidth).roundToInt()
            view.layoutParams.width = (BaseClass.deviceWidth*SqureWithWidth).roundToInt()
            Log.i("Squre","Width ${view.layoutParams.width} Height ${view.layoutParams.height}")
        }

        @BindingAdapter("SqureWithHeight")
        fun SqureWithHeight(view: View, SqureWithWidth: Int) {
            view.layoutParams.width = view.height
        }

