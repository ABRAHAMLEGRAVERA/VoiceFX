package com.abraham.voicefx;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class VoiceService extends Service {

    private static final String CHANNEL_ID = "VoiceFX_Channel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("VoiceFX")
                .setContentText("Voice filter is active")
                .setSmallIcon(android.R.drawable.ic_btn_speak_now)
                .build();
        startForeground(1, notification);

        // Aquí puedes iniciar PulseAudio y el filtro predeterminado si quieres
        // Por ejemplo: Runtime.getRuntime().exec("pulseaudio --start ...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // El servicio continuará funcionando
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Parar PulseAudio y limpiar si es necesario
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "VoiceFX Service Channel",
                    NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
}
