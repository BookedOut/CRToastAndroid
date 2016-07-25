package com.tagantroy.crtoast;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.Map;

public class CRToast {

    public interface ICRToast{
        boolean onTapped();
    }

    Thread timer;

    private AnimationStyle animationStyle;
    private int duration;
    private int backgroundColor;
    private int height;
    private Drawable image;
    private boolean isDismissibleWithTap;
    private boolean isImage;
    private boolean isInsideActionBar;
    private String notificationMessage;
    private int notificationMessageColor = 0;
    private int subTitleColor = 0;
    private Typeface notificationMessageFont = null;
    private int notificationMessageFontSize = -1;
    private Typeface subTitleFont = null;
    private int notificationLayoutGravity = -1;
    private int notificationMessageGravity = -1;
    private int subTitleGravity = -1;
    private int subTitleFontSize = -1;
    private String subtitleText;
    private View customView;
    private ICRToast icrToast;
    private Activity activity;
    private View view;
    private boolean isHomeButtonEnabled = false;
    private boolean isBackButtonEnabled = false;
    WindowManager windowManager;

    public static class Builder {
        private AnimationStyle animationStyle = AnimationStyle.TopToTop;
        private int duration = -1;
        private int backgroundColor = Color.RED;
        private int height = 72;
        private Drawable image = null;
        private boolean isDismissibleWithTap = false;
        private boolean isImage = false;
        private boolean isInsideActionBar = false;
        private boolean isHomeButtonEnabled = false;
        private boolean isBackButtonEnabled = false;
        private String notificationMessage = "";
        private int notificationMessageColor = 0;
        private int subTitleColor = 0;
        private Typeface notificationMessageFont = null;
        private int notificationMessageFontSize = -1;
        private Typeface subTitleFont = null;
        private int subTitleFontSize = -1;
        private int notificationLayoutGravity = -1;
        private int notificationMessageGravity = -1;
        private int subTitleGravity = -1;
        private String subtitleText = "";
        private View customView=null;
        private ICRToast icrToast = null;

        private Activity activity;

        public Builder() {
            this.activity = getRunningActivity();
        }

