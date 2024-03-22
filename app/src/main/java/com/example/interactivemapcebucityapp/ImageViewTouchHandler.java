package com.example.interactivemapcebucityapp;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

public class ImageViewTouchHandler implements View.OnTouchListener {

    private final ViewGroup parentView;
    private float initialX, initialY;
    private ObjectAnimator continuousRotationAnimator;

    public ImageViewTouchHandler(ViewGroup parentView) {
        this.parentView = parentView;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handleActionDown(view, event);
                break;

            case MotionEvent.ACTION_MOVE:
                handleActionMove(view, event);
                break;

            case MotionEvent.ACTION_UP:
                handleActionUp(view);
                break;
        }
        return true;
    }

    private void handleActionDown(View view, MotionEvent event) {
        initialX = view.getX() - event.getRawX();
        initialY = view.getY() - event.getRawY();
        startContinuousRotation(view);
    }

    private void handleActionMove(View view, MotionEvent event) {
        float newX = event.getRawX() + initialX;
        float newY = event.getRawY() + initialY;

        float minX = 0;
        float minY = 0;
        float maxX = parentView.getWidth() - view.getWidth();
        float maxY = parentView.getHeight() - view.getHeight();

        newX = Math.max(minX, Math.min(newX, maxX));
        newY = Math.max(minY, Math.min(newY, maxY));

        view.setX(newX);
        view.setY(newY);
    }

    private void handleActionUp(View view) {
        stopContinuousRotation();
        rotateViewBack(view);
    }

    private void startContinuousRotation(View view) {
        continuousRotationAnimator = ObjectAnimator.ofFloat(view, "rotation", 0, 360);
        continuousRotationAnimator.setDuration(3000);
        continuousRotationAnimator.setInterpolator(new LinearInterpolator());
        continuousRotationAnimator.setRepeatCount(ValueAnimator.INFINITE);
        continuousRotationAnimator.start();
    }

    private void stopContinuousRotation() {
        if (continuousRotationAnimator != null && continuousRotationAnimator.isRunning()) {
            continuousRotationAnimator.cancel();
        }
    }

    private void rotateViewBack(View view) {
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(view, "rotation", view.getRotation(), 0);
        rotateAnimator.setDuration(600);
        rotateAnimator.setInterpolator(new LinearInterpolator());
        rotateAnimator.start();
    }
}