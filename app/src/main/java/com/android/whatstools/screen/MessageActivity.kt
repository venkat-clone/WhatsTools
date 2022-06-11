package com.android.whatstools.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.whatstools.MessageEntity
import com.android.whatstools.R
import com.android.whatstools.databinding.ActivityMessageBinding
import com.android.whatstools.screen.Message.MessageActivityViewModel
import com.android.whatstools.screen.Message.MessageAdapter

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
        viewModel.initDB(application)
        adapter = MessageAdapter(this)
        binding.messageRecycler.adapter = adapter
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

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
            adapter.upDate(list)

        }

    }
}