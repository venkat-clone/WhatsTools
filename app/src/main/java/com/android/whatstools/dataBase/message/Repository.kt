package com.android.whatstools.dataBase.message

import android.app.Application
import androidx.lifecycle.LiveData
import com.android.whatstools.MessageEntity

class Repository(context: Application) {
    val context: Application
    private val dataBase:MessageDataBase
    val dao:Dao
    init {
        this.context =context
        this.dataBase = MessageDataBase.getDataBase(context)
        this.dao = dataBase.dao()
    }
    fun insertMessage(messageEntity: MessageEntity){
        dataBase.databaseWriterExecutor.execute() {
            dao.insert(messageEntity)
        }
    }
    fun getMessages(): LiveData<List<MessageEntity>> {
        return dao.getAllNotes()
    }


}