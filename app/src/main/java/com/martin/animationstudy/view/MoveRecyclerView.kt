package com.martin.animationstudy.view

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import com.martin.animationstudy.util.ScreenUtil
import timber.log.Timber

/**
 * @author martin
 */
class MoveRecyclerView : RecyclerView {

    /**
     * 是否带有状态栏
     * 这里考虑到,有些开发使用沉浸式开发,则状态栏的高度不计算在内
     * 如果状态栏没有使用沉浸式,则会出现第一次移动时闪烁的现象
     */
    var hasStatusBar = true

    /**
     * 距离顶部的高度
     * 滑动时,以此为最高滑动界限
     * 这个默认在布局中获取,也可以设值
     */
    var disTop: Float = 0F

    /**
     * 距离底部的高度
     * 滑动时,以此为最低滑动界限
     * 不获取,需要用户自己设值
     */
    var disBottom: Float = 0F

    /**
     * 屏幕高度
     */
    var screenHeight = ScreenUtil.getScreenHeight(context)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        if (disTop == 0F) {
            disTop = y
        }
    }

    /**
     * 触摸的位置,相对于RecyclerView
     */
    private var touchY = -1F

    /**
     * 触摸滑动
     */
    private var touchMove = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e: MotionEvent?): Boolean {
        when (e?.action) {
            MotionEvent.ACTION_DOWN -> {
                touchMove = false
                touchY = e.y
                Timber.e("ACTION_DOWN touch Y ========== $touchY")
            }
            MotionEvent.ACTION_UP -> {
                touchY = -1F
                if (touchMove) {
                    touchMove = false
                    return true
                }
            }
            MotionEvent.ACTION_MOVE -> {

                Timber.e("action move touch Y = $touchY")
                /*
                   还没有滑动过,刚进入滑动状态
                   这个时候,如果RecyclerView刚好在最顶部,并且滑动是向上,则不处理时间
                 */
                if (!touchMove) {
                    /*
                        如果已经在最顶部(相对于disTop)
                        并且滑动是向上
                        则不处理滑动事件
                     */
                    if (y <= disTop && touchY > e.y) {
                        return super.onTouchEvent(e)
                    }
                    /*
                        判断是否还可以向下滑动
                        如果可以,也不处理活动事件
                        @direction : 1:是否可以向上滑动
                                     -1:是否可以向下滑动
                     */
                    if (canScrollVertically(-1)) {
                        return super.onTouchEvent(e)
                    }
                    touchMove = true
                }

                /*
                    这里解决:
                    如果滑动到最顶部,并且RecyclerView的滑动事件发生
                    则下拉RecyclerView时,ACTION_DOWN事件不会触发
                 */
                if (touchY == -1F) {
                    touchY = e.y
                }

                var invY = e.rawY - touchY

                //如果有StatusBar,则需要减掉状态栏的高度
                if (hasStatusBar) {
                    invY -= ScreenUtil.getStatusHeight(context)
                }

                //如果滑动的距离到达设值的最高距离,则不继续上划
                if (invY < disTop) {
                    invY = disTop
                }

                /*
                    获取距离底部的最小距离
                    默认disBottom为0F,用户可以自己设值
                    如果用户没有设置,并且RecyclerView的Adapter有数据
                    则默认获取第一个Item的高度为最小距离
                 */
                var minHigh = disBottom
                if (disBottom == 0F && adapter.itemCount > 0) {
                    minHigh = getChildAt(0).height.toFloat()
                }

                /**
                 * 判断是否到达最小距离
                 * 如果是,则不想下滑动
                 */
                if (invY + minHigh > screenHeight) {
                    invY = screenHeight - minHigh
                }

                y = invY
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(e)
    }


}
