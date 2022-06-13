package com.android.whatstools.screen.magictext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.whatstools.utlis.BaseClass

class MagicTextViewModel:ViewModel() {
    val text:MutableLiveData<String> = MutableLiveData("")
    val mainText:MutableLiveData<String> = MutableLiveData("")
    val starText:MutableLiveData<String> = MutableLiveData("")
    val dotText:MutableLiveData<String> = MutableLiveData("")






    init {
        text.value = BaseClass.BaseText
    }






















}