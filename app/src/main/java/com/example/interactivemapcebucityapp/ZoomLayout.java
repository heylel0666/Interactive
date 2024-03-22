package com.example.interactivemapcebucityapp;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.FrameLayout;

public class ZoomLayout extends FrameLayout {
    private final ScaleGestureDetector scaleGestureDetector;
    private final Matrix transformationMatrix = new Matrix();
    private float scaleFactor = 1.0f;

    private final PointF lastTouchPoint = new PointF();
    private final PointF startPanPoint = new PointF();
    private boolean isPanning = false;

    // Define zoom and pan limits
    private static final float MIN_SCALE = 1.0f;
    private static final float MAX_SCALE = 5.0f;

    public ZoomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);

        // Handle panning
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchPoint.set(event.getX(), event.getY());
                float[] matrixValues = new float[9];
                transformationMatrix.getValues(matrixValues);
                startPanPoint.set(matrixValues[Matrix.MTRANS_X], matrixValues[Matrix.MTRANS_Y]);
                isPanning = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isPanning) {
                    float deltaX = event.getX() - lastTouchPoint.x;
                    float deltaY = event.getY() - lastTouchPoint.y;
                    transformationMatrix.postTranslate(deltaX, deltaY);

                    // Apply panning bounds
                    applyPanningBounds();

                    lastTouchPoint.set(event.getX(), event.getY());
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isPanning = false;
                break;
        }

        return true;
    }
    private void applyPanningBounds() {
        float[] matrixValues = new float[9];
        transformationMatrix.getValues(matrixValues);

        float currentX = matrixValues[Matrix.MTRANS_X];
        float currentY = matrixValues[Matrix.MTRANS_Y];

        float minX = getWidth() - getWidth() * scaleFactor;
        float minY = getHeight() - getHeight() * scaleFactor;
        float maxX = 0;
        float maxY = 0;

        float newX = Math.min(Math.max(minX, currentX), maxX);
        float newY = Math.min(Math.max(minY, currentY), maxY);

        float deltaX = newX - currentX;
        float deltaY = newY - currentY;

        transformationMatrix.postTranslate(deltaX, deltaY);
    }

    @Override
    protected void dispatchDraw(android.graphics.Canvas canvas) {
        canvas.save();
        canvas.concat(transformationMatrix);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float newScale = scaleFactor * detector.getScaleFactor();
            newScale = Math.max(MIN_SCALE, Math.min(newScale, MAX_SCALE));

            float focusX = detector.getFocusX();
            float focusY = detector.getFocusY();

            float deltaX = (getWidth() / 2 - focusX) * (1 - newScale / scaleFactor);
            float deltaY = (getHeight() / 2 - focusY) * (1 - newScale / scaleFactor);

            transformationMatrix.postScale(newScale / scaleFactor, newScale / scaleFactor, focusX, focusY);
            transformationMatrix.postTranslate(deltaX, deltaY);

            applyPanningBounds();

            scaleFactor = newScale;
            invalidate();
            return true;
        }
    }
}
