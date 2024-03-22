package com.example.interactivemapcebucityapp;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class BackgroundMusicService extends Service {

    private MediaPlayer mediaPlayer;
    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        BackgroundMusicService getService() {
            return BackgroundMusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.vergee);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.3f, 0.3f);
    }

    public void startMusic() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            if (action.equals("START_MUSIC")) {
                startMusic();
            } else if (action.equals("STOP_MUSIC")) {
                stopMusic();
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
