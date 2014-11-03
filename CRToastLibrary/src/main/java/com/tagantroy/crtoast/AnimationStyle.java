package com.tagantroy.crtoast;

import android.content.Context;

public enum AnimationStyle  {
    BottomToBottom,
    BottomToLeft,
    BottomToRight,
    BottomToTop,
    LeftToBottom,
    LeftToLeft,
    LeftToRight,
    LeftToTop,
    RightToBottom,
    RightToLeft,
    RightToRight,
    RightToTop,
    TopToBottom,
    TopToLeft,
    TopToRight,
    TopToTop;

    public int getStyle(Context context){
        return context.getResources().getIdentifier(this.name(),"style",context.getPackageName());
    }
}