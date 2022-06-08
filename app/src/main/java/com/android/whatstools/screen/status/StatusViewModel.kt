package com.android.whatstools.screen.status

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.whatstools.Utlis.StatusItem

class StatusViewModel:ViewModel() {
    var status: MutableLiveData<StatusItem> = MutableLiveData()

}