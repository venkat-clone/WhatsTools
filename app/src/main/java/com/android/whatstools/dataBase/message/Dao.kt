package com.android.whatstools.dataBase.message

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.android.whatstools.MessageEntity

@Dao
interface  Dao {
    @Insert
    fun insert(messageEntity: MessageEntity)
    @Query("select * from Notification ORDER BY date DESC")
    fun getAllNotes(): LiveData<List<MessageEntity>>
}