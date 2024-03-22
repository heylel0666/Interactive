package com.example.interactivemapcebucityapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
public class showImageDialog {
    private Context context;
    private int[] imageResources; // Array to store image resource IDs
    private int currentIndex = 0;
    private boolean isRadioGroupDialogShown2;

    public showImageDialog(Context context, int[] imageResources) {
        this.context = context;
        this.imageResources = imageResources;
    }
    @SuppressLint("ClickableViewAccessibility")

    public void showImage1Dialog(DialogDismissCallback callback) {
        Dialog newdialog1 = new Dialog(context, R.style.NoDimDialogTheme);
        newdialog1.setOnDismissListener(dialog -> {
            isRadioGroupDialogShown2 = false;
            if (callback != null) {
                callback.onDismiss();
            }
        });
        newdialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        newdialog1.setContentView(R.layout.custom_dialog1);
        Window newWindow1 = newdialog1.getWindow();
        newWindow1.setGravity(Gravity.CENTER);
        newWindow1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Apply the animation to the ViewPager
        ;
        ViewPager viewPager2 = newdialog1.findViewById(R.id.viewPager2);
        List<Integer> imageList = Arrays.asList(
                R.drawable.info_marmolpeak1,
                R.drawable.info_marmolpeak2, // Add more image resources as needed
                R.drawable.info_marmolpeak3
        );

        ImagePagerAdapter adapter = new ImagePagerAdapter(context, imageList);
        viewPager2.setAdapter(adapter);
        Toast customToast = new Toast(context);
        View toastView = LayoutInflater.from(context).inflate(R.layout.custom_toast_layout, null);
        TextView toastText = toastView.findViewById(R.id.toast_text);
        toastText.setText("Swipe image left or right to navigate");

        // Set custom background for the toast view
        toastView.setBackgroundResource(R.drawable.toast_background); // Set your custom background drawable here

        // Set custom view for the toast
        customToast.setView(toastView);
        customToast.setDuration(Toast.LENGTH_LONG);

        // Set gravity to display the Toast at the upper top
        customToast.setGravity(Gravity.TOP, 5, 5); // Adjust the second and third parameter for X and Y offset if needed

        customToast.show();
        // Add animation to the ViewPager transition
        viewPager2.setPageTransformer(true, new DepthPageTransformer());

        newdialog1.show();
    }

