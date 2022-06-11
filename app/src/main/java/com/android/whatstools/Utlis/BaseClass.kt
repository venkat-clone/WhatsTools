package com.android.whatstools.Utlis

import android.util.Log

abstract class BaseClass {

    companion object {
        var deviceWidth: Int =0
            get() {
                return field
            }
            set(value) { field = value
            }

        var deviceHeight: Int = 0
            get() {
                return field
            }
            set(value) {
                field = value
            }
    }

}