package com.android.whatstools.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.android.whatstools.databinding.ActivityMagicTextBinding
import com.android.whatstools.screen.magictext.MagicTextViewModel
import com.android.whatstools.utlis.BaseClass
import kotlinx.android.synthetic.main.activity_magic_text.view.*

class MagicTextActivity : AppCompatActivity() {
    lateinit var binding: ActivityMagicTextBinding
    lateinit var viewModel: MagicTextViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMagicTextBinding.inflate(LayoutInflater.from(this))
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this)[MagicTextViewModel::class.java]
        binding.viewmodel = viewModel
        setContentView(binding.root)


        observer()

    }

    private fun observer(){
        binding.toolbar.toolbar.setNavigationOnClickListener(){
            onBackPressed()
        }
        viewModel.text.observe(this){

        }
        viewModel.dotText.observe(this){
            if(it.isNotEmpty())viewModel.text.value = BaseClass.BaseText.replace(".",it)
        }
        viewModel.starText.observe(this){
            if(it.isNotEmpty()) viewModel.text.value = BaseClass.BaseText.replace("*",it)
        }
        viewModel.mainText.observe(this){

        }

    }

}