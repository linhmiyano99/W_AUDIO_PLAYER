package com.e.w_audio_player;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayListActivity extends ListActivity {
    // Songs list
    public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    private NotificationManagerCompat notificationManagerCompat;
    int songIndex;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist);

        notificationManagerCompat = NotificationManagerCompat.from(this);

        ArrayList<HashMap<String, String>> songsListData = new ArrayList<HashMap<String, String>>();
        songIndex = -1;
        SongsManager plm = new SongsManager();
        // get all songs from sdcard
        this.songsList = plm.getPlayList();

        // looping through playlist
        for (int i = 0; i < songsList.size(); i++) {
            // creating new HashMap
            HashMap<String, String> song = songsList.get(i);

            // adding HashList to ArrayList
            songsListData.add(song);
        }

        // Adding menuItems to ListView
        ListAdapter adapter = new SimpleAdapter(this, songsListData,
                R.layout.playlist_item, new String[] { "songTitle" }, new int[] {
                R.id.songTitle });

        setListAdapter(adapter);

        // selecting single ListView item
        ListView lv = getListView();
        // listening to single listitem click
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting listitem index
                songIndex = position;
                if(songIndex >= 0)
                sendNotification(songIndex);
                // Starting new intent
                Intent in = new Intent(getApplicationContext(),  MainActivity.class);
                // Sending songIndex to PlayerActivity
                in.putExtra("songIndex", songIndex);
                setResult(100, in);
                // Closing PlayListView
                finish();
            }
        });
    }
    @SuppressLint("NewApi")
    public void sendNotification(int index) {
        startService();
//        Notification channel = new Notification.Builder(getApplicationContext(), CHANNEL_ID_1)
//                .setSmallIcon(R.drawable.ic_music)
//                .setContentTitle(songsList.get(index).get("songTitle"))
//                .addAction(R.drawable.ic_skip_previous, "prev", null)
//                .addAction(R.drawable.ic_pause, "pause", null)
//                .addAction(R.drawable.ic_skip_next, "next", null)
//                .setStyle(new Notification.MediaStyle()
//                        .setShowActionsInCompactView(0,1,2))
//                .build();
//        notificationManagerCompat.notify(1, channel);
    }

    public void startService(){
        Intent serviceIntent = new Intent(this, MusicService.class);
        serviceIntent.putExtra("songIndex", String.valueOf(songIndex));
        ContextCompat.startForegroundService(this, serviceIntent);
    }
    public void stopService(){
        Intent serviceIntent = new Intent(this, MusicService.class);
        stopService(serviceIntent);
    }
}