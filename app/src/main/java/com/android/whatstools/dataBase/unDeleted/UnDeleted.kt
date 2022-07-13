package com.android.whatstools.dataBase.unDeleted

import android.content.Context
import com.android.whatstools.MessageEntity
import com.android.whatstools.utlis.sharedPrefarenceService

object UnDeleted {
    const val message:String = "Messages"
    fun putMessage(context: Context,id:String, messageEntity: MessageEntity){
        context.getSharedPreferences(message, Context.MODE_PRIVATE).edit()
            .putString("${id}name",messageEntity.name)
            .putString("${id}message",messageEntity.message)
            .putString("${id}profile",messageEntity.profile)
            .putLong("${id}date",messageEntity.date)
            .putBoolean(id,true)
            .apply()
    }
    fun getMessage(context: Context,id: String):MessageEntity{

        return MessageEntity(
            context.getSharedPreferences(message,Context.MODE_PRIVATE).getString("${id}name","")!!,
            context.getSharedPreferences(message,Context.MODE_PRIVATE).getString("${id}message","")!!,
            context.getSharedPreferences(message,Context.MODE_PRIVATE).getString("${id}profile","")!!,
            date = context.getSharedPreferences(message,Context.MODE_PRIVATE).getLong("${id}date",0)
        )
    }
    fun deleteMesssage(context: Context,id: String){
        context.getSharedPreferences(message, Context.MODE_PRIVATE).edit()
            .remove("${id}name")
            .remove("${id}message")
            .remove("${id}profile")
            .remove("${id}date")
            .remove(id)
            .apply()
    }
    fun hasMessage(context: Context,id: String):Boolean{
        return context.getSharedPreferences(message,Context.MODE_PRIVATE).getBoolean(id,false)
    }
}