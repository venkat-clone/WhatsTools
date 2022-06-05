package com.android.whatstools.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.android.whatstools.R
import com.android.whatstools.screen.status.MediaFragment
import com.android.whatstools.screen.status.StatusList
import com.android.whatstools.screen.status.StatusViewModel
import com.android.whatstools.screen.status.statusAdapter

class StatusActivity : AppCompatActivity() {
    private val fm:FragmentManager = supportFragmentManager
    private val mainFragment:Fragment = StatusList()
    private val medialFragment:Fragment = MediaFragment()
    private lateinit var  viewModel:StatusViewModel;
    private var current:Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)
        viewModel = ViewModelProvider(this)[StatusViewModel::class.java]
        handleIntent(intent);
        observers()


    }

    private fun  handleIntent(intent:Intent){
        setFragment(mainFragment)
        if (intent.hasExtra("path")){
            val path:String? = intent.getStringExtra("path")
            if(path!!.contains(".mp4")){

            }
            else {

            }
        }
    }

    fun observers(){
        viewModel.status.observe(this){
            if (it!=null){
                setFragment(medialFragment)
            }
        }
    }

    override fun onBackPressed() {
        if (current!!.equals(mainFragment))
            super.onBackPressed()
        else
            removeFragment()

    }

    fun setFragment(fragment:Fragment ){
        if(current != null) fm.beginTransaction().hide(current!!).commit()
        fm.beginTransaction().add(R.id.layout,fragment).commit()
        current = fragment
    }
    fun removeFragment(){
        if(current!!.equals(mainFragment)) finish()
        else {
            fm.beginTransaction().remove(current!!).show(mainFragment).commit()
            current = mainFragment
        }
    }



    fun whatsappShare(view: View){
        val intent:Intent = Intent(Intent.ACTION_SEND)

    }
    fun Download(view: View){

    }
    fun Share(view: View){

    }

}