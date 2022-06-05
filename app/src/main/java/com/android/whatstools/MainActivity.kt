package com.android.whatstools

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.webkit.PermissionRequest
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.PermissionChecker
import androidx.core.net.toUri
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.setMargins
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.whatstools.databinding.ActivityMainBinding
import com.android.whatstools.screen.StatusActivity
import java.security.Permission
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private lateinit var mViewModel:MainActivityViewModel;
    private lateinit var binding:ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.clickHandler = ClickHandler(baseContext)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        binding.viewModel=mViewModel



        observers()
        if(PermissionChecker.checkSelfPermission(this,android.Manifest.permission_group.STORAGE)==PermissionChecker.PERMISSION_DENIED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),500)
            }
        }else {
            mViewModel.getStatus()
        }



    }

    override fun onRequestPermissionsResult( requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
         500->{
             if (grantResults[0]==PermissionChecker.PERMISSION_GRANTED) mViewModel.getStatus()
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
        mViewModel.StatusList.observe(this, Observer {
            it!!
            mViewModel.setStatus()
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
                else {
                    view.setImageURI(it[i].toUri())
                }
                view.setOnClickListener {_it->
                    val intent:Intent  = Intent(baseContext,StatusActivity::class.java)
                    intent.putExtra("path",it[i].path)
                    startActivity(intent)
                }
                binding.grid.addView(view)
                println({ it[i].path.contains(".mp4") })

            }
        })

    }


    fun openStatus(view:View){
        val intent:Intent = Intent(baseContext,StatusActivity::class.java)
        startActivity(intent)
    }

    // For Click Actions
     class ClickHandler(context :Context){
        lateinit var context:Context
        fun onClickQr(view: View){

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





}