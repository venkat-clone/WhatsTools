package com.android.whatstools.screen

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.whatstools.MessageEntity
import com.android.whatstools.databinding.ActivityMessageBinding
import com.android.whatstools.screen.message.MessageActivityViewModel
import com.android.whatstools.screen.message.MessageAdapter
import com.android.whatstools.utlis.broadcasts.NotificationReceiver


class MessageActivity : AppCompatActivity() {
    lateinit var binding : ActivityMessageBinding
    lateinit var viewModel: MessageActivityViewModel
    lateinit var adapter: MessageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMessageBinding.inflate(LayoutInflater.from(this))
        binding.lifecycleOwner =this
        viewModel = ViewModelProvider(this)[MessageActivityViewModel::class.java]
        setContentView(binding.root)
        binding.viewModel = viewModel
        adapter = MessageAdapter(this)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        setScreen()


    }

    private fun isServiceRunning(): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (NotificationReceiver::class.java.getName() == service.service.className) {
                return true
            }
        }
        return false
    }

    fun setScreen(){
        if(isServiceRunning()){
            binding.messageRecycler.adapter = adapter
            viewModel.initDB(application)
            viewModel.messageLIst.observe(this){
                Log.i("onB","${it.size}")
                val list:MutableList<MessageEntity> = ArrayList()
                list.add(MessageEntity("venkey","haii","profile.png",8184926683F))
                list.add(MessageEntity("venkey","haii","profile.png",8184926683F))
                list.add(MessageEntity("venkey","haii","profile.png",8184926683F))
                list.add(MessageEntity("venkey","haii","profile.png",8184926683F))
                list.add(MessageEntity("venkey","haii","profile.png",8184926683F))
                list.add(MessageEntity("venkey","haii","profile.png",8184926683F))
                list.add(MessageEntity("venkey","haii","profile.png",8184926683F))
                list.add(MessageEntity("venkey","haii","profile.png",8184926683F))
                list.add(MessageEntity("venkey","haii","profile.png",8184926683F))
                list.add(MessageEntity("venkey","haii","profile.png",8184926683F))
                list.add(MessageEntity("venkey","haii","profile.png",8184926683F))
                list.add(MessageEntity("venkey","haii","profile.png",8184926683F))
                list.add(MessageEntity("venkey","haii","profile.png",8184926683F))
                list.add(MessageEntity("venkey","haii","profile.png",8184926683F))
                list.add(MessageEntity("venkey","haii","profile.png",8184926683F))
                adapter.upDate(it)
                if(it.isEmpty())
                    viewModel.screen.value = 3
            }
            viewModel.screen.value = 1
        }
        else{
            viewModel.screen.value = 2
        }

    }

    override fun onResume() {
        super.onResume()
        setScreen()
    }

    fun requestPermission(v:View){
        startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
    }

}