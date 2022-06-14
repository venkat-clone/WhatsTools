package com.android.whatstools.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
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
        binding.clickHandler = ClickHandler()

        observer()

    }

    private fun observer(){
        binding.toolbar.toolbar.setNavigationOnClickListener(){
            onBackPressed()
        }
        viewModel.dotText.observe(this){
            viewModel.Genaratetext()
        }
        viewModel.starText.observe(this){
            viewModel.Genaratetext()
        }
        viewModel.mainText.observe(this){
            viewModel.Genaratetext()
        }

    }
    inner class ClickHandler{
        fun Copy(){
            val  service = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("copid",viewModel.text.value)
            service.setPrimaryClip(clip)
            Toast.makeText(baseContext,"Copied to Clipboard",Toast.LENGTH_SHORT).show()
        }
        fun WhatsappShare(){
            val intent=Intent(Intent.ACTION_SEND)
            intent.`package` = "com.whatsapp"
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,viewModel.text.value)
            startActivity(intent)
        }
        fun Send(){
            val intent=Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,viewModel.text.value)
            startActivity(intent)
        }
        fun Refresh(){
            viewModel.Genaratetext()
        }
    }

}