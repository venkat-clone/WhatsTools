package com.android.whatstools.utlis.broadcasts

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.icu.number.CompactNotation
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.net.toUri
import com.android.whatstools.MessageEntity
import com.android.whatstools.dataBase.message.Repository
import com.android.whatstools.dataBase.unDeleted.UnDeleted
import com.android.whatstools.screen.MessageActivity
import com.android.whatstools.utlis.sharedPrefarenceService
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class NotificationReceiver: NotificationListenerService() {
    var TAG = "NotificationListenerTesting"


    lateinit var repository:Repository
    override fun onCreate() {
        super.onCreate()
        repository = Repository(application)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if(sbn.packageName.equals("com.whatsapp") &&
            sbn.notification.tickerText==null ){




            val bundle = sbn.notification.extras
            if(bundle["last_row_id"]==null) return
            val id = bundle["last_row_id"]!!.toString()
            Log.i("Receiver","Id:${id}")
            val profile:String=saveDp(sbn.notification)
            UnDeleted.putMessage(applicationContext,id,MessageEntity(
                bundle.getString("android.title")!!.replace(" ",""),
                bundle.getString("android.text")!!,
                profile,
                date = sbn.postTime
            ))
            if(UnDeleted.hasMessage(this,id) && bundle.getString("android.title")!="You"){
                val entity= UnDeleted.getMessage(this,id)
                UnDeleted.deleteMesssage(this,id)
                repository.insertMessage(entity)
                Log.i("Receiver","Storing to Db${id}")
                makeNotification(entity)
            }
            else{
                Log.i("Receiver","Storing to Shard${id}")
                UnDeleted.putMessage(applicationContext,id,MessageEntity(
                    bundle.getString("android.title")!!.replace(" ",""),
                    bundle.getString("android.text")!!,
                    profile,
                    date = sbn.postTime
                ))
            }



//            if( bundle.getString("android.text").equals("This message was deleted") &&
//                sharedPrefarenceService.isStored(applicationContext)
//            ) {
//                val entity= sharedPrefarenceService.getStoredNotification(applicationContext)
//                repository.insertMessage(entity)
//                makeNotification(entity)
//            }
//
//            else
//                sharedPrefarenceService.storeNotification( applicationContext,
//                    MessageEntity(
//                        bundle.getString("android.title")!!.replace(" ",""),
//                        bundle.getString("android.text")!!,
//                        profile,
//                        date = sbn.postTime
//                    )
//                )
        }


    }

    private fun makeNotification(entity: MessageEntity){
        val intent = Intent(this, MessageActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel_name"
            val descriptionText = "channel_description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NotificationCompat.Builder(applicationContext,"CHANNEL_ID")
                .setContentTitle(entity.name)
                .setContentText(entity.message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(IconCompat.createWithAdaptiveBitmapContentUri(File(entity.profile).toUri()))
                .setSubText("${entity.name} deleted a message")
                .setLargeIcon(BitmapFactory.decodeFile(entity.profile))
                .setAutoCancel(true)
        } else {
            NotificationCompat.Builder(applicationContext,"CHANNEL_ID")
                .setContentTitle(entity.name)
                .setContentText(entity.message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSubText("${entity.name} deleted a message")
                .setAutoCancel(true)
        }
        notification.setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(120, notification.build())
        }
    }


    private fun saveDp(notification: Notification):String{

            val bmp = notification.largeIcon

            val file = File(filesDir.path,"${notification.extras.getString("android.title")?.replace(" ","")}.png")

            if(!file.exists()){
                file.createNewFile()
            }
            val ou = FileOutputStream(file.path)
            try {
                bmp.compress(Bitmap.CompressFormat.PNG,100,ou)
                ou.flush()
                ou.close()
                Log.i("Db","File saved ${file.path}")
                return file.path
            }catch (e:IOException){
                Log.i("Db","Error $e")
                ou.flush()
                ou.close()
            }

        Log.i("Db","Fishey")
        return ""
    }



    override fun onNotificationRemoved(sbn: StatusBarNotification) {

        if(sbn.packageName.equals("com.whatsapp") &&
            sbn.notification.tickerText==null ){
            Log.i("Receiver","Deleting from Shared")

            UnDeleted.deleteMesssage(this,sbn.notification.extras["last_row_id"]!!.toString())
        }

    }
}