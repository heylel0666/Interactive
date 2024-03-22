package com.example.interactivemapcebucityapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Handler;
import android.os.SystemClock;
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
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.example.interactivemapcebucityapp.R.drawable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
public class Showingbtndialog {
    private final Context context;
    private final ImageView zoomableImageView;
    private boolean isRadioGroupDialogShown = false;
    private boolean isRadioGroupDialogShown2 = false;
    private boolean isRadioGroupDialogShown3 = false;
    private boolean toastShown = false;
    private Handler idleHandler;
    private long idleStartTime;
    private static final long IDLE_TIME_THRESHOLD = 2 * 60 * 1000;
    public Showingbtndialog(Context context, ImageView zoomableImageView) {
        this.context = context;
        this.zoomableImageView = zoomableImageView;
        idleHandler = new Handler();
        resetIdleTimer();
    }
    @SuppressLint("ClickableViewAccessibility")
    public void showRadioGroupDialog() {

        final Dialog dialog = new Dialog(context, R.style.NoDimDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_dialog);
        dialog.show();
        ImageView o1 = dialog.findViewById(R.id.buttonLayers);
        o1.setOnClickListener(v -> {
            if (!isRadioGroupDialogShown) {
                showLayersDialog();
                isRadioGroupDialogShown = true;
                dialog.dismiss();
            }
        });
        ImageView o2 = dialog.findViewById(R.id.buttonInformation);
        o2.setOnClickListener(v -> {
            if (!isRadioGroupDialogShown2) {
                showInfoDialog();
                isRadioGroupDialogShown2 = true;
                dialog.dismiss();
            }
        });
        ImageView o3 = dialog.findViewById(R.id.buttonLegends);
        o3.setOnClickListener(v -> {
            if (!isRadioGroupDialogShown3) {
                showLegendsDialog();
                isRadioGroupDialogShown3 = true;
                dialog.dismiss();
            }
        });

        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(view -> dialog.dismiss());
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.BOTTOM);
    }
    private void resetIdleTimer() {
        idleHandler.removeCallbacks(idleRunnable);
        idleStartTime = SystemClock.elapsedRealtime();
        idleHandler.postDelayed(idleRunnable, IDLE_TIME_THRESHOLD);
    }
    private Runnable idleRunnable = new Runnable() {
        @Override
        public void run() {
            // This code will be executed after the idle time threshold is reached.
            // You can add your actions here, such as returning to the main activity.
            Toast.makeText(context, "Idle for 2 minutes. Returning to the main activity.", Toast.LENGTH_SHORT).show();
            // For example, you can finish the current activity and start the main activity.
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    };
    private void showInfoDialog() {
        Dialog newdialog5 = new Dialog(context, R.style.NoDimDialogTheme);
        newdialog5.setOnDismissListener(dialog -> isRadioGroupDialogShown2 = false);
        newdialog5.requestWindowFeature(Window.FEATURE_NO_TITLE);
        newdialog5.setContentView(R.layout.custom_dialog);
        Window newWindow5 = newdialog5.getWindow();
        newWindow5.setGravity(Gravity.CENTER);
        newWindow5.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Load jiggle animation
        Animation jiggleAnimation = AnimationUtils.loadAnimation(context, R.anim.jiggle_animation);

        // Apply the animation to the ViewPager
;
        ViewPager viewPager = newdialog5.findViewById(R.id.viewPager);
        viewPager.startAnimation(jiggleAnimation);
        List<Integer> imageList = Arrays.asList(
                R.drawable.info_geology,
                R.drawable.info_topology, // Add more image resources as needed
                R.drawable.info_bathymetry,
                R.drawable.info_metallic,
                R.drawable.info_nonmetallic
        );

        ImagePagerAdapter adapter = new ImagePagerAdapter(context, imageList);
        viewPager.setAdapter(adapter);

        Toast customToast = new Toast(context);
        View toastView = LayoutInflater.from(context).inflate(R.layout.custom_toast_layout, null);
        TextView toastText = toastView.findViewById(R.id.toast_text);
        toastText.setText("Swipe left or right to navigate");

        // Set custom background for the toast view
        toastView.setBackgroundResource(R.drawable.toast_background); // Set your custom background drawable here

        customToast.setView(toastView);
        customToast.setDuration(Toast.LENGTH_LONG);

        customToast.setGravity(Gravity.TOP, 5, 5); // Adjust the second and third parameter for X and Y offset if needed

        customToast.show();
        viewPager.setPageTransformer(true, new DepthPageTransformer());

        newdialog5.show();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void showLegendsDialog() {
        Dialog newdialog3 = new Dialog(context, R.style.NoDimDialogTheme);
        newdialog3.setOnDismissListener(dialog -> isRadioGroupDialogShown3 = false);
        newdialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
        newdialog3.setContentView(R.layout.legends);
        Window newWindow3 = newdialog3.getWindow();
        newWindow3.setGravity(Gravity.CENTER);
        newWindow3.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Reset the idle timer when the user interacts with the legends dialog
        View rootView = newdialog3.findViewById(android.R.id.content);
        if (rootView != null) {
            rootView.setOnTouchListener((v, event) -> {
                resetIdleTimer();
                return false; // Ensure that the touch event is propagated to the views inside the dialog
            });
        }

        newdialog3.show();

        // Inside your Showingbtndialog class
        DragHandleTouchListener2 dragHandleTouchListener = new DragHandleTouchListener2(newdialog3);
        View dragHandle2 = newdialog3.findViewById(R.id.dragHandle2);
        dragHandle2.setOnTouchListener((v, event) -> {
            resetIdleTimer();
            return dragHandleTouchListener.onTouch(v, event);
        });
    }
    @SuppressLint({"RtlHardcoded", "ClickableViewAccessibility"})
    private void showLayersDialog() {
        Dialog newdialog = new Dialog(context, R.style.NoDimDialogTheme);
        newdialog.setOnDismissListener(dialog -> isRadioGroupDialogShown = false);
        newdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        newdialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(newdialog.getWindow()).setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        );
        newdialog.setContentView(R.layout.dialog_layers);
        Window newWindow = newdialog.getWindow();
        newWindow.setGravity(Gravity.CENTER);
        newWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView closeButton = newdialog.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(view -> newdialog.dismiss());

        newdialog.show();
        CheckBox lithologyCheckBox = newdialog.findViewById(R.id.CheckBox_Lithology);
        CheckBox faultsCheckBox = newdialog.findViewById(R.id.CheckBox_Faults);
        CheckBox elevationCheckBox = newdialog.findViewById(R.id.CheckBox_Elevation);
        CheckBox reliefCheckBox = newdialog.findViewById(R.id.CheckBox_Relief);
        CheckBox contoursCheckBox = newdialog.findViewById(R.id.CheckBox_Contours);
        CheckBox bathymetryCheckBox = newdialog.findViewById(R.id.CheckBox_Bathymetry);
        CheckBox underwaterreliefCheckBox = newdialog.findViewById(R.id.CheckBox_UnderwaterRelief);
        CheckBox bathcontoursCheckBox = newdialog.findViewById(R.id.CheckBox_BathContours);
        CheckBox metallicCheckBox = newdialog.findViewById(R.id.CheckBox_Metallic);
        CheckBox nonmetallicCheckBox = newdialog.findViewById(R.id.CheckBox_NonMetallic);
        CheckBox municipalitiesCheckBox = newdialog.findViewById(R.id.CheckBox_Tourismspots);
        CheckBox    dfMap = newdialog.findViewById(R.id.dfMap);
        View rootView = newdialog.findViewById(android.R.id.content);
        if (rootView != null) {
            rootView.setOnTouchListener((v, event) -> {
                resetIdleTimer();
                return false; // Ensure that the touch event is propagated to the views inside the dialog
            });
        }
        ImageView imageView = zoomableImageView; // Assuming this is the ImageView where you want to display the images
        dfMap.setOnClickListener(v -> {
            imageView.setImageResource(drawable.image_elevation_relief_bathymetry);
            lithologyCheckBox.setChecked(false);
            faultsCheckBox.setChecked(false);
            elevationCheckBox.setChecked(true);
            reliefCheckBox.setChecked(true);
            contoursCheckBox.setChecked(false);
            bathymetryCheckBox.setChecked(true);
            underwaterreliefCheckBox.setChecked(false);
            bathcontoursCheckBox.setChecked(false);
            metallicCheckBox.setChecked(false);
            nonmetallicCheckBox.setChecked(false);
            dfMap.setChecked(false);
            // Optionally, you can also start the next activity for municipalitiesCheckBox if it is checked
            if (municipalitiesCheckBox.isChecked()) {
                Intent intent = new Intent(context, Municipalities.class);
                context.startActivity(intent);
            }
        });

        SharedPreferences preferences = context.getSharedPreferences("checkbox_states", Context.MODE_PRIVATE);
        lithologyCheckBox.setChecked(preferences.getBoolean("Lithology", false));
        faultsCheckBox.setChecked(preferences.getBoolean("Faults", false));
        elevationCheckBox.setChecked(preferences.getBoolean("Elevation", true));
        reliefCheckBox.setChecked(preferences.getBoolean("Relief", true));
        contoursCheckBox.setChecked(preferences.getBoolean("Contours", false));
        bathymetryCheckBox.setChecked(preferences.getBoolean("Bathymetry", true));
        underwaterreliefCheckBox.setChecked(preferences.getBoolean("Underwater relief", false));
        bathcontoursCheckBox.setChecked(preferences.getBoolean("Bathymetry contours", false));
        metallicCheckBox.setChecked(preferences.getBoolean("Metallic", false));
        nonmetallicCheckBox.setChecked(preferences.getBoolean("Non metallic", false));

        updateImageBasedOnCheckboxes(imageView, lithologyCheckBox.isChecked(), faultsCheckBox.isChecked(), elevationCheckBox.isChecked(), reliefCheckBox.isChecked(), contoursCheckBox.isChecked(), bathymetryCheckBox.isChecked(), underwaterreliefCheckBox.isChecked(), bathcontoursCheckBox.isChecked(), metallicCheckBox.isChecked(), nonmetallicCheckBox.isChecked());

        View.OnClickListener checkBoxClickListener = (buttonView) -> {
            resetIdleTimer();
            if (buttonView == elevationCheckBox && (lithologyCheckBox.isChecked() || faultsCheckBox.isChecked())) {
                Toast.makeText(context, "Elevation cannot be checked with Lithology or Faults", Toast.LENGTH_LONG).show();
                elevationCheckBox.setChecked(false);
            } else if ((buttonView == lithologyCheckBox || buttonView == faultsCheckBox) && elevationCheckBox.isChecked()) {
                Toast.makeText(context, "Lithology or Faults cannot be checked with Elevation", Toast.LENGTH_LONG).show();
                faultsCheckBox.setChecked(false);
                lithologyCheckBox.setChecked(false);
            } else {
                updateImageBasedOnCheckboxes(imageView, lithologyCheckBox.isChecked(), faultsCheckBox.isChecked(), elevationCheckBox.isChecked(), reliefCheckBox.isChecked(), contoursCheckBox.isChecked(), bathymetryCheckBox.isChecked(), underwaterreliefCheckBox.isChecked(), bathcontoursCheckBox.isChecked(), metallicCheckBox.isChecked(), nonmetallicCheckBox.isChecked());
            }
        };

        lithologyCheckBox.setOnClickListener(checkBoxClickListener);
        faultsCheckBox.setOnClickListener(checkBoxClickListener);
        elevationCheckBox.setOnClickListener(checkBoxClickListener);
        reliefCheckBox.setOnClickListener((buttonView) -> updateImageBasedOnCheckboxes(imageView, lithologyCheckBox.isChecked(), faultsCheckBox.isChecked(), elevationCheckBox.isChecked(), reliefCheckBox.isChecked(), contoursCheckBox.isChecked(), bathymetryCheckBox.isChecked(), underwaterreliefCheckBox.isChecked(), bathcontoursCheckBox.isChecked(), metallicCheckBox.isChecked(), nonmetallicCheckBox.isChecked()));
        contoursCheckBox.setOnClickListener((buttonView) -> updateImageBasedOnCheckboxes(imageView, lithologyCheckBox.isChecked(), faultsCheckBox.isChecked(), elevationCheckBox.isChecked(), reliefCheckBox.isChecked(), contoursCheckBox.isChecked(), bathymetryCheckBox.isChecked(), underwaterreliefCheckBox.isChecked(), bathcontoursCheckBox.isChecked(), metallicCheckBox.isChecked(), nonmetallicCheckBox.isChecked()));
        bathymetryCheckBox.setOnClickListener((buttonView) -> updateImageBasedOnCheckboxes(imageView, lithologyCheckBox.isChecked(), faultsCheckBox.isChecked(), elevationCheckBox.isChecked(), reliefCheckBox.isChecked(), contoursCheckBox.isChecked(), bathymetryCheckBox.isChecked(), underwaterreliefCheckBox.isChecked(), bathcontoursCheckBox.isChecked(), metallicCheckBox.isChecked(), nonmetallicCheckBox.isChecked()));
        underwaterreliefCheckBox.setOnClickListener((buttonView) -> updateImageBasedOnCheckboxes(imageView, lithologyCheckBox.isChecked(), faultsCheckBox.isChecked(), elevationCheckBox.isChecked(), reliefCheckBox.isChecked(), contoursCheckBox.isChecked(), bathymetryCheckBox.isChecked(), underwaterreliefCheckBox.isChecked(), bathcontoursCheckBox.isChecked(), metallicCheckBox.isChecked(), nonmetallicCheckBox.isChecked()));
        bathcontoursCheckBox.setOnClickListener((buttonView) -> updateImageBasedOnCheckboxes(imageView, lithologyCheckBox.isChecked(), faultsCheckBox.isChecked(), elevationCheckBox.isChecked(), reliefCheckBox.isChecked(), contoursCheckBox.isChecked(), bathymetryCheckBox.isChecked(), underwaterreliefCheckBox.isChecked(), bathcontoursCheckBox.isChecked(), metallicCheckBox.isChecked(), nonmetallicCheckBox.isChecked()));
        metallicCheckBox.setOnClickListener((buttonView) -> updateImageBasedOnCheckboxes(imageView, lithologyCheckBox.isChecked(), faultsCheckBox.isChecked(), elevationCheckBox.isChecked(), reliefCheckBox.isChecked(), contoursCheckBox.isChecked(), bathymetryCheckBox.isChecked(), underwaterreliefCheckBox.isChecked(), bathcontoursCheckBox.isChecked(), metallicCheckBox.isChecked(), nonmetallicCheckBox.isChecked()));
        nonmetallicCheckBox.setOnClickListener((buttonView) -> updateImageBasedOnCheckboxes(imageView, lithologyCheckBox.isChecked(), faultsCheckBox.isChecked(), elevationCheckBox.isChecked(), reliefCheckBox.isChecked(), contoursCheckBox.isChecked(), bathymetryCheckBox.isChecked(), underwaterreliefCheckBox.isChecked(), bathcontoursCheckBox.isChecked(), metallicCheckBox.isChecked(), nonmetallicCheckBox.isChecked()));

        municipalitiesCheckBox.setOnClickListener((buttonView) -> {
            resetIdleTimer();
            if (municipalitiesCheckBox.isChecked()) {
                Intent intent = new Intent(context, Municipalities.class);
                context.startActivity(intent);
                municipalitiesCheckBox.setChecked(false);
            }
        });

        DragHandleTouchListener dragHandleTouchListener = new DragHandleTouchListener(newdialog);
        View dragHandle = newdialog.findViewById(R.id.dragHandle);
        dragHandle.setOnTouchListener(dragHandleTouchListener);

    }
    private void updateImageBasedOnCheckboxes(ImageView imageView, boolean isLithologyChecked, boolean isFaultsChecked, boolean isElevationChecked, boolean isReliefChecked, boolean isContoursChecked, boolean isBathymetryChecked, boolean isUnderwaterReliefChecked, boolean isBathContoursChecked, boolean isMetallicChecked, boolean isNonMetallicChecked) {

        HashMap<String, Integer> resourceMap = new HashMap<>();

        resourceMap.put("1000000000", drawable.image_lithology);
        resourceMap.put("1000000010", drawable.image_lithology_metallic);
        resourceMap.put("1000000001", drawable.image_lithology_nonmetallic);
        resourceMap.put("1000000011", drawable.image_lithology_metallic_nonmetallic);

        resourceMap.put("0100000000", drawable.image_faults);
        resourceMap.put("0100000010", drawable.image_faults_metallic);
        resourceMap.put("0100000001", drawable.image_faults_nonmetallic);
        resourceMap.put("0100000011", drawable.image_faults_metallic_nonmetallic);

        resourceMap.put("0100010000", drawable.image_faults_bathymetry);
        resourceMap.put("0100010010", drawable.image_faults_bathymetry_metallic);
        resourceMap.put("0100010001", drawable.image_faults_bathymetry_nonmetallic);
        resourceMap.put("0100010011", drawable.image_faults_bathymetry_metallic_nonmetallic);

        resourceMap.put("0100011000", drawable.image_faults_bathymetry_underwaterrelief);
        resourceMap.put("0100011010", drawable.image_faults_bathymetry_underwaterrelief_metallic);
        resourceMap.put("0100011001", drawable.image_faults_bathymetry_underwaterrelief_nonmetallic);
        resourceMap.put("0100011011", drawable.image_faults_bathymetry_underwaterrelief_metallic_nonmetallic);

        resourceMap.put("0100011100", drawable.image_faults_bathymetry_underwaterrelief_bathcontour);
        resourceMap.put("0100011110", drawable.image_faults_bathymetry_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0100011101", drawable.image_faults_bathymetry_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0100011111", drawable.image_faults_bathymetry_underwaterrelief_bathcontour_metallic_nonmetallic);

        resourceMap.put("0100010100", drawable.image_faults_bathymetry_bathcontour);
        resourceMap.put("0100010110", drawable.image_faults_bathymetry_bathcontour_metallic);
        resourceMap.put("0100010101", drawable.image_faults_bathymetry_bathcontour_nonmetallic);
        resourceMap.put("0100010111", drawable.image_faults_bathymetry_bathcontour_metallic_nonmetallic);

        resourceMap.put("0100001000", drawable.image_faults_underwaterrelief);
        resourceMap.put("0100001010", drawable.image_faults_underwaterrelief_metallic);
        resourceMap.put("0100001001", drawable.image_faults_underwaterrelief_nonmetallic);
        resourceMap.put("0100001011", drawable.image_faults_underwaterrelief_metallic_nonmetallic);

        resourceMap.put("0100001100", drawable.image_faults_underwaterrelief_bathcontour);
        resourceMap.put("0100001110", drawable.image_faults_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0100001101", drawable.image_faults_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0100001111", drawable.image_faults_underwaterrelief_bathcontour_metallic_nonmetallic);

        resourceMap.put("0100000100", drawable.image_faults_bathcontour);
        resourceMap.put("0100000110", drawable.image_faults_bathcontour_metallic);
        resourceMap.put("0100000101", drawable.image_faults_bathcontour_nonmetallic);
        resourceMap.put("0100000111", drawable.image_faults_bathcontour_metallic_nonmetallic);

        resourceMap.put("0010000000", drawable.image_elevation);
        resourceMap.put("0010000010", drawable.image_elevation_metallic);
        resourceMap.put("0010000001", drawable.image_elevation_nonmetallic);
        resourceMap.put("0010000011", drawable.image_elevation_metallic_nonmetallic);

        resourceMap.put("0001000000", drawable.image_relief);
        resourceMap.put("0001000010", drawable.image_relief_metallic);
        resourceMap.put("0001000001", drawable.image_relief_nonmetallic);
        resourceMap.put("0001000011", drawable.image_relief_metallic_nonmetallic);

        resourceMap.put("0001010000", drawable.image_relief_bathymetry);
        resourceMap.put("0001010010", drawable.image_relief_bathymetry_metallic);
        resourceMap.put("0001010001", drawable.image_relief_bathymetry_nonmetallic);
        resourceMap.put("0001010011", drawable.image_relief_bathymetry_metallic_nonmetallic);

        resourceMap.put("0001011000", drawable.image_relief_bathymetry_underwaterrelief);
        resourceMap.put("0001011010", drawable.image_relief_bathymetry_underwaterrelief_metallic);
        resourceMap.put("0001011001", drawable.image_relief_bathymetry_underwaterrelief_nonmetallic);
        resourceMap.put("0001011011", drawable.image_relief_bathymetry_underwaterrelief_metallic_nonmetallic);

        resourceMap.put("0001011100", drawable.image_relief_bathymetry_underwaterrelief_bathcontour);
        resourceMap.put("0001011110", drawable.image_relief_bathymetry_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0001011101", drawable.image_relief_bathymetry_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0001011111", drawable.image_relief_bathymetry_underwaterrelief_bathcontour_metallic_nonmetallic);

        resourceMap.put("0001010100", drawable.image_relief_bathymetry_bathcontour);
        resourceMap.put("0001010110", drawable.image_relief_bathymetry_bathcontour_metallic);
        resourceMap.put("0001010101", drawable.image_relief_bathymetry_bathcontour_nonmetallic);
        resourceMap.put("0001010111", drawable.image_relief_bathymetry_bathcontour_metallic_nonmetallic);

        resourceMap.put("0001001000", drawable.image_relief_underwaterrelief);
        resourceMap.put("0001001010", drawable.image_relief_underwaterrelief_metallic);
        resourceMap.put("0001001001", drawable.image_relief_underwaterrelief_nonmetallic);
        resourceMap.put("0001001011", drawable.image_relief_underwaterrelief_metallic_nonmetallic);

        resourceMap.put("0001001100", drawable.image_relief_underwaterrelief_bathcontour);
        resourceMap.put("0001001110", drawable.image_relief_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0001001101", drawable.image_relief_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0001001111", drawable.image_relief_underwaterrelief_bathcontour_metallic_nonmetallic);

        resourceMap.put("0001000100", drawable.image_relief_bathcontour);
        resourceMap.put("0001000110", drawable.image_relief_bathcontour_metallic);
        resourceMap.put("0001000101", drawable.image_relief_bathcontour_nonmetallic);
        resourceMap.put("0001000111", drawable.image_relief_bathcontour_metallic_nonmetallic);

        resourceMap.put("0000100000", drawable.image_contours);
        resourceMap.put("0000100010", drawable.image_contours_metallic);
        resourceMap.put("0000100001", drawable.image_contours_nonmetallic);
        resourceMap.put("0000100011", drawable.image_contours_metallic_nonmetallic);

        resourceMap.put("0000010000", drawable.image_bathymetry);
        resourceMap.put("0000010010", drawable.image_bathymetry_metallic);
        resourceMap.put("0000010001", drawable.image_bathymetry_nonmetallic);
        resourceMap.put("0000010011", drawable.image_bathymetry_metallic_nonmetallic);

        resourceMap.put("0000011000", drawable.image_bathymetry_underwaterrelief);
        resourceMap.put("0000011010", drawable.image_bathymetry_underwaterrelief_metallic);
        resourceMap.put("0000011001", drawable.image_bathymetry_underwaterrelief_nonmetallic);
        resourceMap.put("0000011011", drawable.image_bathymetry_underwaterrelief_metallic_nonmetallic);

        resourceMap.put("0000011100", drawable.image_bathymetry_underwaterrelief_bathcontour);
        resourceMap.put("0000011110", drawable.image_bathymetry_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0000011101", drawable.image_bathymetry_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0000011111", drawable.image_bathymetry_underwaterrelief_bathcontour_metallic_nonmetallic);

        resourceMap.put("0000010100", drawable.image_bathymetry_bathcontour);
        resourceMap.put("0000010110", drawable.image_bathymetry_bathcontour_metallic);
        resourceMap.put("0000010101", drawable.image_bathymetry_bathcontour_nonmetallic);
        resourceMap.put("0000010111", drawable.image_bathymetry_bathcontour_metallic_nonmetallic);

        resourceMap.put("0000001000", drawable.image_underwaterrelief);
        resourceMap.put("0000001010", drawable.image_underwaterrelief_metallic);
        resourceMap.put("0000001001", drawable.image_underwaterrelief_nonmetallic);
        resourceMap.put("0000001011", drawable.image_underwaterrelief_metallic_nonmetallic);

        resourceMap.put("0000001100", drawable.image_underwaterrelief_bathcontour);
        resourceMap.put("0000001110", drawable.image_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0000001101", drawable.image_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0000001111", drawable.image_underwaterrelief_bathcontour_metallic_nonmetallic);

        resourceMap.put("0000000100", drawable.image_bathcontours);
        resourceMap.put("0000000110", drawable.image_bathcontours_metallic);
        resourceMap.put("0000000101", drawable.image_bathcontours_nonmetallic);
        resourceMap.put("0000000111", drawable.image_bathcontours_metallic_nonmetallic);

        resourceMap.put("0000000010", drawable.image_metallic);
        resourceMap.put("0000000001", drawable.image_nonmetallic);
        resourceMap.put("0000000011", drawable.image_metallic_nonmetallic);

        resourceMap.put("1100000000", drawable.image_lithology_faults);
        resourceMap.put("1100000010", drawable.image_lithology_faults_metallic);
        resourceMap.put("1100000001", drawable.image_lithology_faults_nonmetallic);
        resourceMap.put("1100000011", drawable.image_lithology_faults_metallic_nonmetallic);

        resourceMap.put("1001000000", drawable.image_lithology_relief);
        resourceMap.put("1001000010", drawable.image_lithology_relief_metallic);
        resourceMap.put("1001000001", drawable.image_lithology_relief_nonmetallic);
        resourceMap.put("1001000011", drawable.image_lithology_relief_metallic_nonmetallic);
        resourceMap.put("1001010000", drawable.image_lithology_relief_bathymetry);
        resourceMap.put("1001010010", drawable.image_lithology_relief_bathymetry_metallic);
        resourceMap.put("1001010001", drawable.image_lithology_relief_bathymetry_nonmetallic);
        resourceMap.put("1001010011", drawable.image_lithology_relief_bathymetry_metallic_nonmetallic);
        resourceMap.put("1001011000", drawable.image_lithology_relief_bathymetry_underwaterrelief);
        resourceMap.put("1001011010", drawable.image_lithology_relief_bathymetry_underwaterrelief_metallic);
        resourceMap.put("1001011001", drawable.image_lithology_relief_bathymetry_underwaterrelief_nonmetallic);
        resourceMap.put("1001011011", drawable.image_lithology_relief_bathymetry_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("1001011100", drawable.image_lithology_relief_bathymetry_underwaterrelief_bathcontour);
        resourceMap.put("1001011110", drawable.image_lithology_relief_bathymetry_underwaterrelief_bathcontour_metallic);
        resourceMap.put("1001011101", drawable.image_lithology_relief_bathymetry_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("1001011111", drawable.image_lithology_relief_bathymetry_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("1001010100", drawable.image_lithology_relief_bathymetry_bathcontour);
        resourceMap.put("1001010110", drawable.image_lithology_relief_bathymetry_bathcontour_metallic);
        resourceMap.put("1001010101", drawable.image_lithology_relief_bathymetry_bathcontour_nonmetallic);
        resourceMap.put("1001010111", drawable.image_lithology_relief_bathymetry_bathcontour_metallic_nonmetallic);
        resourceMap.put("1001001000", drawable.image_lithology_relief_underwaterrelief);
        resourceMap.put("1001001010", drawable.image_lithology_relief_underwaterrelief_metallic);
        resourceMap.put("1001001001", drawable.image_lithology_relief_underwaterrelief_nonmetallic);
        resourceMap.put("1001001011", drawable.image_lithology_relief_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("1001001100", drawable.image_lithology_relief_underwaterrelief_bathcontour);
        resourceMap.put("1001001110", drawable.image_lithology_relief_underwaterrelief_bathcontour_metallic);
        resourceMap.put("1001001101", drawable.image_lithology_relief_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("1001001111", drawable.image_lithology_relief_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("1001000100", drawable.image_lithology_relief_bathcontour);
        resourceMap.put("1001000110", drawable.image_lithology_relief_bathcontour_metallic);
        resourceMap.put("1001000101", drawable.image_lithology_relief_bathcontour_nonmetallic);
        resourceMap.put("1001000111", drawable.image_lithology_relief_bathcontour_metallic_nonmetallic);

        resourceMap.put("1000100000", drawable.image_lithology_contours);
        resourceMap.put("1000100010", drawable.image_lithology_contours_metallic);
        resourceMap.put("1000100001", drawable.image_lithology_contours_nonmetallic);
        resourceMap.put("1000100011", drawable.image_lithology_contours_metallic_nonmetallic);
        resourceMap.put("1000110000", drawable.image_lithology_contours_bathymetry);
        resourceMap.put("1000110010", drawable.image_lithology_contours_bathymetry_metallic);
        resourceMap.put("1000110001", drawable.image_lithology_contours_bathymetry_nonmetallic);
        resourceMap.put("1000110011", drawable.image_lithology_contours_bathymetry_metallic_nonmetallic);
        resourceMap.put("1000111000", drawable.image_lithology_contours_bathymetry_underwaterrelief);
        resourceMap.put("1000111010", drawable.image_lithology_contours_bathymetry_underwaterrelief_metallic);
        resourceMap.put("1000111001", drawable.image_lithology_contours_bathymetry_underwaterrelief_nonmetallic);
        resourceMap.put("1000111011", drawable.image_lithology_contours_bathymetry_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("1000111100", drawable.image_lithology_contours_bathymetry_underwaterrelief_bathcontour);
        resourceMap.put("1000111110", drawable.image_lithology_contours_bathymetry_underwaterrelief_bathcontour_metallic);
        resourceMap.put("1000111101", drawable.image_lithology_contours_bathymetry_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("1000111111", drawable.image_lithology_contours_bathymetry_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("1000110100", drawable.image_lithology_contours_bathymetry_bathcontour);
        resourceMap.put("1000110110", drawable.image_lithology_contours_bathymetry_bathcontour_metallic);
        resourceMap.put("1000110101", drawable.image_lithology_contours_bathymetry_bathcontour_nonmetallic);
        resourceMap.put("1000110111", drawable.image_lithology_contours_bathymetry_bathcontour_metallic_nonmetallic);
        resourceMap.put("1000101000", drawable.image_lithology_contours_underwaterrelief);
        resourceMap.put("1000101010", drawable.image_lithology_contours_underwaterrelief_metallic);
        resourceMap.put("1000101001", drawable.image_lithology_contours_underwaterrelief_nonmetallic);
        resourceMap.put("1000101011", drawable.image_lithology_contours_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("1000101100", drawable.image_lithology_contours_underwaterrelief_bathcontour);
        resourceMap.put("1000101110", drawable.image_lithology_contours_underwaterrelief_bathcontour_metallic);
        resourceMap.put("1000101101", drawable.image_lithology_contours_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("1000101111", drawable.image_lithology_contours_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("1000100100", drawable.image_lithology_contours_bathcontour);
        resourceMap.put("1000100110", drawable.image_lithology_contours_bathcontour_metallic);
        resourceMap.put("1000100101", drawable.image_lithology_contours_bathcontour_nonmetallic);
        resourceMap.put("1000100111", drawable.image_lithology_contours_bathcontour_metallic_nonmetallic);

        resourceMap.put("1000010000", drawable.image_lithology_bathymetry);
        resourceMap.put("1000010010", drawable.image_lithology_bathymetry_metallic);
        resourceMap.put("1000010001", drawable.image_lithology_bathymetry_nonmetallic);
        resourceMap.put("1000010011", drawable.image_lithology_bathymetry_metallic_nonmetallic);
        resourceMap.put("1000011000", drawable.image_lithology_bathymetry_underwaterrelief);
        resourceMap.put("1000011010", drawable.image_lithology_bathymetry_underwaterrelief_metallic);
        resourceMap.put("1000011001", drawable.image_lithology_bathymetry_underwaterrelief_nonmetallic);
        resourceMap.put("1000011011", drawable.image_lithology_bathymetry_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("1000011100", drawable.image_lithology_bathymetry_underwaterrelief_bathcontour);
        resourceMap.put("1000011110", drawable.image_lithology_bathymetry_underwaterrelief_bathcontour_metallic);
        resourceMap.put("1000011101", drawable.image_lithology_bathymetry_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("1000011111", drawable.image_lithology_bathymetry_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("1000010100", drawable.image_lithology_bathymetry_bathcontour);
        resourceMap.put("1000010110", drawable.image_lithology_bathymetry_bathcontour_metallic);
        resourceMap.put("1000010101", drawable.image_lithology_bathymetry_bathcontour_nonmetallic);
        resourceMap.put("1000010111", drawable.image_lithology_bathymetry_bathcontour_metallic_nonmetallic);
        resourceMap.put("1000001000", drawable.image_lithology_underwaterrelief);
        resourceMap.put("1000001010", drawable.image_lithology_underwaterrelief_metallic);
        resourceMap.put("1000001001", drawable.image_lithology_underwaterrelief_nonmetallic);
        resourceMap.put("1000001011", drawable.image_lithology_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("1000001100", drawable.image_lithology_underwaterrelief_bathcontour);
        resourceMap.put("1000001110", drawable.image_lithology_underwaterrelief_bathcontour_metallic);
        resourceMap.put("1000001101", drawable.image_lithology_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("1000001111", drawable.image_lithology_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("1000000100", drawable.image_lithology_bathcontour);
        resourceMap.put("1000000110", drawable.image_lithology_bathcontour_metallic);
        resourceMap.put("1000000101", drawable.image_lithology_bathcontour_nonmetallic);
        resourceMap.put("1000000111", drawable.image_lithology_bathcontour_metallic_nonmetallic);

        resourceMap.put("0101000000", drawable.image_faults_relief);
        resourceMap.put("0101000010", drawable.image_faults_relief_metallic);
        resourceMap.put("0101000001", drawable.image_faults_relief_nonmetallic);
        resourceMap.put("0101000011", drawable.image_faults_relief_metallic_nonmetallic);

        resourceMap.put("0101010000", drawable.image_faults_relief_bathymetry);
        resourceMap.put("0101010010", drawable.image_faults_relief_bathymetry_metallic);
        resourceMap.put("0101010001", drawable.image_faults_relief_bathymetry_nonmetallic);
        resourceMap.put("0101010011", drawable.image_faults_relief_bathymetry_metallic_nonmetallic);
        resourceMap.put("0101011000", drawable.image_faults_relief_bathymetry_underwaterrelief);
        resourceMap.put("0101011010", drawable.image_faults_relief_bathymetry_underwaterrelief_metallic);
        resourceMap.put("0101011001", drawable.image_faults_relief_bathymetry_underwaterrelief_nonmetallic);
        resourceMap.put("0101011011", drawable.image_faults_relief_bathymetry_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("0101011100", drawable.image_faults_relief_bathymetry_underwaterrelief_bathcontour);
        resourceMap.put("0101011110", drawable.image_faults_relief_bathymetry_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0101011101", drawable.image_faults_relief_bathymetry_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0101011111", drawable.image_faults_relief_bathymetry_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("0101010100", drawable.image_faults_relief_bathymetry_bathcontour);
        resourceMap.put("0101010110", drawable.image_faults_relief_bathymetry_bathcontour_metallic);
        resourceMap.put("0101010101", drawable.image_faults_relief_bathymetry_bathcontour_nonmetallic);
        resourceMap.put("0101010111", drawable.image_faults_relief_bathymetry_bathcontour_metallic_nonmetallic);
        resourceMap.put("0101001000", drawable.image_faults_relief_underwaterrelief);
        resourceMap.put("0101001010", drawable.image_faults_relief_underwaterrelief_metallic);
        resourceMap.put("0101001001", drawable.image_faults_relief_underwaterrelief_nonmetallic);
        resourceMap.put("0101001011", drawable.image_faults_relief_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("0101001100", drawable.image_faults_relief_underwaterrelief_bathcontour);
        resourceMap.put("0101001110", drawable.image_faults_relief_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0101001101", drawable.image_faults_relief_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0101001111", drawable.image_faults_relief_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("0101000100", drawable.image_faults_relief_bathcontour);
        resourceMap.put("0101000110", drawable.image_faults_relief_bathcontour_metallic);
        resourceMap.put("0101000101", drawable.image_faults_relief_bathcontour_nonmetallic);
        resourceMap.put("0101000111", drawable.image_faults_relief_bathcontour_metallic_nonmetallic);

        resourceMap.put("0100100000", drawable.image_faults_contours);
        resourceMap.put("0100100010", drawable.image_faults_contours_metallic);
        resourceMap.put("0100100001", drawable.image_faults_contours_nonmetallic);
        resourceMap.put("0100100011", drawable.image_faults_contours_metallic_nonmetallic);
        resourceMap.put("0100110000", drawable.image_faults_contours_bathymetry);
        resourceMap.put("0100110010", drawable.image_faults_contours_bathymetry_metallic);
        resourceMap.put("0100110001", drawable.image_faults_contours_bathymetry_nonmetallic);
        resourceMap.put("0100110011", drawable.image_faults_contours_bathymetry_metallic_nonmetallic);
        resourceMap.put("0100111000", drawable.image_faults_contours_bathymetry_underwaterrelief);
        resourceMap.put("0100111010", drawable.image_faults_contours_bathymetry_underwaterrelief_metallic);
        resourceMap.put("0100111001", drawable.image_faults_contours_bathymetry_underwaterrelief_nonmetallic);
        resourceMap.put("0100111011", drawable.image_faults_contours_bathymetry_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("0100111100", drawable.image_faults_contours_bathymetry_underwaterrelief_bathcontour);
        resourceMap.put("0100111110", drawable.image_faults_contours_bathymetry_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0100111101", drawable.image_faults_contours_bathymetry_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0100111111", drawable.image_faults_contours_bathymetry_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("0100110100", drawable.image_faults_contours_bathymetry_bathcontour);
        resourceMap.put("0100110110", drawable.image_faults_contours_bathymetry_bathcontour_metallic);
        resourceMap.put("0100110101", drawable.image_faults_contours_bathymetry_bathcontour_nonmetallic);
        resourceMap.put("0100110111", drawable.image_faults_contours_bathymetry_bathcontour_metallic_nonmetallic);
        resourceMap.put("0100101000", drawable.image_faults_contours_underwaterrelief);
        resourceMap.put("0100101010", drawable.image_faults_contours_underwaterrelief_metallic);
        resourceMap.put("0100101001", drawable.image_faults_contours_underwaterrelief_nonmetallic);
        resourceMap.put("0100101011", drawable.image_faults_contours_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("0100101100", drawable.image_faults_contours_underwaterrelief_bathcontour);
        resourceMap.put("0100101110", drawable.image_faults_contours_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0100101101", drawable.image_faults_contours_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0100101111", drawable.image_faults_contours_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("0100100100", drawable.image_faults_contours_bathcontour);
        resourceMap.put("0100100110", drawable.image_faults_contours_bathcontour_metallic);
        resourceMap.put("0100100101", drawable.image_faults_contours_bathcontour_nonmetallic);
        resourceMap.put("0100100111", drawable.image_faults_contours_bathcontour_metallic_nonmetallic);

        resourceMap.put("0011000000", drawable.image_elevation_relief);
        resourceMap.put("0011000010", drawable.image_elevation_relief_metallic);
        resourceMap.put("0011000001", drawable.image_elevation_relief_nonmetallic);
        resourceMap.put("0011000011", drawable.image_elevation_relief_metallic_nonmetallic);
        resourceMap.put("0011010000", drawable.image_elevation_relief_bathymetry);
        resourceMap.put("0011010010", drawable.image_elevation_relief_bathymetry_metallic);
        resourceMap.put("0011010001", drawable.image_elevation_relief_bathymetry_nonmetallic);
        resourceMap.put("0011010011", drawable.image_elevation_relief_bathymetry_metallic_nonmetallic);
        resourceMap.put("0011011000", drawable.image_elevation_relief_bathymetry_underwaterrelief);
        resourceMap.put("0011011010", drawable.image_elevation_relief_bathymetry_underwaterrelief_metallic);
        resourceMap.put("0011011001", drawable.image_elevation_relief_bathymetry_underwaterrelief_nonmetallic);
        resourceMap.put("0011011011", drawable.image_elevation_relief_bathymetry_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("0011011100", drawable.image_elevation_relief_bathymetry_underwaterrelief_bathcontour);
        resourceMap.put("0011011110", drawable.image_elevation_relief_bathymetry_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0011011101", drawable.image_elevation_relief_bathymetry_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0011011111", drawable.image_elevation_relief_bathymetry_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("0011010100", drawable.image_elevation_relief_bathymetry_bathcontour);
        resourceMap.put("0011010110", drawable.image_elevation_relief_bathymetry_bathcontour_metallic);
        resourceMap.put("0011010101", drawable.image_elevation_relief_bathymetry_bathcontour_nonmetallic);
        resourceMap.put("0011010111", drawable.image_elevation_relief_bathymetry_bathcontour_metallic_nonmetallic);
        resourceMap.put("0011001000", drawable.image_elevation_relief_underwaterrelief);
        resourceMap.put("0011001010", drawable.image_elevation_relief_underwaterrelief_metallic);
        resourceMap.put("0011001001", drawable.image_elevation_relief_underwaterrelief_nonmetallic);
        resourceMap.put("0011001011", drawable.image_elevation_relief_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("0011001100", drawable.image_elevation_relief_underwaterrelief_bathcontour);
        resourceMap.put("0011001110", drawable.image_elevation_relief_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0011001101", drawable.image_elevation_relief_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0011001111", drawable.image_elevation_relief_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("0011000100", drawable.image_elevation_relief_bathcontour);
        resourceMap.put("0011000110", drawable.image_elevation_relief_bathcontour_metallic);
        resourceMap.put("0011000101", drawable.image_elevation_relief_bathcontour_nonmetallic);
        resourceMap.put("0011000111", drawable.image_elevation_relief_bathcontour_metallic_nonmetallic);

        resourceMap.put("0010100000", drawable.image_elevation_contours);
        resourceMap.put("0010100010", drawable.image_elevation_contours_metallic);
        resourceMap.put("0010100001", drawable.image_elevation_contours_nonmetallic);
        resourceMap.put("0010100011", drawable.image_elevation_contours_metallic_nonmetallic);
        resourceMap.put("0010110000", drawable.image_elevation_contours_bathymetry);
        resourceMap.put("0010110010", drawable.image_elevation_contours_bathymetry_metallic);
        resourceMap.put("0010110001", drawable.image_elevation_contours_bathymetry_nonmetallic);
        resourceMap.put("0010110011", drawable.image_elevation_contours_bathymetry_metallic_nonmetallic);
        resourceMap.put("0010111000", drawable.image_elevation_contours_bathymetry_underwaterrelief);
        resourceMap.put("0010111010", drawable.image_elevation_contours_bathymetry_underwaterrelief_metallic);
        resourceMap.put("0010111001", drawable.image_elevation_contours_bathymetry_underwaterrelief_nonmetallic);
        resourceMap.put("0010111011", drawable.image_elevation_contours_bathymetry_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("0010111100", drawable.image_elevation_contours_bathymetry_underwaterrelief_bathcontour);
        resourceMap.put("0010111110", drawable.image_elevation_contours_bathymetry_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0010111101", drawable.image_elevation_contours_bathymetry_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0010111111", drawable.image_elevation_contours_bathymetry_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("0010110100", drawable.image_elevation_contours_bathymetry_bathcontour);
        resourceMap.put("0010110110", drawable.image_elevation_contours_bathymetry_bathcontour_metallic);
        resourceMap.put("0010110101", drawable.image_elevation_contours_bathymetry_bathcontour_nonmetallic);
        resourceMap.put("0010110111", drawable.image_elevation_contours_bathymetry_bathcontour_metallic_nonmetallic);
        resourceMap.put("0010101000", drawable.image_elevation_contours_underwaterrelief);
        resourceMap.put("0010101010", drawable.image_elevation_contours_underwaterrelief_metallic);
        resourceMap.put("0010101001", drawable.image_elevation_contours_underwaterrelief_nonmetallic);
        resourceMap.put("0010101011", drawable.image_elevation_contours_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("0010101100", drawable.image_elevation_contours_underwaterrelief_bathcontour);
        resourceMap.put("0010101110", drawable.image_elevation_contours_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0010101101", drawable.image_elevation_contours_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0010101111", drawable.image_elevation_contours_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("0010100100", drawable.image_elevation_contours_bathcontour);
        resourceMap.put("0010100110", drawable.image_elevation_contours_bathcontour_metallic);
        resourceMap.put("0010100101", drawable.image_elevation_contours_bathcontour_nonmetallic);
        resourceMap.put("0010100111", drawable.image_elevation_contours_bathcontour_metallic_nonmetallic);
        resourceMap.put("0010010000", drawable.image_elevation_bathymetry);
        resourceMap.put("0010010010", drawable.image_elevation_bathymetry_metallic);
        resourceMap.put("0010010001", drawable.image_elevation_bathymetry_nonmetallic);
        resourceMap.put("0010010011", drawable.image_elevation_bathymetry_metallic_nonmetallic);
        resourceMap.put("0010011000", drawable.image_elevation_bathymetry_underwaterrelief);
        resourceMap.put("0010011010", drawable.image_elevation_bathymetry_underwaterrelief_metallic);
        resourceMap.put("0010011001", drawable.image_elevation_bathymetry_underwaterrelief_nonmetallic);
        resourceMap.put("0010011011", drawable.image_elevation_bathymetry_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("0010011100", drawable.image_elevation_bathymetry_underwaterrelief_bathcontour);
        resourceMap.put("0010011110", drawable.image_elevation_bathymetry_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0010011101", drawable.image_elevation_bathymetry_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0010011111", drawable.image_elevation_bathymetry_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("0010010100", drawable.image_elevation_bathymetry_bathcontour);
        resourceMap.put("0010010110", drawable.image_elevation_bathymetry_bathcontour_metallic);
        resourceMap.put("0010010101", drawable.image_elevation_bathymetry_bathcontour_nonmetallic);
        resourceMap.put("0010010111", drawable.image_elevation_bathymetry_bathcontour_metallic_nonmetallic);

        resourceMap.put("0010001000", drawable.image_elevation_underwaterrelief);
        resourceMap.put("0010001010", drawable.image_elevation_underwaterrelief_metallic);
        resourceMap.put("0010001001", drawable.image_elevation_underwaterrelief_nonmetallic);
        resourceMap.put("0010001011", drawable.image_elevation_underwaterrelief_metallic_nonmetallic);

        resourceMap.put("0010001100", drawable.image_elevation_underwaterrelief_bathcontour);
        resourceMap.put("0010001110", drawable.image_elevation_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0010001101", drawable.image_elevation_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0010001111", drawable.image_elevation_underwaterrelief_bathcontour_metallic_nonmetallic);

        resourceMap.put("0010000100", drawable.image_elevation_bathcontour);
        resourceMap.put("0010000110", drawable.image_elevation_bathcontour_metallic);
        resourceMap.put("0010000101", drawable.image_elevation_bathcontour_nonmetallic);
        resourceMap.put("0010000111", drawable.image_elevation_bathcontour_metallic_nonmetallic);

        resourceMap.put("0001100000", drawable.image_relief_contours);
        resourceMap.put("0001100010", drawable.image_relief_contours_metallic);
        resourceMap.put("0001100001", drawable.image_relief_contours_nonmetallic);
        resourceMap.put("0001100011", drawable.image_relief_contours_metallic_nonmetallic);
        resourceMap.put("0001110000", drawable.image_relief_contours_bathymetry);
        resourceMap.put("0001110010", drawable.image_relief_contours_bathymetry_metallic);
        resourceMap.put("0001110001", drawable.image_relief_contours_bathymetry_nonmetallic);
        resourceMap.put("0001110011", drawable.image_relief_contours_bathymetry_metallic_nonmetallic);
        resourceMap.put("0001111000", drawable.image_relief_contours_bathymetry_underwaterrelief);
        resourceMap.put("0001111010", drawable.image_relief_contours_bathymetry_underwaterrelief_metallic);
        resourceMap.put("0001111001", drawable.image_relief_contours_bathymetry_underwaterrelief_nonmetallic);
        resourceMap.put("0001111011", drawable.image_relief_contours_bathymetry_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("0001111100", drawable.image_relief_contours_bathymetry_underwaterrelief_bathcontour);
        resourceMap.put("0001111110", drawable.image_relief_contours_bathymetry_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0001111101", drawable.image_relief_contours_bathymetry_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0001111111", drawable.image_relief_contours_bathymetry_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("0001110100", drawable.image_relief_contours_bathymetry_bathcontour);
        resourceMap.put("0001110110", drawable.image_relief_contours_bathymetry_bathcontour_metallic);
        resourceMap.put("0001110101", drawable.image_relief_contours_bathymetry_bathcontour_nonmetallic);
        resourceMap.put("0001110111", drawable.image_relief_contours_bathymetry_bathcontour_metallic_nonmetallic);
        resourceMap.put("0001101000", drawable.image_relief_contours_underwaterrelief);
        resourceMap.put("0001101010", drawable.image_relief_contours_underwaterrelief_metallic);
        resourceMap.put("0001101001", drawable.image_relief_contours_underwaterrelief_nonmetallic);
        resourceMap.put("0001101011", drawable.image_relief_contours_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("0001101100", drawable.image_relief_contours_underwaterrelief_bathcontour);
        resourceMap.put("0001101110", drawable.image_relief_contours_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0001101101", drawable.image_relief_contours_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0001101111", drawable.image_relief_contours_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("0001100100", drawable.image_relief_contours_bathcontour);
        resourceMap.put("0001100110", drawable.image_relief_contours_bathcontour_metallic);
        resourceMap.put("0001100101", drawable.image_relief_contours_bathcontour_nonmetallic);
        resourceMap.put("0001100111", drawable.image_relief_contours_bathcontour_metallic_nonmetallic);

        resourceMap.put("0000110000", drawable.image_contours_bathymetry);
        resourceMap.put("0000110010", drawable.image_contours_bathymetry_metallic);
        resourceMap.put("0000110001", drawable.image_contours_bathymetry_nonmetallic);
        resourceMap.put("0000110011", drawable.image_contours_bathymetry_metallic_nonmetallic);
        resourceMap.put("0000111000", drawable.image_contours_bathymetry_underwaterrelief);
        resourceMap.put("0000111010", drawable.image_contours_bathymetry_underwaterrelief_metallic);
        resourceMap.put("0000111001", drawable.image_contours_bathymetry_underwaterrelief_nonmetallic);
        resourceMap.put("0000111011", drawable.image_contours_bathymetry_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("0000111100", drawable.image_contours_bathymetry_underwaterrelief_bathcontour);
        resourceMap.put("0000111110", drawable.image_contours_bathymetry_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0000111101", drawable.image_contours_bathymetry_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0000111111", drawable.image_contours_bathymetry_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("0000110100", drawable.image_contours_bathymetry_bathcontour);
        resourceMap.put("0000110110", drawable.image_contours_bathymetry_bathcontour_metallic);
        resourceMap.put("0000110101", drawable.image_contours_bathymetry_bathcontour_nonmetallic);
        resourceMap.put("0000110111", drawable.image_contours_bathymetry_bathcontour_metallic_nonmetallic);
        resourceMap.put("0000101000", drawable.image_contours_underwaterrelief);
        resourceMap.put("0000101010", drawable.image_contours_underwaterrelief_metallic);
        resourceMap.put("0000101001", drawable.image_contours_underwaterrelief_nonmetallic);
        resourceMap.put("0000101011", drawable.image_contours_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("0000101100", drawable.image_contours_underwaterrelief_bathcontour);
        resourceMap.put("0000101110", drawable.image_contours_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0000101101", drawable.image_contours_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0000101111", drawable.image_contours_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("0000100100", drawable.image_contours_bathcontour);
        resourceMap.put("0000100110", drawable.image_contours_bathcontour_metallic);
        resourceMap.put("0000100101", drawable.image_contours_bathcontour_nonmetallic);
        resourceMap.put("0000100111", drawable.image_contours_bathcontour_metallic_nonmetallic);

        resourceMap.put("1101000000", drawable.image_lithology_faults_relief);
        resourceMap.put("1101000010", drawable.image_lithology_faults_relief_metallic);
        resourceMap.put("1101000001", drawable.image_lithology_faults_relief_nonmetallic);
        resourceMap.put("1101000011", drawable.image_lithology_faults_relief_metallic_nonmetallic);
        resourceMap.put("1101010000", drawable.image_lithology_faults_relief_bathymetry);
        resourceMap.put("1101010010", drawable.image_lithology_faults_relief_bathymetry_metallic);
        resourceMap.put("1101010001", drawable.image_lithology_faults_relief_bathymetry_nonmetallic);
        resourceMap.put("1101010011", drawable.image_lithology_faults_relief_bathymetry_metallic_nonmetallic);
        resourceMap.put("1101011000", drawable.image_lithology_faults_relief_bathymetry_underwaterrelief);
        resourceMap.put("1101011010", drawable.image_lithology_faults_relief_bathymetry_underwaterrelief_metallic);
        resourceMap.put("1101011001", drawable.image_lithology_faults_relief_bathymetry_underwaterrelief_nonmetallic);
        resourceMap.put("1101011011", drawable.image_lithology_faults_relief_bathymetry_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("1101011100", drawable.image_lithology_faults_relief_bathymetry_underwaterrelief_bathcontour);
        resourceMap.put("1101011110", drawable.image_lithology_faults_relief_bathymetry_underwaterrelief_bathcontour_metallic);
        resourceMap.put("1101011101", drawable.image_lithology_faults_relief_bathymetry_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("1101011111", drawable.image_lithology_faults_relief_bathymetry_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("1101010100", drawable.image_lithology_faults_relief_bathymetry_bathcontour);
        resourceMap.put("1101010110", drawable.image_lithology_faults_relief_bathymetry_bathcontour_metallic);
        resourceMap.put("1101010101", drawable.image_lithology_faults_relief_bathymetry_bathcontour_nonmetallic);
        resourceMap.put("1101010111", drawable.image_lithology_faults_relief_bathymetry_bathcontour_metallic_nonmetallic);
        resourceMap.put("1101001000", drawable.image_lithology_faults_relief_underwaterrelief);
        resourceMap.put("1101001010", drawable.image_lithology_faults_relief_underwaterrelief_metallic);
        resourceMap.put("1101001001", drawable.image_lithology_faults_relief_underwaterrelief_nonmetallic);
        resourceMap.put("1101001011", drawable.image_lithology_faults_relief_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("1101001100", drawable.image_lithology_faults_relief_underwaterrelief_bathcontour);
        resourceMap.put("1101001110", drawable.image_lithology_faults_relief_underwaterrelief_bathcontour_metallic);
        resourceMap.put("1101001101", drawable.image_lithology_faults_relief_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("1101001111", drawable.image_lithology_faults_relief_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("1101000100", drawable.image_lithology_faults_relief_bathcontour);
        resourceMap.put("1101000110", drawable.image_lithology_faults_relief_bathcontour_metallic);
        resourceMap.put("1101000101", drawable.image_lithology_faults_relief_bathcontour_nonmetallic);
        resourceMap.put("1101000111", drawable.image_lithology_faults_relief_bathcontour_metallic_nonmetallic);

        resourceMap.put("1100100000", drawable.image_lithology_faults_contours);
        resourceMap.put("1100100010", drawable.image_lithology_faults_contours_metallic);
        resourceMap.put("1100100001", drawable.image_lithology_faults_contours_nonmetallic);
        resourceMap.put("1100100011", drawable.image_lithology_faults_contours_metallic_nonmetallic);
        resourceMap.put("1100110000", drawable.image_lithology_faults_contours_bathymetry);
        resourceMap.put("1100110010", drawable.image_lithology_faults_contours_bathymetry_metallic);
        resourceMap.put("1100110001", drawable.image_lithology_faults_contours_bathymetry_nonmetallic);
        resourceMap.put("1100110011", drawable.image_lithology_faults_contours_bathymetry_metallic_nonmetallic);
        resourceMap.put("1100111000", drawable.image_lithology_faults_contours_bathymetry_underwaterrelief);
        resourceMap.put("1100111010", drawable.image_lithology_faults_contours_bathymetry_underwaterrelief_metallic);
        resourceMap.put("1100111001", drawable.image_lithology_faults_contours_bathymetry_underwaterrelief_nonmetallic);
        resourceMap.put("1100111011", drawable.image_lithology_faults_contours_bathymetry_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("1100111100", drawable.image_lithology_faults_contours_bathymetry_underwaterrelief_bathcontour);
        resourceMap.put("1100111110", drawable.image_lithology_faults_contours_bathymetry_underwaterrelief_bathcontour_metallic);
        resourceMap.put("1100111101", drawable.image_lithology_faults_contours_bathymetry_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("1100111111", drawable.image_lithology_faults_contours_bathymetry_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("1100110100", drawable.image_lithology_faults_contours_bathymetry_bathcontour);
        resourceMap.put("1100110110", drawable.image_lithology_faults_contours_bathymetry_bathcontour_metallic);
        resourceMap.put("1100110101", drawable.image_lithology_faults_contours_bathymetry_bathcontour_nonmetallic);
        resourceMap.put("1100110111", drawable.image_lithology_faults_contours_bathymetry_bathcontour_metallic_nonmetallic);
        resourceMap.put("1100101000", drawable.image_lithology_faults_contours_underwaterrelief);
        resourceMap.put("1100101010", drawable.image_lithology_faults_contours_underwaterrelief_metallic);
        resourceMap.put("1100101001", drawable.image_lithology_faults_contours_underwaterrelief_nonmetallic);
        resourceMap.put("1100101011", drawable.image_lithology_faults_contours_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("1100101100", drawable.image_lithology_faults_contours_underwaterrelief_bathcontour);
        resourceMap.put("1100101110", drawable.image_lithology_faults_contours_underwaterrelief_bathcontour_metallic);
        resourceMap.put("1100101101", drawable.image_lithology_faults_contours_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("1100101111", drawable.image_lithology_faults_contours_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("1100100100", drawable.image_lithology_faults_contours_bathcontour);
        resourceMap.put("1100100110", drawable.image_lithology_faults_contours_bathcontour_metallic);
        resourceMap.put("1100100101", drawable.image_lithology_faults_contours_bathcontour_nonmetallic);
        resourceMap.put("1100100111", drawable.image_lithology_faults_contours_bathcontour_metallic_nonmetallic);

        resourceMap.put("1100010000", drawable.image_lithology_faults_bathymetry);
        resourceMap.put("1100010010", drawable.image_lithology_faults_bathymetry_metallic);
        resourceMap.put("1100010001", drawable.image_lithology_faults_bathymetry_nonmetallic);
        resourceMap.put("1100010011", drawable.image_lithology_faults_bathymetry_metallic_nonmetallic);
        resourceMap.put("1100011000", drawable.image_lithology_faults_bathymetry_underwaterrelief);
        resourceMap.put("1100011010", drawable.image_lithology_faults_bathymetry_underwaterrelief_metallic);
        resourceMap.put("1100011001", drawable.image_lithology_faults_bathymetry_underwaterrelief_nonmetallic);
        resourceMap.put("1100011011", drawable.image_lithology_faults_bathymetry_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("1100011100", drawable.image_lithology_faults_bathymetry_underwaterrelief_bathcontour);
        resourceMap.put("1100011110", drawable.image_lithology_faults_bathymetry_underwaterrelief_bathcontour_metallic);
        resourceMap.put("1100011101", drawable.image_lithology_faults_bathymetry_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("1100011111", drawable.image_lithology_faults_bathymetry_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("1100010100", drawable.image_lithology_faults_bathymetry_bathcontour);
        resourceMap.put("1100010110", drawable.image_lithology_faults_bathymetry_bathcontour_metallic);
        resourceMap.put("1100010101", drawable.image_lithology_faults_bathymetry_bathcontour_nonmetallic);
        resourceMap.put("1100010111", drawable.image_lithology_faults_bathymetry_bathcontour_metallic_nonmetallic);
        resourceMap.put("1100001000", drawable.image_lithology_faults_underwaterrelief);
        resourceMap.put("1100001010", drawable.image_lithology_faults_underwaterrelief_metallic);
        resourceMap.put("1100001001", drawable.image_lithology_faults_underwaterrelief_nonmetallic);
        resourceMap.put("1100001011", drawable.image_lithology_faults_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("1100001100", drawable.image_lithology_faults_underwaterrelief_bathcontour);
        resourceMap.put("1100001110", drawable.image_lithology_faults_underwaterrelief_bathcontour_metallic);
        resourceMap.put("1100001101", drawable.image_lithology_faults_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("1100001111", drawable.image_lithology_faults_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("1100000100", drawable.image_lithology_faults_bathcontour);
        resourceMap.put("1100000110", drawable.image_lithology_faults_bathcontour_metallic);
        resourceMap.put("1100000101", drawable.image_lithology_faults_bathcontour_nonmetallic);
        resourceMap.put("1100000111", drawable.image_lithology_faults_bathcontour_metallic_nonmetallic);

        resourceMap.put("0101100000", drawable.image_faults_relief_contours);
        resourceMap.put("0101100010", drawable.image_faults_relief_contours_metallic);
        resourceMap.put("0101100001", drawable.image_faults_relief_contours_nonmetallic);
        resourceMap.put("0101100011", drawable.image_faults_relief_contours_metallic_nonmetallic);
        resourceMap.put("0101110000", drawable.image_faults_relief_contours_bathymetry);
        resourceMap.put("0101110010", drawable.image_faults_relief_contours_bathymetry_metallic);
        resourceMap.put("0101110001", drawable.image_faults_relief_contours_bathymetry_nonmetallic);
        resourceMap.put("0101110011", drawable.image_faults_relief_contours_bathymetry_metallic_nonmetallic);
        resourceMap.put("0101111000", drawable.image_faults_relief_contours_bathymetry_underwaterrelief);
        resourceMap.put("0101111010", drawable.image_faults_relief_contours_bathymetry_underwaterrelief_metallic);
        resourceMap.put("0101111001", drawable.image_faults_relief_contours_bathymetry_underwaterrelief_nonmetallic);
        resourceMap.put("0101111011", drawable.image_faults_relief_contours_bathymetry_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("0101110100", drawable.image_faults_relief_contours_bathymetry_bathcontour);
        resourceMap.put("0101110110", drawable.image_faults_relief_contours_bathymetry_bathcontour_metallic);
        resourceMap.put("0101110101", drawable.image_faults_relief_contours_bathymetry_bathcontour_nonmetallic);
        resourceMap.put("0101110111", drawable.image_faults_relief_contours_bathymetry_bathcontour_metallic_nonmetallic);
        resourceMap.put("0101101000", drawable.image_faults_relief_contours_underwaterrelief);
        resourceMap.put("0101101010", drawable.image_faults_relief_contours_underwaterrelief_metallic);
        resourceMap.put("0101101001", drawable.image_faults_relief_contours_underwaterrelief_nonmetallic);
        resourceMap.put("0101101011", drawable.image_faults_relief_contours_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("0101101100", drawable.image_faults_relief_contours_underwaterrelief_bathcontour);
        resourceMap.put("0101101110", drawable.image_faults_relief_contours_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0101101101", drawable.image_faults_relief_contours_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0101111111", drawable.image_faults_relief_contours_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("0101100100", drawable.image_faults_relief_contours_bathcontour);
        resourceMap.put("0101100110", drawable.image_faults_relief_contours_bathcontour_metallic);
        resourceMap.put("0101100101", drawable.image_faults_relief_contours_bathcontour_nonmetallic);
        resourceMap.put("0101100111", drawable.image_faults_relief_contours_bathcontour_metallic_nonmetallic);

        resourceMap.put("0011100000", drawable.image_elevation_relief_contours);
        resourceMap.put("0011100010", drawable.image_elevation_relief_contours_metallic);
        resourceMap.put("0011100001", drawable.image_elevation_relief_contours_nonmetallic);
        resourceMap.put("0011100011", drawable.image_elevation_relief_contours_metallic_nonmetallic);
        resourceMap.put("0011110000", drawable.image_elevation_relief_contours_bathymetry);
        resourceMap.put("0011110010", drawable.image_elevation_relief_contours_bathymetry_metallic);
        resourceMap.put("0011110001", drawable.image_elevation_relief_contours_bathymetry_nonmetallic);
        resourceMap.put("0011110011", drawable.image_elevation_relief_contours_bathymetry_metallic_nonmetallic);
        resourceMap.put("0011111000", drawable.image_elevation_relief_contours_bathymetry_underwaterrelief);
        resourceMap.put("0011111010", drawable.image_elevation_relief_contours_bathymetry_underwaterrelief_metallic);
        resourceMap.put("0011111001", drawable.image_elevation_relief_contours_bathymetry_underwaterrelief_nonmetallic);
        resourceMap.put("0011111011", drawable.image_elevation_relief_contours_bathymetry_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("0011111100", drawable.image_elevation_relief_contours_bathymetry_underwaterrelief_bathcontour);
        resourceMap.put("0011111110", drawable.image_elevation_relief_contours_bathymetry_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0011111101", drawable.image_elevation_relief_contours_bathymetry_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0011111111", drawable.image_elevation_relief_contours_bathymetry_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("0011110100", drawable.image_elevation_relief_contours_bathymetry_bathcontour);
        resourceMap.put("0011110110", drawable.image_elevation_relief_contours_bathymetry_bathcontour_metallic);
        resourceMap.put("0011110101", drawable.image_elevation_relief_contours_bathymetry_bathcontour_nonmetallic);
        resourceMap.put("0011110111", drawable.image_elevation_relief_contours_bathymetry_bathcontour_metallic_nonmetallic);
        resourceMap.put("0011101000", drawable.image_elevation_relief_contours_underwaterrelief);
        resourceMap.put("0011101010", drawable.image_elevation_relief_contours_underwaterrelief_metallic);
        resourceMap.put("0011101001", drawable.image_elevation_relief_contours_underwaterrelief_nonmetallic);
        resourceMap.put("0011101011", drawable.image_elevation_relief_contours_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("0011101100", drawable.image_elevation_relief_contours_underwaterrelief_bathcontour);
        resourceMap.put("0011101110", drawable.image_elevation_relief_contours_underwaterrelief_bathcontour_metallic);
        resourceMap.put("0011101101", drawable.image_elevation_relief_contours_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("0011101111", drawable.image_elevation_relief_contours_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("0011100100", drawable.image_elevation_relief_contours_bathcontour);
        resourceMap.put("0011100110", drawable.image_elevation_relief_contours_bathcontour_metallic);
        resourceMap.put("0011100101", drawable.image_elevation_relief_contours_bathcontour_nonmetallic);
        resourceMap.put("0011100111", drawable.image_elevation_relief_contours_bathcontour_metallic_nonmetallic);

        resourceMap.put("1101100000", drawable.image_lithology_faults_relief_contours);
        resourceMap.put("1101100010", drawable.image_lithology_faults_relief_contours_metallic);
        resourceMap.put("1101100001", drawable.image_lithology_faults_relief_contours_nonmetallic);
        resourceMap.put("1101100011", drawable.image_lithology_faults_relief_contours_metallic_nonmetallic);
        resourceMap.put("1101110000", drawable.image_lithology_faults_relief_contours_bathymetry);
        resourceMap.put("1101110010", drawable.image_lithology_faults_relief_contours_bathymetry_metallic);
        resourceMap.put("1101110001", drawable.image_lithology_faults_relief_contours_bathymetry_nonmetallic);
        resourceMap.put("1101110011", drawable.image_lithology_faults_relief_contours_bathymetry_metallic_nonmetallic);
        resourceMap.put("1101111000", drawable.image_lithology_faults_relief_contours_bathymetry_underwaterrelief);
        resourceMap.put("1101111010", drawable.image_lithology_faults_relief_contours_bathymetry_underwaterrelief_metallic);
        resourceMap.put("1101111001", drawable.image_lithology_faults_relief_contours_bathymetry_underwaterrelief_nonmetallic);
        resourceMap.put("1101111011", drawable.image_lithology_faults_relief_contours_bathymetry_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("1101111100", drawable.image_lithology_faults_relief_contours_bathymetry_underwaterrelief_bathcontour);
        resourceMap.put("1101111110", drawable.image_lithology_faults_relief_contours_bathymetry_underwaterrelief_bathcontour_metallic);
        resourceMap.put("1101111101", drawable.image_lithology_faults_relief_contours_bathymetry_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("1101111111", drawable.image_lithology_faults_relief_contours_bathymetry_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("1101101100", drawable.image_lithology_faults_relief_contours_underwaterrelief_bathcontour);
        resourceMap.put("1101101110", drawable.image_lithology_faults_relief_contours_underwaterrelief_bathcontour_metallic);
        resourceMap.put("1101101101", drawable.image_lithology_faults_relief_contours_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("1101101111", drawable.image_lithology_faults_relief_contours_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("1101110100", drawable.image_lithology_faults_relief_contours_bathymetry_bathcontour);
        resourceMap.put("1101110110", drawable.image_lithology_faults_relief_contours_bathymetry_bathcontour_metallic);
        resourceMap.put("1101110101", drawable.image_lithology_faults_relief_contours_bathymetry_bathcontour_nonmetallic);
        resourceMap.put("1101110111", drawable.image_lithology_faults_relief_contours_bathymetry_bathcontour_metallic_nonmetallic);

        resourceMap.put("1001110000", drawable.image_lithology_relief_contours_bathymetry);
        resourceMap.put("1001110010", drawable.image_lithology_relief_contours_bathymetry_metallic);
        resourceMap.put("1001110001", drawable.image_lithology_relief_contours_bathymetry_nonmetallic);
        resourceMap.put("1001110011", drawable.image_lithology_relief_contours_bathymetry_metallic_nonmetallic);
        resourceMap.put("1001111000", drawable.image_lithology_relief_contours_bathymetry_underwaterrelief);
        resourceMap.put("1001111010", drawable.image_lithology_relief_contours_bathymetry_underwaterrelief_metallic);
        resourceMap.put("1001111001", drawable.image_lithology_relief_contours_bathymetry_underwaterrelief_nonmetallic);
        resourceMap.put("1001111011", drawable.image_lithology_relief_contours_bathymetry_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("1001111100", drawable.image_lithology_relief_contours_bathymetry_underwaterrelief_bathcontour);
        resourceMap.put("1001111110", drawable.image_lithology_relief_contours_bathymetry_underwaterrelief_bathcontour_metallic);
        resourceMap.put("1001111101", drawable.image_lithology_relief_contours_bathymetry_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("1001111111", drawable.image_lithology_relief_contours_bathymetry_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("1001110100", drawable.image_lithology_relief_contours_bathymetry_bathymetry_bathcontour);
        resourceMap.put("1001110110", drawable.image_lithology_relief_contours_bathymetry_bathymetry_bathcontour_metallic);
        resourceMap.put("1001110101", drawable.image_lithology_relief_contours_bathymetry_bathymetry_bathcontour_nonmetallic);
        resourceMap.put("1001110111", drawable.image_lithology_relief_contours_bathymetry_bathymetry_bathcontour_metallic_nonmetallic);
        resourceMap.put("1001101100", drawable.image_lithology_relief_contours_underwaterrelief_bathcontour);
        resourceMap.put("1001101110", drawable.image_lithology_relief_contours_underwaterrelief_bathcontour_metallic);
        resourceMap.put("1001101101", drawable.image_lithology_relief_contours_underwaterrelief_bathcontour_nonmetallic);
        resourceMap.put("1001101111", drawable.image_lithology_relief_contours_underwaterrelief_bathcontour_metallic_nonmetallic);
        resourceMap.put("1001101000", drawable.image_lithology_relief_contours_underwaterrelief);
        resourceMap.put("1001101010", drawable.image_lithology_relief_contours_underwaterrelief_metallic);
        resourceMap.put("1001101001", drawable.image_lithology_relief_contours_underwaterrelief_nonmetallic);
        resourceMap.put("1001101011", drawable.image_lithology_relief_contours_underwaterrelief_metallic_nonmetallic);
        resourceMap.put("1001100100", drawable.image_lithology_relief_contours_bathcontour);
        resourceMap.put("1001100110", drawable.image_lithology_relief_contours_bathcontour_metallic);
        resourceMap.put("1001100101", drawable.image_lithology_relief_contours_bathcontour_nonmetallic);
        resourceMap.put("1001100111", drawable.image_lithology_relief_contours_bathcontour_metallic_nonmetallic);
        resourceMap.put("1001100010", drawable.image_lithology_relief_contours_metallic);
        resourceMap.put("1001100001", drawable.image_lithology_relief_contours_nonmetallic);
        resourceMap.put("1001100011", drawable.image_lithology_relief_contours_metallic_nonmetallic);


        String key = (isLithologyChecked ? "1" : "0") +
                (isFaultsChecked ? "1" : "0") +
                (isElevationChecked ? "1" : "0") +
                (isReliefChecked ? "1" : "0") +
                (isContoursChecked ? "1" : "0") +
                (isBathymetryChecked ? "1" : "0") +
                (isUnderwaterReliefChecked ? "1" : "0") +
                (isBathContoursChecked ? "1" : "0")+
                (isMetallicChecked ? "1" : "0")+
                (isNonMetallicChecked ? "1" : "0");

        int resourceId = resourceMap.getOrDefault(key, drawable.image_nolayer);
        // Save checkbox states to SharedPreferences
        SharedPreferences preferences = context.getSharedPreferences("checkbox_states", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("Lithology", isLithologyChecked);
        editor.putBoolean("Faults", isFaultsChecked);
        editor.putBoolean("Elevation", isElevationChecked);
        editor.putBoolean("Relief", isReliefChecked);
        editor.putBoolean("Contours", isContoursChecked);
        editor.putBoolean("Bathymetry", isBathymetryChecked);
        editor.putBoolean("Underwater relief", isUnderwaterReliefChecked);
        editor.putBoolean("Bathymetry contours", isBathContoursChecked);
        editor.putBoolean("Metallic", isMetallicChecked);
        editor.putBoolean("Non metallic", isNonMetallicChecked);
        editor.apply();
        imageView.setImageResource(resourceId);

    }
}