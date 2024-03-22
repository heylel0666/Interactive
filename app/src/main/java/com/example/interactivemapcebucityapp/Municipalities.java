package com.example.interactivemapcebucityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

public class Municipalities extends AppCompatActivity {
    private showImageDialog btndialog;
    private final float jumpHeight = 50f; // Adjust this value as needed
    private ImageView currentActiveButton = null;
    private float initialYTranslation;
    private ObjectAnimator jumpAnimator;
    private static final long IDLE_TIMEOUT = 2 * 60 * 1000; // 2 minutes in milliseconds
    private Handler idleHandler;
    private Runnable idleRunnable;
    private boolean isAnimationRunning = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipalities);

        idleHandler = new Handler();
        idleRunnable = () -> {
            // Navigate back to MainActivity
            Intent intent = new Intent(Municipalities.this, MainActivity.class);
            startActivity(intent);
            finish();
        };
        startIdleTimer();

        // Assuming you have an array of image resources defined as 'imageResources'
        int[] imageResources = {R.drawable.municipalities_tumalog, R.drawable.municipalities_tumalog2};

        // Pass the context and imageResources to the showImageDialog constructor
        btndialog = new showImageDialog(this, imageResources);

        ImageView buttontuburanBTN = findViewById(R.id.tuburanBTN);
        buttontuburanBTN.setOnClickListener(v -> {
            resetIdleTimer();
            if (!isAnimationRunning) {
                isAnimationRunning = true;
                // Disable the button to prevent double click
                buttontuburanBTN.setEnabled(false);

                if (currentActiveButton != null) {
                    // Reset the color of the previously active button
                    currentActiveButton.setColorFilter(null);
                }
                currentActiveButton = buttontuburanBTN;
                startContinuousJumpingAnimation(buttontuburanBTN);
                btndialog.showImage1Dialog(() -> {
                    currentActiveButton.setColorFilter(null);
                    currentActiveButton = null;
                    stopContinuousJumpingAnimation(buttontuburanBTN);

                    // Delayed re-enabling of the button after the animation completes
                    new Handler().postDelayed(() -> {
                        buttontuburanBTN.setEnabled(true);
                        isAnimationRunning = false;
                    }, 200); // Delay time in milliseconds, adjust as needed
                });
                buttontuburanBTN.setColorFilter(getResources().getColor(R.color.red));
            }
        });
        ImageView buttonKawasanfalls = findViewById(R.id.KawasanFallsBTN);
        buttonKawasanfalls.setOnClickListener(v -> {
            resetIdleTimer();
            if (!isAnimationRunning) {
                isAnimationRunning = true;
                // Disable the button to prevent double click
                buttonKawasanfalls.setEnabled(false);

                if (currentActiveButton != null) {
                    // Reset the color of the previously active button
                    currentActiveButton.setColorFilter(null);
                }
                currentActiveButton = buttonKawasanfalls;
                startContinuousJumpingAnimation(buttonKawasanfalls);
                btndialog.showImage2Dialog(() -> {
                    currentActiveButton.setColorFilter(null);
                    currentActiveButton = null;
                    stopContinuousJumpingAnimation(buttonKawasanfalls);

                    // Delayed re-enabling of the button after the animation completes
                    new Handler().postDelayed(() -> {
                        buttonKawasanfalls.setEnabled(true);
                        isAnimationRunning = false;
                    }, 200); // Delay time in milliseconds, adjust as needed
                });
                buttonKawasanfalls.setColorFilter(getResources().getColor(R.color.red));
            }
        });
        ImageView buttonBantayan = findViewById(R.id.BantayanBTN);
        buttonBantayan.setOnClickListener(v -> {
            resetIdleTimer();
            if (!isAnimationRunning) {
                isAnimationRunning = true;
                // Disable the button to prevent double click
                buttonBantayan.setEnabled(false);

                if (currentActiveButton != null) {
                    // Reset the color of the previously active button
                    currentActiveButton.setColorFilter(null);
                }
                currentActiveButton = buttonBantayan;
                startContinuousJumpingAnimation(buttonBantayan);
                btndialog.showImage3Dialog(() -> {
                    currentActiveButton.setColorFilter(null);
                    currentActiveButton = null;
                    stopContinuousJumpingAnimation(buttonBantayan);

                    // Delayed re-enabling of the button after the animation completes
                    new Handler().postDelayed(() -> {
                        buttonBantayan.setEnabled(true);
                        isAnimationRunning = false;
                    }, 200); // Delay time in milliseconds, adjust as needed
                });
                buttonBantayan.setColorFilter(getResources().getColor(R.color.red));
            }
        });
        ImageView buttonCamotes = findViewById(R.id.CamotesBTN);
        buttonCamotes.setOnClickListener(v -> {
            resetIdleTimer();
            if (!isAnimationRunning) {
                isAnimationRunning = true;
                // Disable the button to prevent double click
                buttonCamotes.setEnabled(false);

                if (currentActiveButton != null) {
                    // Reset the color of the previously active button
                    currentActiveButton.setColorFilter(null);
                }
                currentActiveButton = buttonCamotes;
                startContinuousJumpingAnimation(buttonCamotes);
                btndialog.showImage4Dialog(() -> {
                    currentActiveButton.setColorFilter(null);
                    currentActiveButton = null;
                    stopContinuousJumpingAnimation(buttonCamotes);

                    // Delayed re-enabling of the button after the animation completes
                    new Handler().postDelayed(() -> {
                        buttonCamotes.setEnabled(true);
                        isAnimationRunning = false;
                    }, 200); // Delay time in milliseconds, adjust as needed
                });
                buttonCamotes.setColorFilter(getResources().getColor(R.color.red));
            }
        });
        ImageView buttonSumilon = findViewById(R.id.SumilonBTN);
        buttonSumilon.setOnClickListener(v -> {
            resetIdleTimer();
            if (!isAnimationRunning) {
                isAnimationRunning = true;
                // Disable the button to prevent double click
                buttonSumilon.setEnabled(false);

                if (currentActiveButton != null) {
                    // Reset the color of the previously active button
                    currentActiveButton.setColorFilter(null);
                }
                currentActiveButton = buttonSumilon;
                startContinuousJumpingAnimation(buttonSumilon);
                btndialog.showImage5Dialog(() -> {
                    currentActiveButton.setColorFilter(null);
                    currentActiveButton = null;
                    stopContinuousJumpingAnimation(buttonSumilon);

                    // Delayed re-enabling of the button after the animation completes
                    new Handler().postDelayed(() -> {
                        buttonSumilon.setEnabled(true);
                        isAnimationRunning = false;
                    }, 200); // Delay time in milliseconds, adjust as needed
                });
                buttonSumilon.setColorFilter(getResources().getColor(R.color.red));
            }
        });
        ImageView buttonAguinid = findViewById(R.id.AguinidBTN);
        buttonAguinid.setOnClickListener(v -> {
            resetIdleTimer();
            if (!isAnimationRunning) {
                isAnimationRunning = true;
                // Disable the button to prevent double click
                buttonAguinid.setEnabled(false);

                if (currentActiveButton != null) {
                    // Reset the color of the previously active button
                    currentActiveButton.setColorFilter(null);
                }
                currentActiveButton = buttonAguinid;
                startContinuousJumpingAnimation(buttonAguinid);
                btndialog.showImage6Dialog(() -> {
                    currentActiveButton.setColorFilter(null);
                    currentActiveButton = null;
                    stopContinuousJumpingAnimation(buttonAguinid);

                    // Delayed re-enabling of the button after the animation completes
                    new Handler().postDelayed(() -> {
                        buttonAguinid.setEnabled(true);
                        isAnimationRunning = false;
                    }, 200); // Delay time in milliseconds, adjust as needed
                });
                buttonAguinid.setColorFilter(getResources().getColor(R.color.red));
            }
        });
        ImageView buttonKandungaw = findViewById(R.id.KandungawBTN);
        buttonKandungaw.setOnClickListener(v -> {
            resetIdleTimer();
            if (!isAnimationRunning) {
                isAnimationRunning = true;
                // Disable the button to prevent double click
                buttonKandungaw.setEnabled(false);

                if (currentActiveButton != null) {
                    // Reset the color of the previously active button
                    currentActiveButton.setColorFilter(null);
                }
                currentActiveButton = buttonKandungaw;
                startContinuousJumpingAnimation(buttonKandungaw);
                btndialog.showImage7Dialog(() -> {
                    currentActiveButton.setColorFilter(null);
                    currentActiveButton = null;
                    stopContinuousJumpingAnimation(buttonKandungaw);

                    // Delayed re-enabling of the button after the animation completes
                    new Handler().postDelayed(() -> {
                        buttonKandungaw.setEnabled(true);
                        isAnimationRunning = false;
                    }, 200); // Delay time in milliseconds, adjust as needed
                });
                buttonKandungaw.setColorFilter(getResources().getColor(R.color.red));
            }
        });
        ImageView buttonTumalog = findViewById(R.id.TumalogBTN);
