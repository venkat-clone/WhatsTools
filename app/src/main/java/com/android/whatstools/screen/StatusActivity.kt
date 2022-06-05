package com.android.whatstools.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.android.whatstools.R
import com.android.whatstools.screen.status.statusList

class StatusActivity : AppCompatActivity() {
    val fm:FragmentManager = supportFragmentManager
    private val mainFragment:Fragment = statusList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)
        handleIntent(intent);



    }

    private fun  handleIntent(intent:Intent){
        fm.beginTransaction().add(R.id.layout,mainFragment).commit()

        if (intent.hasExtra("path")){
            val path:String? = intent.getStringExtra("path")
            if(path!!.contains(".mp4")){

            }
            else {

            }
        }
    }
}