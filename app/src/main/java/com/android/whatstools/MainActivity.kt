package com.android.whatstools

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.core.view.setMargins
import androidx.lifecycle.ViewModelProvider
import com.android.whatstools.databinding.ActivityMainBinding
import com.android.whatstools.screen.*
import com.android.whatstools.utlis.BaseClass
import io.sentry.Sentry

class MainActivity : AppCompatActivity() {
    private lateinit var mViewModel:MainActivityViewModel;
    private lateinit var binding:ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        var display:DisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(display)
        BaseClass.deviceWidth= display.widthPixels
        BaseClass.deviceHeight = display.heightPixels
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.clickHandler = ClickHandler(baseContext)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        binding.viewModel=mViewModel


        observers()
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if (SDK_INT >= Build.VERSION_CODES.R) {
                Log.d("myz", "" + SDK_INT)
                if (!Environment.isExternalStorageManager()) { mViewModel.permission.value = false
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE), 500
                    ) //permission request code is just an int
                }
                else mViewModel.getStatus()
            } else {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
                    mViewModel.permission.value = false
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 500)
                }
                else mViewModel.getStatus()
            }
            {
//            mViewModel.permission.value = false
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),500)
//            }
//
//        }else {
//            mViewModel.getStatus()
//        }
        }




    }

    override fun onRequestPermissionsResult( requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
         500->{
             if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_DENIED) mViewModel.getStatus()
         }
        }

    }


    // Observers and Listener Hear
    private fun observers(){
        binding.textInputLayout.setEndIconOnClickListener {
            if(mViewModel.number.value.toString().length<10){
                Toast.makeText(this,"Please Enter A Valid Number",Toast.LENGTH_LONG).show()
                return@setEndIconOnClickListener
            }
            val intent=Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse("https://wa.me/+91${mViewModel.number.value}")
            intent.`package` = "com.whatsapp"
            startActivity(intent)
        }
        mViewModel.StatusList.observe(this) {
            it!!
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                it.removeIf { fil->fil.path.contains(".nomedia") }
            }
            if(binding.grid.childCount==0)
            for (i in 1..6 ) {
                val view:ImageView = ImageView(baseContext)
                val parms = GridLayout.LayoutParams()
                parms.height =200
                parms.width =0
                parms.columnSpec = GridLayout.spec(i%3,1f)
                parms.setMargins(30)
                view.layoutParams = parms
                view.setBackgroundColor(resources.getColor(R.color.blue_A))
                if(it[i].name.contains(".mp4")) {
                    view.setImageBitmap(ThumbnailUtils.createVideoThumbnail(
                        it[i].path,
                        MediaStore.Images.Thumbnails.MICRO_KIND))
                }
                else  {
                    view.setImageURI(it[i].toUri())
                }
                view.setOnClickListener {_it->
                    val intent:Intent  = Intent(baseContext,StatusActivity::class.java)
                    intent.putExtra("path",it[i].path)
                    startActivity(intent)
                }
                binding.lottie.visibility = View.GONE
                binding.grid.addView(view)

                println({ it[i].path.contains(".mp4") })

            }
        }

        mViewModel.messages.observe(this){
            val mList = it.subList(0,if(it.size>5) 4 else it.size)
            val pager :HomeMessagePager= HomeMessagePager(mList,this)
            binding.messagePager.adapter = pager
            binding.messageTab.setupWithViewPager(binding.messagePager)

        }



    }


    fun openStatus(view:View){
        if(mViewModel.permission.value == true){
            val intent: Intent = Intent(baseContext, StatusActivity::class.java)
            startActivity(intent)
        }
    }

    fun onClickQr(view: View){
        val intent:Intent = Intent(this,WEBActivity::class.java)
        startActivity(intent)
    }

    // For Click Actions
     class ClickHandler(context :Context){
        lateinit var context:Context
        fun onClickQr(view: View){
            val intent:Intent = Intent(context,StatusActivity::class.java)
            context.startActivity(intent)
        }
        init {
            this.context = context
        }
        fun openChat(view:View){
            val intent:Intent=Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT,"https://wa.me/+918184926683?text=A chu")
            intent.type="text/url"
            startActivity(view.context,intent,null)
        }
        fun message(view:View){
            val intent:Intent=Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"https://wa.me/+918184926683?text=A chu")
            intent.type="text/url"
            intent.`package` = "com.whatsapp"
            startActivity(view.context,intent,null)
//            startActivity(intent)
        }
    }

    fun messages(view: View){
        val intent:Intent = Intent(this,MessageActivity::class.java)
        startActivity(intent)
    }

    fun magicText(view: View){
        startActivity(Intent(this,MagicTextActivity::class.java))
    }
    fun about(view: View){
        startActivity(Intent(this,InfoActivity::class.java))
    }





}