package com.android.whatstools.screen.magictext

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.whatstools.utlis.BaseClass
import kotlin.math.log
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
        for (v in 1..31) {
            val c1 = if(v!=1) Random.nextInt(0, 10) else 0
            val c2 = Random.nextInt(c1, 20)
            val c3 = Random.nextInt(c2, 30)
            Log.i("Log_1","$c3")
            if(v%16==0)
                Ttext+="${" ".repeat(c1)}${mainText.value}"
            else
                Ttext+="${" ".repeat(c1)}${starText.value}${" ".repeat(c2)}${dotText.value}${" ".repeat(c3)}${starText.value}"
        }
        text.value = Ttext.trimStart()

    }



}