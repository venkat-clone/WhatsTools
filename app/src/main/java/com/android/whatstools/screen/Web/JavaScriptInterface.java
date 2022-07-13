package com.android.whatstools.screen.Web;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class JavaScriptInterface {
    private Context context;
    public JavaScriptInterface(Context context) {
        this.context = context;
    }
    static String fileName="";
    static String mimeType ="";
    @JavascriptInterface
    public void getBase64FromBlobData(String base64Data) throws IOException {
        convertBase64StringToPdfAndStoreIt(base64Data,fileName);
    }
    public static String getBase64StringFromBlobUrl(String blobUrl,String mimeType) {
        if(blobUrl.startsWith("blob")){
            Log.i("Log","Loading Blob");
            JavaScriptInterface.mimeType = mimeType;
            fileName = URLUtil.guessFileName(
                    blobUrl, "", mimeType);
            return "javascript: var xhr = new XMLHttpRequest();" +
                    "xhr.open('GET', '"+ blobUrl +"', true);" +
                    "xhr.setRequestHeader('Content-type','"+mimeType+"');" +
                    "xhr.responseType = 'blob';" +
                    "xhr.onload = function(e) {" +
                    "    if (this.status == 200) {" +
                    "        var blobPdf = this.response;" +
                    "        var reader = new FileReader();" +
                    "        reader.readAsDataURL(blobPdf);" +
                    "        reader.onloadend = function() {" +
                    "            base64data = reader.result;" +
                    "            Android.getBase64FromBlobData(base64data);" +
                    "        }" +
                    "    }" +
                    "};" +
                    "xhr.send();";
        }
        return "javascript: console.log('It is not a Blob URL');";
    }
    private void convertBase64StringToPdfAndStoreIt(String base64PDf,String filename) throws IOException {

        File path = new File(Environment.getExternalStorageDirectory(),"/Whatstools/Downloads");
        path.mkdirs();
        final File dwldsPath = new File(path.getPath() + "/"+filename);
        byte[] pdfAsBytes = Base64.decode(base64PDf.replaceFirst("^data:"+mimeType+";base64,", ""), 0);
        FileOutputStream os;
        os = new FileOutputStream(dwldsPath, false);
        os.write(pdfAsBytes);
        os.flush();

//        if (dwldsPath.exists()) {
//            Intent intent = new Intent();
//            intent.setAction(android.content.Intent.ACTION_VIEW);
//            Uri apkURI = FileProvider.getUriForFile(context,context.getApplicationContext().getPackageName() + ".provider", dwldsPath);
//            Log.i("JavaScript",""+apkURI.getPath());
//            intent.setDataAndType(apkURI, MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf"));
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context,1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//            String CHANNEL_ID = "MYCHANNEL";
//            final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                NotificationChannel notificationChannel= new NotificationChannel(CHANNEL_ID,"name", NotificationManager.IMPORTANCE_LOW);
//                Notification notification = new Notification.Builder(context,CHANNEL_ID)
//                        .setContentText("You have got something new!")
//                        .setContentTitle("File downloaded")
//                        .setContentIntent(pendingIntent)
//                        .setChannelId(CHANNEL_ID)
//                        .setSmallIcon(android.R.drawable.sym_action_chat)
//                        .build();
//                if (notificationManager != null) {
//                    notificationManager.createNotificationChannel(notificationChannel);
//                    notificationManager.notify(notificationId, notification);
//                }
//
//            } else {
//                NotificationCompat.Builder b = new NotificationCompat.Builder(context, CHANNEL_ID)
//                        .setDefaults(NotificationCompat.DEFAULT_ALL)
//                        .setWhen(System.currentTimeMillis())
//                        .setSmallIcon(android.R.drawable.sym_action_chat)
//                        //.setContentIntent(pendingIntent)
//                        .setContentTitle("MY TITLE")
//                        .setContentText("MY TEXT CONTENT");
//
//                if (notificationManager != null) {
//                    notificationManager.notify(notificationId, b.build());
//                    Handler h = new Handler();
//                    long delayInMilliseconds = 1000;
//                    h.postDelayed(new Runnable() {
//                        public void run() {
//                            notificationManager.cancel(notificationId);
//                        }
//                    }, delayInMilliseconds);
//                }
//            }
//        }
        Toast.makeText(context, "File Downloaded", Toast.LENGTH_SHORT).show();
    }
}

