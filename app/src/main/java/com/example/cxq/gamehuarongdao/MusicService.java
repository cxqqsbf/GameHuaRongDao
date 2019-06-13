package com.example.cxq.gamehuarongdao;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service  {
    private MediaPlayer player=null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.bgm1);
        player.setLooping(true);
        player.start();
        Log.d("MyService", "onCreate executed");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service1", "play");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(!player.isPlaying()){
                    player.start();
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if(player.isPlaying()){
            player.stop();
        }
        player.release();
        super.onDestroy();
    }

}
