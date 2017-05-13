package com.skateboard.moveabledemo;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

/**
 * Created by skateboard on 2017/5/13.
 */

public class INlineLayout extends FrameLayout
{

    private float lastTouchX;

    private float lastTouchY;

    private int slop;


    private MoveableLayout moveableLayout;

    public INlineLayout(@NonNull Context context)
    {
        this(context,null,0);
    }

    public INlineLayout(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs,0);
    }

    public INlineLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        slop= ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        moveableLayout= (MoveableLayout) getChildAt(0);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        boolean intercept=false;
        switch(ev.getAction())
        {

            case MotionEvent.ACTION_MOVE:

                if(moveableLayout.getX()+moveableLayout.getWidth()>=getWidth())
                {
                    moveableLayout.setX(getWidth()-moveableLayout.getWidth());
                    if(ev.getRawX()-lastTouchX>0 && Math.abs(ev.getRawX()-lastTouchX)>Math.abs(ev.getRawY()-lastTouchY))
                    {
                         intercept=true;
                         moveableLayout.setY(moveableLayout.getY()+ev.getRawY()-lastTouchY);
                    }
                }

                else if(moveableLayout.getX()<=0)
                {
                    moveableLayout.setX(0);
                    if(ev.getRawX()-lastTouchX<0 && Math.abs(ev.getRawX()-lastTouchX)>Math.abs(ev.getRawY()-lastTouchY))
                    {
                        intercept=true;
                        moveableLayout.setY(moveableLayout.getY()+ev.getRawY()-lastTouchY);
                    }
                }

                if(moveableLayout.getY()<=0)
                {
                    moveableLayout.setY(0);
                    if(ev.getRawY()-lastTouchY<0 && Math.abs(ev.getRawY()-lastTouchY)>Math.abs(ev.getRawX()-lastTouchX))
                    {
                        intercept=true;
                        moveableLayout.setX(moveableLayout.getX()+ev.getRawX()-lastTouchX);
                    }
                }

                else if(moveableLayout.getY()+moveableLayout.getHeight()>=getHeight())
                {
                    moveableLayout.setY(getHeight()-moveableLayout.getHeight());
                    if(ev.getRawY()-lastTouchY>0 && Math.abs(ev.getRawY()-lastTouchY)>Math.abs(ev.getRawX()-lastTouchX))
                    {
                        intercept=true;
                        moveableLayout.setX(moveableLayout.getX()+ev.getRawX()-lastTouchX);
                    }
                }

                break;

        }

        lastTouchX=ev.getRawX();
        lastTouchY=ev.getRawY();
        return intercept;
    }
}
