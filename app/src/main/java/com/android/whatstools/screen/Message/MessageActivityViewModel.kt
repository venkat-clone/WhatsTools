package com.android.whatstools.screen.Message

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.whatstools.MessageEntity

import com.android.whatstools.dataBase.message.Repository

class MessageActivityViewModel :ViewModel() {
    lateinit var repository: Repository
    lateinit var messageLIst:LiveData<List<MessageEntity>>
    fun initDB(context: Application){
        repository = Repository(context)
        messageLIst= repository.getMessages()
    }
    fun insertMessage(){
        repository.insertMessage(MessageEntity("venkey","haii","profile.png",8184926683F))
    }



}