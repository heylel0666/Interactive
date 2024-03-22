package com.example.interactivemapcebucityapp;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SecondActivity extends AppCompatActivity {
    public static ObjectAnimator continuousRotationAnimator;
    private Showingbtndialog btndialog;
    private ImageView muteButton;
    private boolean isMuted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ImageView northArrowImageView = findViewById(R.id.northArrowImageView);
        ImageView zoomableImageView = findViewById(R.id.zoomableImageView);
        btndialog = new Showingbtndialog(this, zoomableImageView);

        // Set up the ImageView touch handler
        ImageViewTouchHandler imageViewTouchHandler = new ImageViewTouchHandler(
                (ViewGroup) northArrowImageView.getParent());
        northArrowImageView.setOnTouchListener(imageViewTouchHandler);

        // Initialize muteButton and set click listener
        muteButton = findViewById(R.id.mutebutton);
        muteButton.setOnClickListener(v -> {
            // Toggle mute state
            isMuted = !isMuted;
            // Change image based on mute state
            int imageResource = isMuted ? R.drawable.icon_mute : R.drawable.icon_unmute;

            muteButton.setImageResource(imageResource);
            Intent intent = new Intent(this, BackgroundMusicService.class);
            if (isMuted) {
                intent.setAction("STOP_MUSIC");
            } else {
                intent.setAction("START_MUSIC");
            }
            startService(intent);
            // Check if the animation is running and cancel it to stop jiggling
            if (continuousRotationAnimator != null && continuousRotationAnimator.isRunning()) {
                continuousRotationAnimator.cancel();

            }

            // Start jiggling animation if not muted
            if (!isMuted) {
                startJigglingAnimation(muteButton);
            }
        });


        ImageView showbuttondialog = findViewById(R.id.up_layer_information_legends);
        showbuttondialog.setOnClickListener(v -> btndialog.showRadioGroupDialog());
    }

    private void startJigglingAnimation(View view) {
        if (continuousRotationAnimator != null && continuousRotationAnimator.isRunning()) {
            continuousRotationAnimator.cancel();
        }

        continuousRotationAnimator = ObjectAnimator.ofFloat(view, "rotation", -10f, 10f);
        continuousRotationAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        continuousRotationAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        continuousRotationAnimator.setDuration(100); // Set the duration of each rotation
        continuousRotationAnimator.start();
    }
}
