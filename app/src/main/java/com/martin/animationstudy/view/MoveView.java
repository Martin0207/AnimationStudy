package com.martin.animationstudy.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.martin.animationstudy.util.ScreenUtil;

/**
 * @author martin
 */
public class MoveView extends View {

    /**
     * View的宽高
     */
    private float width;
    private float height;

    /**
     * 触摸点相对于View的坐标
     */
    private float touchX;
    private float touchY;
    /**
     * 状态栏的高度
     */
    int barHeight = 0;
    /**
     * 屏幕的宽高
     */
    private int screenWidth;
    private int screenHeight;

    public MoveView(Context context) {
        super(context);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //获取状态栏高度
        barHeight = ScreenUtil.INSTANCE.getStatusHeight(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        screenWidth = ScreenUtil.INSTANCE.getScreenWidth(getContext());
        screenHeight = ScreenUtil.INSTANCE.getScreenHeight(getContext());
        width = canvas.getWidth();
        height = canvas.getHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                clearAnimation();
                touchX = event.getX();
                touchY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                /*
                    这里Y轴要减掉barHeight
                    因为计算时，View的默认坐标原点在状态栏下面
                    而屏幕的坐标是包含着状态栏的
                 */
                float nowY = event.getRawY() - touchY - barHeight;
                float nowX = event.getRawX() - touchX;
                nowX = nowX < 0 ? 0 : (nowX + width > screenWidth) ? (screenWidth - width) : nowX;
                nowY = nowY < 0 ? 0 : nowY + height > screenHeight ? screenHeight - height : nowY;
                this.setY(nowY);
                this.setX(nowX);
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                //这里做动画贴边效果
                float centerX = getX() + width / 2;
                if (centerX > screenWidth / 2) {
                    ObjectAnimator.ofFloat(this, "translationX",
                            getX(), screenWidth - width)
                            .setDuration(500)
                            .start();
                } else {
                    ObjectAnimator.ofFloat(this, "translationX",
                            getX(), 0)
                            .setDuration(500)
                            .start();
                }
                touchX = 0;
                touchY = 0;
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }
}
