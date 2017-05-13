package com.skateboard.moveabledemo;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

/**
 * Created by skateboard on 2017/5/10.
 */

public class MoveableLayout extends FrameLayout
{
    private float lastTouchX;
    private float lastTouchY;
    private int touchSlop;


    public MoveableLayout(@NonNull Context context)
    {
        this(context, null, 0);
    }

    public MoveableLayout(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);

    }

    public MoveableLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        boolean intercept = false;
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getRawX() - lastTouchX) > touchSlop || Math.abs(ev.getRawY() - lastTouchY) > touchSlop)
                {
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }

        lastTouchX = ev.getRawX();
        lastTouchY = ev.getRawY();
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {


        switch (event.getAction())
        {

            case MotionEvent.ACTION_MOVE:
                float disX = event.getRawX() - lastTouchX;
                float disY = event.getRawY() - lastTouchY;
                setX(getX() + disX);
                setY(getY() + disY);
                break;
        }
        lastTouchX = event.getRawX();
        lastTouchY = event.getRawY();
        return true;
    }

}
