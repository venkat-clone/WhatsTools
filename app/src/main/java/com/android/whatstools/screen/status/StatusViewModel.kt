package com.android.whatstools.screen.status

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatusViewModel:ViewModel() {
    var status: MutableLiveData<String> = MutableLiveData()
    var removeFragment:MutableLiveData<Boolean> = MutableLiveData()

}