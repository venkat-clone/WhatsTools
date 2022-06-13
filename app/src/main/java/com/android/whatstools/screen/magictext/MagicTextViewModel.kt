package com.android.whatstools.screen.magictext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.whatstools.utlis.BaseClass
import kotlin.random.Random

class MagicTextViewModel:ViewModel() {
    val text:MutableLiveData<String> = MutableLiveData("")
    val mainText:MutableLiveData<String> = MutableLiveData("Wolla")
    val starText:MutableLiveData<String> = MutableLiveData("*")
    val dotText:MutableLiveData<String> = MutableLiveData(".")






    init {
        text.value = BaseClass.BaseText
    }

    fun Genaratetext(){
        var Ttext =""
        for (v in 1..15) {
            val c1 = Random.nextInt(0, 6)
            val c2 = Random.nextInt(c1, 20)
            val c3 = Random.nextInt(c2, 27)
            if(v%8==0)
                Ttext+="${" ".repeat(c1)}${mainText.value}\n"
            else
                Ttext+="${" ".repeat(c1)}${starText.value}${" ".repeat(c2)}${dotText.value}${" ".repeat(c3)}${starText.value}\n"
        }
        text.value = Ttext

    }



}