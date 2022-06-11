package com.android.whatstools.dataBase.message

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.whatstools.MessageEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(
    entities = [MessageEntity::class],
    version = 2,
    exportSchema = false
)
abstract class  MessageDataBase :RoomDatabase() {
    abstract fun dao():Dao

    private val _NUMBER_OF_THREADS= 4
    val databaseWriterExecutor:ExecutorService = Executors.newFixedThreadPool(_NUMBER_OF_THREADS)
    companion object{
        var INSTANCE:MessageDataBase? = null
        fun getDataBase(context:Application) : MessageDataBase{
            if(INSTANCE==null){
                synchronized(MessageDataBase::class.java) {
                    if (MessageDataBase.INSTANCE == null) {
                        MessageDataBase.INSTANCE =
                            Room.databaseBuilder(
                                context,
                                MessageDataBase::class.java,
                                "Notification"
                            )
                                .fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}