        public Builder(Activity activity) {
            this.activity = activity;
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        private Activity getRunningActivity() {
            try {
                Class activityThreadClass = Class.forName("android.app.ActivityThread");
                Object activityThread = activityThreadClass.getMethod("currentActivityThread")
                        .invoke(null);
                Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
                activitiesField.setAccessible(true);
                Map activities = (Map) activitiesField.get(activityThread);
                for (Object activityRecord : activities.values()) {
                    Class activityRecordClass = activityRecord.getClass();
                    Field pausedField = activityRecordClass.getDeclaredField("paused");
                    pausedField.setAccessible(true);
                    if (!pausedField.getBoolean(activityRecord)) {
                        Field activityField = activityRecordClass.getDeclaredField("activity");
                        activityField.setAccessible(true);
                        return (Activity) activityField.get(activityRecord);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            throw new RuntimeException("Didn't find the running activity");
        }

        public Builder animationStyle(AnimationStyle val) {
            animationStyle = val;
            return this;
        }

        public Builder backgroundColor(int val) {
            backgroundColor = val;
            return this;
        }

        public Builder notificationMessageColor(int val){
            notificationMessageColor = val;
            return this;
        }

        public Builder subTitleColor(int val){
            subTitleColor = val;
            return this;
        }

        public Builder notificationMessageFont(Typeface val,int fontSize){
            notificationMessageFont = val;
            notificationMessageFontSize = fontSize;
            return this;
        }

        public Builder subTitleFont(Typeface val,int fontSize){
            subTitleFont = val;
            subTitleFontSize = fontSize;
            return this;
        }

        public Builder notificationLayoutGravity(int val) {
            notificationLayoutGravity = val;
            return this;
        }

        public Builder notificationMessageGravity(int val){
            notificationMessageGravity = val;
            return this;
        }

        public Builder subTitleGravity(int val){
            subTitleGravity = val;
            return this;
        }

        public Builder homeButtonEnabled(boolean val){
            isHomeButtonEnabled = val;
            return this;
        }

        public Builder backButtonEnabled(boolean val){
            isBackButtonEnabled = val;
            return this;
        }

        public Builder customHeight(int val) {
            height = val;
            return this;
        }

        public Builder customView(View val){
            customView = val;
            return this;
        }

        public Builder dismissWithTap(boolean val) {
            isDismissibleWithTap = val;
            return this;
        }

        public Builder onTapped(ICRToast val){
            icrToast = val;
            return this;
        }

        public Builder duration(int val) {
            duration = val;
            return this;
        }

        public Builder image(Drawable val) {
            isImage = true;
            image = val;
            return this;
        }

        public Builder notificationMessage(String val) {
            notificationMessage = val;
            return this;
        }

        public Builder subtitleText(String val) {
            subtitleText = val;
            return this;
        }

        public Builder insideActionBar(boolean val) {
            isInsideActionBar = val;
            return this;
        }

        public CRToast build() {
            return new CRToast(this);
        }
    }

    private CRToast(Builder builder) {
        animationStyle = builder.animationStyle;
        backgroundColor = builder.backgroundColor;
        duration = builder.duration;
        isImage = builder.isImage;
        image = builder.image;
        height = builder.height;
        isDismissibleWithTap = builder.isDismissibleWithTap;
        icrToast =  builder.icrToast;
        isInsideActionBar = builder.isInsideActionBar;
        notificationMessage = builder.notificationMessage;
        subtitleText = builder.subtitleText;
        notificationMessageColor = builder.notificationMessageColor;
        subTitleColor = builder.subTitleColor;
        notificationMessageFont = builder.notificationMessageFont;
        notificationMessageFontSize = builder.notificationMessageFontSize;
        subTitleFont = builder.subTitleFont;
        subTitleFontSize = builder.subTitleFontSize;
        notificationLayoutGravity = builder.notificationLayoutGravity;
        notificationMessageGravity = builder.notificationMessageGravity;
        subTitleGravity = builder.subTitleGravity;
        activity = builder.activity;
        customView = builder.customView;
        isBackButtonEnabled = builder.isBackButtonEnabled;
        isHomeButtonEnabled = builder.isHomeButtonEnabled;
        windowManager = (WindowManager) activity
                .getSystemService(Context.WINDOW_SERVICE);
        if(customView != null){
            view = customView;
        } else {
            view = generateToast();
        }

        int statusBarPadding = getStatusBarHeight(activity);

        if (isStatusBarVisible()) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, statusBarPadding, 0, statusBarPadding);
            view.setLayoutParams(lp);
        }

        if (isInsideActionBar) {
            view.setPadding(0, statusBarPadding, 0, 0);
        }
    }

    void show() {
        windowManager.addView(view, getLayoutParams());
        if(duration!=-1)
            startTimer(duration);
    }

    void dismiss() {
        removeToast();
    }

    private synchronized void removeToast() {
        try {
            if (view != null) {
                windowManager.removeView(view);
            }
        }catch (IllegalArgumentException e){}
    }

    private void startTimer(final int duration) {
        final Handler handler = new Handler();
        timer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(duration);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            CRToastManager.dismiss();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        timer.start();
    }

    private LinearLayout generateToast() {
        int statusBarHeight = getStatusBarHeight(activity);

        int toastXML = activity.getResources()
                .getIdentifier("toast", "layout", activity.getPackageName());

        LinearLayout view = (LinearLayout) activity.getLayoutInflater()
                .inflate(toastXML, null);

        int notificationLayoutId = activity.getResources().getIdentifier("notificationLayoutHolder", "id", activity.getPackageName());

        int messageId = activity.getResources()
                .getIdentifier("notificationMessage", "id", activity.getPackageName());
        int subtitleId = activity.getResources()
                .getIdentifier("subtitleText", "id", activity.getPackageName());
        int customImageViewId = activity.getResources()
                .getIdentifier("customImageView", "id", activity.getPackageName());

        int marginViewId = activity.getResources()
                .getIdentifier("marginView", "id", activity.getPackageName());

        LinearLayout notificationLayoutHolder = (LinearLayout) view.findViewById(notificationLayoutId);

        LinearLayout marginLL = (LinearLayout) view.findViewById(marginViewId);
        LinearLayout.LayoutParams tempParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
        marginLL.setLayoutParams(tempParams);

        TextView message = (TextView) view.findViewById(messageId);
        TextView subtitle = (TextView) view.findViewById(subtitleId);

        ImageView customImageView = (ImageView) view.findViewById(customImageViewId);
        view.setBackgroundColor(backgroundColor);
        subtitle.setText(subtitleText);
        message.setText(notificationMessage);
        if(subtitleText== null || subtitleText.length()==0){
            message.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            subtitle.setVisibility(View.GONE);
        }
        if(icrToast!=null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(icrToast.onTapped())
                        CRToastManager.dismiss();
                }
            });
        }else{
            if (isDismissibleWithTap) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CRToastManager.dismiss();
                    }
                });
            }
        }
        if (isImage) {
            customImageView.setImageDrawable(image);
        }
        if(notificationMessageColor!=0){
            message.setTextColor(notificationMessageColor);
        }
        if(notificationMessageFont!=null){
            message.setTypeface(notificationMessageFont);
        }
        if(notificationMessageFontSize!=-1){
            message.setTextSize(notificationMessageFontSize/ activity.getResources().getDisplayMetrics().density);
        }
        if(notificationLayoutGravity != -1) {
            notificationLayoutHolder.setGravity(notificationLayoutGravity);
        }
        if(notificationMessageGravity!=-1){
            message.setGravity(notificationMessageGravity);
        }
        if(subTitleColor!=0){
            subtitle.setTextColor(subTitleColor);
        }
        if(subTitleFont!=null){
            subtitle.setTypeface(subTitleFont);
        }
        if(subTitleFontSize!=-1){
            subtitle.setTextSize(subTitleFontSize/ activity.getResources().getDisplayMetrics().density);
        }
        if(subTitleGravity!=-1){
            subtitle.setGravity(subTitleGravity);
        }
        return view;
    }

    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        if(isHomeButtonEnabled )
            layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        else
            layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        if(isBackButtonEnabled) {
            layoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        } else {
            layoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        }

        layoutParams.format = PixelFormat.RGB_888;
        layoutParams.width = ActionBar.LayoutParams.MATCH_PARENT;
        layoutParams.height = ((int) Math.ceil(height * activity.getResources().getDisplayMetrics().density)) + getStatusBarHeight(activity);
        layoutParams.gravity = Gravity.TOP;
        layoutParams.windowAnimations = animationStyle.getStyle(activity);
        return layoutParams;
    }

    private boolean isStatusBarVisible() {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result != 0;
    }
}