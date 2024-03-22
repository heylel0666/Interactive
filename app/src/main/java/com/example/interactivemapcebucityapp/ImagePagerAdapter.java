package com.example.interactivemapcebucityapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {
    private Context context;
    private List<Integer> imageList;

    public ImagePagerAdapter(Context context, List<Integer> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        // This method checks if a view is associated with an object.
        // The default implementation is suitable for your use case.
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = createImageView();
        loadImage(position, imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private ImageView createImageView() {
        // Create and configure an ImageView instance.
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return imageView;
    }

    private void loadImage(int position, ImageView imageView) {
        // Load the image from the imageList into the ImageView.
        imageView.setImageResource(imageList.get(position));
    }
}
