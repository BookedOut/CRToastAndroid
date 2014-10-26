package com.tagantroy.crtoast;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.tagantroy.crtoast.enums.AnimationFrom;
import com.tagantroy.crtoast.enums.AnimationTo;
import com.tagantroy.crtoast.enums.EntranceAnimation;
import com.tagantroy.crtoast.enums.ExitAnimation;
import com.tagantroy.crtoast.enums.NotificationAlign;
import com.tagantroy.crtoast.enums.SubtitleAlign;

public class CRToast {

    private AnimationFrom animationFrom;
    private AnimationTo animationTo;
    private EntranceAnimation entranceAnimation;
    private ExitAnimation exitAnimation;
    private NotificationAlign notificationAlign;
    private SubtitleAlign subtitleAlign;

    private double duration;
    private boolean isImage;
    private Drawable image;
    private boolean isCoverActionBar;
    private boolean isSlideOverStatusAndActionBar;
    private boolean isStatusBarVisible;
    private boolean isDismissibleWithTap;
    private boolean showStatusBar;
    private boolean showActionBar;
    private String notificationMessage;
    private String subtitleText;
    private int height;

    private Activity activity;

    public static class Builder {
        private AnimationFrom animationFrom = AnimationFrom.TOP;
        private AnimationTo animationTo = AnimationTo.TOP;
        private EntranceAnimation entranceAnimation = EntranceAnimation.LINEAR;
        private ExitAnimation exitAnimation = ExitAnimation.LINEAR;
        private NotificationAlign notificationAlign = NotificationAlign.LEFT;
        private SubtitleAlign subtitleAlign = SubtitleAlign.LEFT;
        private String notificationMessage = "";
        private String subtitleText = "";

        private double duration = 1;
        private boolean isImage = false;
        private Drawable image = null;
        private boolean isCoverActionBar = true;
        private boolean isSlideOverStatusAndActionBar = true;
        private boolean isStatusBarVisible = false;
        private boolean isDismissibleWithTap = false;
        private boolean showStatusBar = true;
        private boolean showActionBar = true;
        private int height = 40;

        private Activity activity;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder animationFrom(AnimationFrom val) {
            animationFrom = val;
            return this;
        }

        public Builder animationTo(AnimationTo val) {
            animationTo = val;
            return this;
        }

        public Builder entranceAnimation(EntranceAnimation val) {
            entranceAnimation = val;
            return this;
        }

        public Builder exitAnimation(ExitAnimation val) {
            exitAnimation = val;
            return this;
        }

        public Builder notificationAlign(NotificationAlign val) {
            notificationAlign = val;
            return this;
        }

        public Builder subtitleAlign(SubtitleAlign val) {
            subtitleAlign = val;
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

        public Builder duration(double val) {
            duration = val;
            return this;
        }

        public Builder image(Drawable val) {
            isImage = true;
            image = val;
            return this;
        }

        public Builder coverActionBar(boolean val) {
            isCoverActionBar = val;
            return this;
        }

        public Builder slideOverStatusAndActionBar(boolean val) {
            isSlideOverStatusAndActionBar = val;
            return this;
        }

        public Builder statusBarVisible(boolean val) {
            isStatusBarVisible = val;
            return this;
        }

        public Builder dismissWithTap(boolean val) {
            isDismissibleWithTap = val;
            return this;
        }

        public Builder showStatusBar(boolean val) {
            showStatusBar = val;
            return this;
        }

        public Builder showActionBar(boolean val) {
            showActionBar = val;
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
        animationFrom = builder.animationFrom;
        animationTo = builder.animationTo;
        entranceAnimation = builder.entranceAnimation;
        exitAnimation = builder.exitAnimation;
        notificationAlign = builder.notificationAlign;
        subtitleAlign = builder.subtitleAlign;

        duration = builder.duration;
        isImage = builder.isImage;
        image = builder.image;
        isCoverActionBar = builder.isCoverActionBar;
        isSlideOverStatusAndActionBar = builder.isSlideOverStatusAndActionBar;
        isStatusBarVisible = builder.isStatusBarVisible;
        isDismissibleWithTap = builder.isDismissibleWithTap;
        showStatusBar = builder.showStatusBar;
        showActionBar = builder.showActionBar;
        notificationMessage = builder.notificationMessage;
        subtitleText = builder.subtitleText;
        activity = builder.activity;
    }

    public void show() {
        LinearLayout toast = (LinearLayout) activity.getLayoutInflater()
                .inflate(R.layout.toast, null);

        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        int statusBarHeight = (int) Math.ceil(60 * activity.getResources().getDisplayMetrics().density);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                statusBarHeight,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,

                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |

                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |

                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.RIGHT | Gravity.TOP;
        wm.addView(toast, params);
    }


}
