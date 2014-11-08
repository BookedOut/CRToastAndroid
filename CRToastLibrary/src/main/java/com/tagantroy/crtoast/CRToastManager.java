package com.tagantroy.crtoast;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CRToastManager {
    private static final Queue<CRToast> toastQueue = new ConcurrentLinkedQueue<CRToast>();

    public synchronized static void show(CRToast toast){
        toastQueue.offer(toast);
        if(toastQueue.size()==1){
            _show();
        }
    }

    public static void dismiss(){
        if(toastQueue.peek()!=null){
            toastQueue.poll().dismiss();
            if(hasNext()) showNext();
        }
    }

    public static boolean hasNext(){
        return toastQueue.size()>0;
    }

    public static void showNext(){
        if(toastQueue.size()>0){
            _show();
        }
    }

    private static void _show(){
        toastQueue.peek().show();
    }
}
