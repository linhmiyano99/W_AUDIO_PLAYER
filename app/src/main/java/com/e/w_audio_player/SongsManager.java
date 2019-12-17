package com.e.w_audio_player;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class SongsManager {
    // SDCard Path
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

    // Constructor
    public SongsManager(){

    }

    public ArrayList<HashMap<String, String>> getPlayList(){
        songsList.clear();
        // lấy file nhạc từ DIRECTORY download và DIRECTORY music
        addMusicFileFrom(String.valueOf(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MUSIC )));
        addMusicFileFrom(String.valueOf(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS )));
        return songsList;
    }

    private void addMusicFileFrom(String dirPath){

        final File musicDir = new File(dirPath);
        if(!musicDir.exists()){
            musicDir.mkdir();
            return;
        }
        final File[] files = musicDir.listFiles();
        for(File file : files){
            HashMap<String, String> song = new HashMap<String, String>();
            song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
            song.put("songPath", file.getPath());

            // Add file mp3
            if(file.getPath().endsWith(".mp3")){
                songsList.add(song);
            }

        }
    }
}
