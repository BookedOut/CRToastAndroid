package com.tagantroy.crtoast;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
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

    private final WindowManager.LayoutParams LAYOUT_PARAMS = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.TYPE_TOAST,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.RGB_888);

    Thread timer;

    private AnimationStyle animationStyle;
    private int duration;
    private boolean isImage;
    private Drawable image;
    private boolean isDismissibleWithTap;
    private String notificationMessage;
    private String subtitleText;
    private int height;

    private Activity activity;
    private LinearLayout toast;

    WindowManager windowManager;

    public static class Builder {
        private AnimationStyle animationStyle = AnimationStyle.TopToTop;
        private String notificationMessage = "";
        private String subtitleText = "";
        private int duration = 1000;
        private boolean isImage = false;
        private Drawable image = null;
        private boolean isDismissibleWithTap = false;
        private int height = 72;

        private Activity activity;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder animationStyle(AnimationStyle val) {
            animationStyle = val;
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

        public Builder duration(int val) {
            duration = val;
            return this;
        }

        public Builder image(Drawable val) {
            isImage = true;
            image = val;
            return this;
        }

        public Builder dismissWithTap(boolean val) {
            isDismissibleWithTap = val;
            return this;
        }


        public Builder customHeight(int val) {
            height = val;
            return this;
        }

        public CRToast build() {
            return new CRToast(this);
        }
    }

    private CRToast(Builder builder) {
        animationStyle = builder.animationStyle;
        duration = builder.duration;
        isImage = builder.isImage;
        image = builder.image;
        height = builder.height;
        isDismissibleWithTap = builder.isDismissibleWithTap;
        notificationMessage = builder.notificationMessage;
        subtitleText = builder.subtitleText;
        activity = builder.activity;
    }

    public void show() {
        toast = (LinearLayout) activity.getLayoutInflater()
                .inflate(R.layout.toast, null);

        int statusBarHeight = (int) Math.ceil(height * activity.getResources().getDisplayMetrics().density);

        windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);

        LAYOUT_PARAMS.width = ActionBar.LayoutParams.MATCH_PARENT;
        LAYOUT_PARAMS.height = statusBarHeight;
        LAYOUT_PARAMS.gravity = Gravity.TOP;
        LAYOUT_PARAMS.windowAnimations = animationStyle.getStyle(activity);

        if(isDismissibleWithTap){
            toast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeToast();
                }
            });
        }

        TextView message = (TextView) toast.findViewById(R.id.notificationMessage);
        message.setText(notificationMessage);
        TextView subtitle = (TextView) toast.findViewById(R.id.subtitleText);
        subtitle.setText(subtitleText);

        if(isImage){
            ImageView customImageView = (ImageView)toast.findViewById(R.id.customImageView);
            customImageView.setImageDrawable(image);
        }

        windowManager.addView(toast,LAYOUT_PARAMS);
        startTimer(duration);
    }

    public void dismiss(){
        removeToast();
    }

    private synchronized void removeToast() {
        if(toast != null){
            windowManager.removeView(toast);
            toast=null;
        }
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
                            removeToast();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        timer.start();
    }
}
