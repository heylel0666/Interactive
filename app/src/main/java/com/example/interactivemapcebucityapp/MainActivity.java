package com.example.interactivemapcebucityapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private BackgroundMusicService musicService;
    private boolean isMusicBound = false;

    private final ServiceConnection musicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BackgroundMusicService.LocalBinder binder = (BackgroundMusicService.LocalBinder) service;
            musicService = binder.getService();
            isMusicBound = true;
            musicService.startMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isMusicBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnClick = findViewById(R.id.btnClick);
        final Animation blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blink);

        Intent musicIntent = new Intent(this, BackgroundMusicService.class);
        bindService(musicIntent, musicConnection, Context.BIND_AUTO_CREATE);

        btnClick.startAnimation(blinkAnimation);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Stop the animation
                blinkAnimation.cancel();

                // Transition to the next activity
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isMusicBound) {
            unbindService(musicConnection);
            isMusicBound = false;
        }
    }
}
