package com.android.whatstools.utlis

import android.content.Context
import android.content.SharedPreferences
import com.android.whatstools.MessageEntity

class sharedPrefarenceService {
    companion object{
        val app:String="MY_APP_SHARED"
        fun storeNotification(context: Context,messageEntity: MessageEntity){
            context.getSharedPreferences(app,Context.MODE_PRIVATE).edit()
                .putString("name",messageEntity.name)
                .putString("message",messageEntity.message)
                .putString("profile",messageEntity.profile)
                .putLong("date",messageEntity.date)
                .putBoolean("isStored",true)
                .apply()
        }
        fun getStoredNotification(context: Context):MessageEntity{
            context.getSharedPreferences(app,Context.MODE_PRIVATE).edit().putBoolean("isStored",false).apply()

            return MessageEntity(
                context.getSharedPreferences(app,Context.MODE_PRIVATE).getString("name","")!!,
                context.getSharedPreferences(app,Context.MODE_PRIVATE).getString("message","")!!,
                context.getSharedPreferences(app,Context.MODE_PRIVATE).getString("profile","")!!,
                date = context.getSharedPreferences(app,Context.MODE_PRIVATE).getLong("date",0)
            )

        }
        fun isStored(context: Context):Boolean{
            return context.getSharedPreferences(app,Context.MODE_PRIVATE).getBoolean("isStored",false)
        }
    }
}


