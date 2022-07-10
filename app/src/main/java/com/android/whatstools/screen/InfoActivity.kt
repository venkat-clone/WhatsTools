package com.android.whatstools.screen

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.android.whatstools.R
import com.android.whatstools.databinding.ActivityInfoBinding
import java.util.*

class InfoActivity : AppCompatActivity() {
    lateinit var binding:ActivityInfoBinding
    val li = Arrays.asList(
        "",
        "https://github.com/venkat-clone",
        "https://www.linkedin.com/in/venkey-venkey-b2aa34202/",
        "https://www.instagram.com/v____e___n__k_ey/",
        "http://play.google.com/store/apps/details?id=$packageName"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(LayoutInflater.from(this))
        binding.clickHandler = ClickHandler()
        setContentView(binding.root)
    }
    inner class ClickHandler(){
        fun open(i:Int){
            if(i==1 || i==5) {
                Toast.makeText(baseContext, " Not Yet Implemented", Toast.LENGTH_SHORT).show()
                return
            }
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(li[i-1])
                )
            )

        }
    }
}