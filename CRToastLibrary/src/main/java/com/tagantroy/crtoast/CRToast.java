package com.tagantroy.crtoast;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CRToast {

    public interface ICRToast{
         boolean onTapped();
    }

    public static final float STATUS_BAR_MARGIN = 0.05F;
    Thread timer;

    private AnimationStyle animationStyle;
    private int duration;
    private int backgroundColor;
    private int height;
    private Drawable image;
    private boolean isDismissibleWithTap;
    private boolean isImage;
    private boolean isStatusBarVisible;
    private boolean isInsideActionBar;
    private String notificationMessage;
    private String subtitleText;
    private View customView;
    private ICRToast icrToast;
    private Activity activity;
    private View view;

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
        private boolean isStatusBarVisible = false;
        private String notificationMessage = "";
        private String subtitleText = "";
        private View customView=null;
        private ICRToast icrToast = null;

        private Activity activity;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder animationStyle(AnimationStyle val) {
            animationStyle = val;
            return this;
        }

        public Builder backgroundColor(int val) {
            backgroundColor = val;
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

        public Builder statusBarVisible(boolean val) {
            isStatusBarVisible = val;
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
        isStatusBarVisible = builder.isStatusBarVisible;
        isInsideActionBar = builder.isInsideActionBar;
        notificationMessage = builder.notificationMessage;
        subtitleText = builder.subtitleText;
        activity = builder.activity;
        customView=builder.customView;
        windowManager = (WindowManager) activity
                .getSystemService(Context.WINDOW_SERVICE);
        if(customView!=null){
            view = customView;
        }else{
            view = generateToast();
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
        int toastXML = activity.getResources()
                .getIdentifier("toast", "layout", activity.getPackageName());
        LinearLayout view = (LinearLayout) activity.getLayoutInflater()
                .inflate(toastXML, null);
        int messageId = activity.getResources()
                .getIdentifier("notificationMessage", "id", activity.getPackageName());
        int subtitleId = activity.getResources()
                .getIdentifier("subtitleText", "id", activity.getPackageName());
        int customImageViewId = activity.getResources()
                .getIdentifier("customImageView", "id", activity.getPackageName());
        TextView message = (TextView) view.findViewById(messageId);
        TextView subtitle = (TextView) view.findViewById(subtitleId);
        ImageView customImageView = (ImageView) view.findViewById(customImageViewId);
        view.setBackgroundColor(backgroundColor);
        subtitle.setText(subtitleText);
        message.setText(notificationMessage);
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
        return view;
    }

    private WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        int statusBarHeight = (int) Math.ceil(height * activity.getResources().getDisplayMetrics().density);

        if (!isStatusBarVisible) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            layoutParams.verticalMargin = STATUS_BAR_MARGIN;
        }

        if (isInsideActionBar) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            layoutParams.verticalMargin = STATUS_BAR_MARGIN;
            statusBarHeight = activity.getActionBar().getHeight();
        }

        layoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        layoutParams.format = PixelFormat.RGB_888;
        layoutParams.width = ActionBar.LayoutParams.MATCH_PARENT;
        layoutParams.height = statusBarHeight;
        layoutParams.gravity = Gravity.TOP;
        layoutParams.windowAnimations = animationStyle.getStyle(activity);
        return layoutParams;
    }
}