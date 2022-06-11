package com.android.whatstools.screen

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.android.whatstools.R
import com.android.whatstools.screen.status.MediaFragment
import com.android.whatstools.screen.status.StatusList
import com.android.whatstools.screen.status.StatusViewModel
import com.android.whatstools.screen.status.statusAdapter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

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
            viewModel.status.postValue(path)
        }
    }

    private fun observers(){
        viewModel.status.observe(this){
            if (it!=null){
                setFragment(medialFragment)
            }
        }
        viewModel.removeFragment.observe(this){
            if (it) removeFragment()
        }
    }

    override fun onBackPressed() {
        if (current!!.equals(mainFragment))
            super.onBackPressed()
        else
            removeFragment()

    }

    private fun setFragment(fragment:Fragment ){
        if(current != null) fm.beginTransaction().hide(current!!).commit()
        fm.beginTransaction().add(R.id.layout,fragment).commit()
        current = fragment
    }
    private fun removeFragment(){
        if(current!! == mainFragment) finish()
        else {
            fm.beginTransaction().remove(current!!).show(mainFragment).commit()
            current = mainFragment
        }
        viewModel.removeFragment.postValue(false)
    }



    fun whatsappShare(view: View){
        val intent:Intent = Intent(Intent.ACTION_SEND)
//        intent.data = File(viewModel.status.value!!).toUri()
        intent.putExtra(Intent.EXTRA_STREAM,Uri.parse(File(viewModel.status.value!!).absolutePath))
        intent.`package`= "com.whatsapp"
        intent.type=if(viewModel.status.value!!.contains("jpg")) "image/jpg" else "video/mp4"
        Log.i("File Type",intent.type+"")
        startActivity(intent)
    }
    fun Download(view: View){
        val download = File(viewModel.status.value!!)
        val appfolder = File("${Environment.getExternalStorageDirectory()}/Whatstools/Media")
        appfolder.mkdirs()
        val outputFile: File = File(appfolder, download.name)
        val outfile = FileOutputStream(outputFile)
        val infile = FileInputStream(download.path)
        val buffer = ByteArray(1024)
        var read: Int;
        while (infile.read(buffer).also { read = it } !== -1) {
            outfile.write(buffer, 0, read)
        }
        infile.close()
        // write the output file
        outfile.flush()
        outfile.close()
        sendBroadcast(
            Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + appfolder.getPath())
            )
        )
        Toast.makeText(this,"File Saved Successfully", Toast.LENGTH_SHORT).show();

    }
    fun Share(view: View){
        val intent:Intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM,Uri.parse(File(viewModel.status.value!!).absolutePath))
        intent.type=if(viewModel.status.value!!.contains("jpg")) "image/jpg" else "video/mp4"
        Log.i("File Type",intent.type+"")
        startActivity(intent)
    }

}