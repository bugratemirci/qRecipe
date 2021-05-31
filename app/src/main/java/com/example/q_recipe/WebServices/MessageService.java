package com.example.q_recipe.WebServices;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.q_recipe.MainActivity;
import com.example.q_recipe.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        Log.d("Başlık", title);
        Log.d("İçerik", body);

        bildirimOlustur(title, body);
    }


    public void bildirimOlustur(String baslik, String mesaj){

        NotificationCompat.Builder builder;
        NotificationManager bildirimYoneticisi =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        Intent ıntent = new Intent(this, MainActivity.class);
        PendingIntent gidilecekIntent = PendingIntent.getActivity(this,1,ıntent,PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Oreo sürümü için bu kod çalışacak.

            String kanalId = "kanalId";
            String kanalAd = "kanalAd";
            String kanalTanım = "kanalTanım";
            int kanalOnceligi = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel kanal = bildirimYoneticisi.getNotificationChannel(kanalId);

            if (kanal == null) {
                kanal = new NotificationChannel(kanalId, kanalAd, kanalOnceligi);
                kanal.setDescription(kanalTanım);
                bildirimYoneticisi.createNotificationChannel(kanal);
            }

            builder = new NotificationCompat.Builder(this, kanalId);
            builder.setContentTitle(baslik)  // gerekli
                    .setContentText(mesaj)  // gerekli
                    .setSmallIcon(R.drawable.ic_baseline_add_72) // gerekli
                    .setContentIntent(gidilecekIntent);


        } else { // OREO Sürümü haricinde bu kod çalışacak.

            builder = new NotificationCompat.Builder(this);
            builder.setContentTitle(baslik)  // gerekli
                    .setContentText(mesaj)  // gerekli
                    .setSmallIcon(R.drawable.ic_baseline_add_72) // gerekli
                    .setContentIntent(gidilecekIntent)
                    .setPriority(Notification.PRIORITY_HIGH);
        }

        bildirimYoneticisi.notify(1,builder.build());
    }
}
