package com.example.interactivemapcebucityapp;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class DepthPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(@NonNull View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)
            view.setAlpha(0f);
        } else if (position <= 0) { // [-1,0]
            view.setAlpha(1f);
            view.setTranslationX(0f);
            view.setScaleX(1f);
            view.setScaleY(1f);
        } else if (position <= 1) { // (0,1]
            view.setAlpha(1 - position);
            view.setTranslationX(pageWidth * -position);
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        } else { // (1,+Infinity]
            view.setAlpha(0f);
        }
    }
}
