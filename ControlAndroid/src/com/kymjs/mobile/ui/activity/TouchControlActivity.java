package com.kymjs.mobile.ui.activity;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public abstract class TouchControlActivity extends ControlActivity {
    
    private static float sMoveX = 0; // 发送的鼠标移动的差值
    private static float sMoveY = 0;
    private static float sPrevX; // 记录上次鼠标的位置
    private static float sPrevY;
    private static float sFirstX; // 手指第一次接触屏幕时的坐标
    private static float sFirstY;
    
    protected abstract View setTouchView();
    
    @Override
    protected void initWidget() {
        super.initWidget();
        /**
         * 触摸板事件
         */
        setTouchView().setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sPrevX = event.getX();
                    sPrevY = event.getY();
                    sFirstX = event.getX();
                    sFirstY = event.getY();
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    handleMoveEvent(event);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (sFirstX == event.getX() && sFirstY == event.getY()) {
                        sendMessage(LeftKeyDown);
                        sendMessage(LeftKeyUp);
                    } else {
                        v.performClick();
                    }
                }
                return true;
            }
        });
    }
    
    /**
     * 处理鼠标移动事件
     */
    private void handleMoveEvent(MotionEvent ev) {
        float currentX = ev.getX();
        sMoveX = currentX - sPrevX; // 当前鼠标位置 - 上次鼠标的位置
        sPrevX = currentX; // 把当前鼠标的位置付给lx 以备下次使用
        float currentY = ev.getY();
        sMoveY = currentY - sPrevY;
        sPrevY = currentY;
        if (sMoveX != 0 && sMoveY != 0) {
            StringBuilder str = new StringBuilder(MouseMove);
            str.append(sMoveX).append(",").append(sMoveY);
            sendMessage(str.toString());
        }
    }
    
}
