package com.android.whatstools.utlis

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