// Inside your button click listeners
        buttonTumalog.setOnClickListener(v -> {
            resetIdleTimer();
            if (!isAnimationRunning) {
                isAnimationRunning = true;
                // Disable the button to prevent double click
                buttonTumalog.setEnabled(false);

                if (currentActiveButton != null) {
                    // Reset the color of the previously active button
                    currentActiveButton.setColorFilter(null);
                }
                currentActiveButton = buttonTumalog;
                startContinuousJumpingAnimation(buttonTumalog);
                btndialog.showImage8Dialog(() -> {
                    currentActiveButton.setColorFilter(null);
                    currentActiveButton = null;
                    stopContinuousJumpingAnimation(buttonTumalog);

                    // Delayed re-enabling of the button after the animation completes
                    new Handler().postDelayed(() -> {
                        buttonTumalog.setEnabled(true);
                        isAnimationRunning = false;
                    }, 200); // Delay time in milliseconds, adjust as needed
                });
                buttonTumalog.setColorFilter(getResources().getColor(R.color.red));
            }
        });
        ImageView returnButton = findViewById(R.id.returnb);
        returnButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Create an Intent to navigate back to SecondActivity
                Intent intent = new Intent(Municipalities.this, SecondActivity.class);
                startActivity(intent);

                // Optional: If you want to finish the current activity when navigating back
                finish();
            }
        });

    }
    private void startIdleTimer() {
        // Remove any existing callbacks to prevent duplicate callbacks
        idleHandler.removeCallbacks(idleRunnable);
        // Start the idle timer
        idleHandler.postDelayed(idleRunnable, IDLE_TIMEOUT);
    }

    private void resetIdleTimer() {
        // Reset the idle timer every time a button is clicked
        startIdleTimer();
    }
    private void startContinuousJumpingAnimation(ImageView imageView) {
        jumpAnimator = ObjectAnimator.ofFloat(imageView, "translationY", initialYTranslation, initialYTranslation - jumpHeight);
        jumpAnimator.setDuration(500); // Adjust the duration as needed
        jumpAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        jumpAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        jumpAnimator.setRepeatCount(ObjectAnimator.INFINITE);

        jumpAnimator.start();
    }

    private void stopContinuousJumpingAnimation(ImageView imageView) {
        if (jumpAnimator != null && jumpAnimator.isRunning()) {
            jumpAnimator.cancel(); // Cancel the animation

            imageView.setTranslationY(initialYTranslation); // Reset to the initial Y translation
        }
    }
}