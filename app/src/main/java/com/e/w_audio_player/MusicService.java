package com.e.w_audio_player;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.HashMap;

import static com.e.w_audio_player.App.CHANNEL_ID_1;

public class MusicService extends Service {
    public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    @Override
    public void onCreate() {
        super.onCreate();
        SongsManager plm = new SongsManager();
        songsList = plm.getPlayList();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @SuppressLint("NewApi")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int input = Integer.parseInt(intent.getStringExtra("songIndex"));

        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_music)
                .setContentTitle(songsList.get(input).get("songTitle"))
                .addAction(R.drawable.ic_skip_previous, "prev", null)
                .addAction(R.drawable.ic_pause, "pause", null)
                .addAction(R.drawable.ic_skip_next, "next", null)
                .setContentIntent(pendingIntent)
                .setStyle(new Notification.MediaStyle()
                        .setShowActionsInCompactView(0,1,2))
                .build();
        startForeground(1, notification);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
