package com.example.interactivemapcebucityapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class DragHandleTouchListener3 implements View.OnTouchListener {

    private int lastX;
    private int lastY;
    private int initialX;
    private int initialY;
    private boolean isDragging = false;
    private final Dialog dialog;

    public DragHandleTouchListener3(Dialog dialog) {
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
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;

                if (!isDragging && (Math.abs(event.getRawX() - initialX) > 10 || Math.abs(event.getRawY() - initialY) > 10)) {
                    // If the user moved the view by a certain threshold, consider it as dragging.
                    isDragging = true;
                }

                if (isDragging) {
                    Window window = dialog.getWindow();
                    if (window != null) {
                        WindowManager.LayoutParams params = window.getAttributes();
                        params.x += dx;
                        params.y += dy;
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
