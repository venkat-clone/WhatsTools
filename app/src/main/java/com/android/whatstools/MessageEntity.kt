package com.android.whatstools
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notification")
data class MessageEntity(
    val name: String,
    val message: String,
    val profile: String,
    val number: Float=0F,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
)
//{
////    @PrimaryKey(autoGenerate = true)
////    lateinit var id: Int
//
////    @NonNull
////    @ColumnInfo(name = "name")
////    lateinit var name:String
////    @NonNull
////    @ColumnInfo(name = "message")
////    lateinit var message:String
////    @NonNull
////    @ColumnInfo(name = "profile")
////    lateinit var profile:String
//
//}