    public void showImage2Dialog(DialogDismissCallback callback) {
        Dialog newdialog2 = new Dialog(context, R.style.NoDimDialogTheme);
        newdialog2.setOnDismissListener(dialog -> {
            isRadioGroupDialogShown2 = false;
            if (callback != null) {
                callback.onDismiss();
            }
        });
        newdialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        newdialog2.setContentView(R.layout.custom_dialog2);
        Window newWindow2 = newdialog2.getWindow();
        newWindow2.setGravity(Gravity.CENTER);
        newWindow2.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Apply the animation to the ViewPager
        ;
        ViewPager viewPager2 = newdialog2.findViewById(R.id.viewPager2);
        List<Integer> imageList = Arrays.asList(
                R.drawable.info_kawasanfalls1,
                R.drawable.info_kawasanfalls2, // Add more image resources as needed
                R.drawable.info_kawasanfalls3
        );

        ImagePagerAdapter adapter = new ImagePagerAdapter(context, imageList);
        viewPager2.setAdapter(adapter);
        Toast customToast = new Toast(context);
        View toastView = LayoutInflater.from(context).inflate(R.layout.custom_toast_layout, null);
        TextView toastText = toastView.findViewById(R.id.toast_text);
        toastText.setText("Swipe image left or right to navigate");

        // Set custom background for the toast view
        toastView.setBackgroundResource(R.drawable.toast_background); // Set your custom background drawable here

        // Set custom view for the toast
        customToast.setView(toastView);
        customToast.setDuration(Toast.LENGTH_LONG);

        // Set gravity to display the Toast at the upper top
        customToast.setGravity(Gravity.TOP, 5, 5); // Adjust the second and third parameter for X and Y offset if needed

        customToast.show();
        // Add animation to the ViewPager transition
        viewPager2.setPageTransformer(true, new DepthPageTransformer());

        newdialog2.show();
    }
    public void showImage3Dialog(DialogDismissCallback callback) {
        Dialog newdialog3 = new Dialog(context, R.style.NoDimDialogTheme);
        newdialog3.setOnDismissListener(dialog -> {
            isRadioGroupDialogShown2 = false;
            if (callback != null) {
                callback.onDismiss();
            }
        });
        newdialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
        newdialog3.setContentView(R.layout.custom_dialog3);
        Window newWindow3 = newdialog3.getWindow();
        newWindow3.setGravity(Gravity.CENTER);
        newWindow3.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Apply the animation to the ViewPager
        ;
        ViewPager viewPager2 = newdialog3.findViewById(R.id.viewPager2);
        List<Integer> imageList = Arrays.asList(
                R.drawable.info_bantayan1,
                R.drawable.info_bantayan2, // Add more image resources as needed
                R.drawable.info_bantayan3
        );

        ImagePagerAdapter adapter = new ImagePagerAdapter(context, imageList);
        viewPager2.setAdapter(adapter);
        Toast customToast = new Toast(context);
        View toastView = LayoutInflater.from(context).inflate(R.layout.custom_toast_layout, null);
        TextView toastText = toastView.findViewById(R.id.toast_text);
        toastText.setText("Swipe image left or right to navigate");

        // Set custom background for the toast view
        toastView.setBackgroundResource(R.drawable.toast_background); // Set your custom background drawable here

        // Set custom view for the toast
        customToast.setView(toastView);
        customToast.setDuration(Toast.LENGTH_LONG);

        // Set gravity to display the Toast at the upper top
        customToast.setGravity(Gravity.TOP, 5, 5); // Adjust the second and third parameter for X and Y offset if needed

        customToast.show();
        // Add animation to the ViewPager transition
        viewPager2.setPageTransformer(true, new DepthPageTransformer());

        newdialog3.show();
    }
    public void showImage4Dialog(DialogDismissCallback callback) {
        Dialog newdialog4 = new Dialog(context, R.style.NoDimDialogTheme);
        newdialog4.setOnDismissListener(dialog -> {
            isRadioGroupDialogShown2 = false;
            if (callback != null) {
                callback.onDismiss();
            }
        });
        newdialog4.requestWindowFeature(Window.FEATURE_NO_TITLE);
        newdialog4.setContentView(R.layout.custom_dialog4);
        Window newWindow4 = newdialog4.getWindow();
        newWindow4.setGravity(Gravity.CENTER);
        newWindow4.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Apply the animation to the ViewPager
        ;
        ViewPager viewPager2 = newdialog4.findViewById(R.id.viewPager2);
        List<Integer> imageList = Arrays.asList(
                R.drawable.info_camotesisland1,
                R.drawable.info_camotesisland2, // Add more image resources as needed
                R.drawable.info_camotesisland3
        );

        ImagePagerAdapter adapter = new ImagePagerAdapter(context, imageList);
        viewPager2.setAdapter(adapter);
        Toast customToast = new Toast(context);
        View toastView = LayoutInflater.from(context).inflate(R.layout.custom_toast_layout, null);
        TextView toastText = toastView.findViewById(R.id.toast_text);
        toastText.setText("Swipe image left or right to navigate");

        // Set custom background for the toast view
        toastView.setBackgroundResource(R.drawable.toast_background); // Set your custom background drawable here

        // Set custom view for the toast
        customToast.setView(toastView);
        customToast.setDuration(Toast.LENGTH_LONG);

        // Set gravity to display the Toast at the upper top
        customToast.setGravity(Gravity.TOP, 5, 5); // Adjust the second and third parameter for X and Y offset if needed

        customToast.show();
        // Add animation to the ViewPager transition
        viewPager2.setPageTransformer(true, new DepthPageTransformer());

        newdialog4.show();
    }
    public void showImage5Dialog(DialogDismissCallback callback) {
        Dialog newdialog5 = new Dialog(context, R.style.NoDimDialogTheme);
        newdialog5.setOnDismissListener(dialog -> {
            isRadioGroupDialogShown2 = false;
            if (callback != null) {
                callback.onDismiss();
            }
        });
        newdialog5.requestWindowFeature(Window.FEATURE_NO_TITLE);
        newdialog5.setContentView(R.layout.custom_dialog5);
        Window newWindow5 = newdialog5.getWindow();
        newWindow5.setGravity(Gravity.CENTER);
        newWindow5.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Apply the animation to the ViewPager
        ;
        ViewPager viewPager2 = newdialog5.findViewById(R.id.viewPager2);
        List<Integer> imageList = Arrays.asList(
                R.drawable.info_sumilonislands1,
                R.drawable.info_sumilonislands2, // Add more image resources as needed
                R.drawable.info_sumilonislands3
        );

        ImagePagerAdapter adapter = new ImagePagerAdapter(context, imageList);
        viewPager2.setAdapter(adapter);
        Toast customToast = new Toast(context);
        View toastView = LayoutInflater.from(context).inflate(R.layout.custom_toast_layout, null);
        TextView toastText = toastView.findViewById(R.id.toast_text);
        toastText.setText("Swipe image left or right to navigate");

        // Set custom background for the toast view
        toastView.setBackgroundResource(R.drawable.toast_background); // Set your custom background drawable here

        // Set custom view for the toast
        customToast.setView(toastView);
        customToast.setDuration(Toast.LENGTH_LONG);

        // Set gravity to display the Toast at the upper top
        customToast.setGravity(Gravity.TOP, 5, 5); // Adjust the second and third parameter for X and Y offset if needed

        customToast.show();
        // Add animation to the ViewPager transition
        viewPager2.setPageTransformer(true, new DepthPageTransformer());

        newdialog5.show();
    }
    public void showImage6Dialog(DialogDismissCallback callback) {
        Dialog newdialog6 = new Dialog(context, R.style.NoDimDialogTheme);
        newdialog6.setOnDismissListener(dialog -> {
            isRadioGroupDialogShown2 = false;
            if (callback != null) {
                callback.onDismiss();
            }
        });
        newdialog6.requestWindowFeature(Window.FEATURE_NO_TITLE);
        newdialog6.setContentView(R.layout.custom_dialog6);
        Window newWindow6 = newdialog6.getWindow();
        newWindow6.setGravity(Gravity.CENTER);
        newWindow6.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Apply the animation to the ViewPager
        ;
        ViewPager viewPager2 = newdialog6.findViewById(R.id.viewPager2);
        List<Integer> imageList = Arrays.asList(
                R.drawable.info_aguinidfalls1,
                R.drawable.info_aguinidfalls2
        );

        ImagePagerAdapter adapter = new ImagePagerAdapter(context, imageList);
        viewPager2.setAdapter(adapter);
        Toast customToast = new Toast(context);
        View toastView = LayoutInflater.from(context).inflate(R.layout.custom_toast_layout, null);
        TextView toastText = toastView.findViewById(R.id.toast_text);
        toastText.setText("Swipe image left or right to navigate");

        // Set custom background for the toast view
        toastView.setBackgroundResource(R.drawable.toast_background); // Set your custom background drawable here

        // Set custom view for the toast
        customToast.setView(toastView);
        customToast.setDuration(Toast.LENGTH_LONG);

        // Set gravity to display the Toast at the upper top
        customToast.setGravity(Gravity.TOP, 5, 5); // Adjust the second and third parameter for X and Y offset if needed

        customToast.show();
        // Add animation to the ViewPager transition
        viewPager2.setPageTransformer(true, new DepthPageTransformer());

        newdialog6.show();
    }
    public void showImage7Dialog(DialogDismissCallback callback) {
        Dialog newdialog7 = new Dialog(context, R.style.NoDimDialogTheme);
        newdialog7.setOnDismissListener(dialog -> {
            isRadioGroupDialogShown2 = false;
            if (callback != null) {
                callback.onDismiss();
            }
        });
        newdialog7.requestWindowFeature(Window.FEATURE_NO_TITLE);
        newdialog7.setContentView(R.layout.custom_dialog7);
        Window newWindow7 = newdialog7.getWindow();
        newWindow7.setGravity(Gravity.CENTER);
        newWindow7.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Apply the animation to the ViewPager
        ;
        ViewPager viewPager2 = newdialog7.findViewById(R.id.viewPager2);
        List<Integer> imageList = Arrays.asList(
                R.drawable.info_kandungaw1,
                R.drawable.info_kandungaw2,
                R.drawable.info_kandungaw3
        );

        ImagePagerAdapter adapter = new ImagePagerAdapter(context, imageList);
        viewPager2.setAdapter(adapter);
        Toast customToast = new Toast(context);
        View toastView = LayoutInflater.from(context).inflate(R.layout.custom_toast_layout, null);
        TextView toastText = toastView.findViewById(R.id.toast_text);
        toastText.setText("Swipe image left or right to navigate");

        // Set custom background for the toast view
        toastView.setBackgroundResource(R.drawable.toast_background); // Set your custom background drawable here

        // Set custom view for the toast
        customToast.setView(toastView);
        customToast.setDuration(Toast.LENGTH_LONG);

        // Set gravity to display the Toast at the upper top
        customToast.setGravity(Gravity.TOP, 5, 5); // Adjust the second and third parameter for X and Y offset if needed

        customToast.show();
        // Add animation to the ViewPager transition
        viewPager2.setPageTransformer(true, new DepthPageTransformer());

        newdialog7.show();
    }
    public void showImage8Dialog(DialogDismissCallback callback) {
        Dialog newdialog8 = new Dialog(context, R.style.NoDimDialogTheme);
        newdialog8.setOnDismissListener(dialog -> {
            isRadioGroupDialogShown2 = false;
            if (callback != null) {
                callback.onDismiss();
            }
        });
        newdialog8.requestWindowFeature(Window.FEATURE_NO_TITLE);
        newdialog8.setContentView(R.layout.custom_dialog8);
        Window newWindow8 = newdialog8.getWindow();
        newWindow8.setGravity(Gravity.CENTER);
        newWindow8.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Apply the animation to the ViewPager
        ;
        ViewPager viewPager2 = newdialog8.findViewById(R.id.viewPager2);
        List<Integer> imageList = Arrays.asList(
                R.drawable.info_tumalogfalls1,
                R.drawable.info_tumalogfalls2
        );

        ImagePagerAdapter adapter = new ImagePagerAdapter(context, imageList);
        viewPager2.setAdapter(adapter);
        Toast customToast = new Toast(context);
        View toastView = LayoutInflater.from(context).inflate(R.layout.custom_toast_layout, null);
        TextView toastText = toastView.findViewById(R.id.toast_text);
        toastText.setText("Swipe image left or right to navigate");

        // Set custom background for the toast view
        toastView.setBackgroundResource(R.drawable.toast_background); // Set your custom background drawable here

        // Set custom view for the toast
        customToast.setView(toastView);
        customToast.setDuration(Toast.LENGTH_LONG);

        // Set gravity to display the Toast at the upper top
        customToast.setGravity(Gravity.TOP, 5, 5); // Adjust the second and third parameter for X and Y offset if needed

        customToast.show();
        // Add animation to the ViewPager transition
        viewPager2.setPageTransformer(true, new DepthPageTransformer());

        newdialog8.show();
    }
    public interface DialogDismissCallback {
        void onDismiss();
    }

}