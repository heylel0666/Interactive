package com.example.interactivemapcebucityapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class DragHandleTouchListener implements View.OnTouchListener {

    private static final int TOUCH_THRESHOLD = 10;

    private int lastX;
    private int lastY;
    private int initialX;
    private int initialY;
    private boolean isDragging = false;
    private final Dialog dialog;

    public DragHandleTouchListener(Dialog dialog) {
        this.dialog = dialog;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isDragging = false;
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                initialX = lastX;
                initialY = lastY;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) event.getRawX() - lastX;
                int deltaY = (int) event.getRawY() - lastY;

                if (!isDragging && (Math.abs(event.getRawX() - initialX) > TOUCH_THRESHOLD
                        || Math.abs(event.getRawY() - initialY) > TOUCH_THRESHOLD)) {
                    isDragging = true;
                }

                if (isDragging) {
                    Window window = dialog.getWindow();
                    if (window != null) {
                        WindowManager.LayoutParams params = window.getAttributes();
                        params.x += deltaX;
                        params.y += deltaY;
                        window.setAttributes(params);
                    }
                }

                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
        }
        return true;
    }
}
